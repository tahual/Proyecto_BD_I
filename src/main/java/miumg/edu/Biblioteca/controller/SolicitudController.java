/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.controller;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import miumg.edu.Biblioteca.dto.LoanItemDTO;
import miumg.edu.Biblioteca.dto.LoanRequestDTO;
import miumg.edu.Biblioteca.entity.DetallePrestamo;
import miumg.edu.Biblioteca.entity.Historial;
import miumg.edu.Biblioteca.entity.Libro;
import miumg.edu.Biblioteca.entity.Prestamo;
import miumg.edu.Biblioteca.entity.Usuario;
import miumg.edu.Biblioteca.repository.DetallePrestamoRepository;
import miumg.edu.Biblioteca.repository.HistorialRepository;
import miumg.edu.Biblioteca.repository.LibroRepository;
import miumg.edu.Biblioteca.repository.PrestamoRepository;
import miumg.edu.Biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author danyt
 */
@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private LibroRepository libroRepository;
    @Autowired private PrestamoRepository prestamoRepository;
    @Autowired private DetallePrestamoRepository detalleRepository;
    @Autowired private HistorialRepository historialRepository;

    // Usuario solicita un préstamo (o bibliotecario registra el préstamo en nombre de un usuario)
    @PreAuthorize("hasAnyRole('USUARIO','BIBLIOTECARIO','ADMIN')")
    @PostMapping
    @Transactional
    public ResponseEntity<?> crearPrestamo(@RequestBody LoanRequestDTO request, Authentication authentication) {
        Long usuarioId = request.getUsuarioId();
        if (usuarioId == null) {
            // intentar obtener usuario por username del token
            String nombre = authentication.getName();
            Optional<Usuario> uo = usuarioRepository.findByNombre(nombre);
            if (uo.isEmpty()) return ResponseEntity.badRequest().body("Usuario no encontrado");
            usuarioId = uo.get().getIdUsuario();
        }
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) return ResponseEntity.badRequest().body("Usuario inválido");
        Usuario usuario = usuarioOpt.get();

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setEstado("ACTIVO");
        prestamo = prestamoRepository.save(prestamo);

        List<String> errors = new ArrayList<>();
        List<DetallePrestamo> detallesGuardados = new ArrayList<>();

        for (LoanItemDTO item : request.getItems()) {
            Optional<Libro> libroOpt = libroRepository.findById(item.getLibroId());
            if (libroOpt.isEmpty()) {
                errors.add("Libro no encontrado: id " + item.getLibroId());
                continue;
            }
            Libro libro = libroOpt.get();
            int disponibles = libro.getNumCopias() == null ? 0 : libro.getNumCopias();
            int pedir = item.getCantidad() == null ? 1 : item.getCantidad();

            if (pedir <= 0) {
                errors.add("Cantidad inválida para libro id " + item.getLibroId());
                continue;
            }
            if (disponibles < pedir) {
                errors.add("No hay suficientes copias para libro id " + item.getLibroId());
                continue;
            }

            // descuentos de stock
            libro.setNumCopias(disponibles - pedir);
            libroRepository.save(libro);

            DetallePrestamo detalle = new DetallePrestamo();
            detalle.setPrestamo(prestamo);
            detalle.setLibro(libro);
            detalle.setCantidad(pedir);
            detalle = detalleRepository.save(detalle);
            detallesGuardados.add(detalle);

            // historial
            Historial h = new Historial();
            h.setPrestamo(prestamo);
            h.setUsuario(usuario);
            h.setFechaEvento(LocalDateTime.now());
            h.setEvento("Se prestó " + pedir + " ejemplar(es) del libro: " + libro.getTitulo());
            historialRepository.save(h);
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("prestamoId", prestamo.getIdPrestamo());
        resp.put("detalles", detallesGuardados);
        resp.put("errors", errors);
        return ResponseEntity.ok(resp);
    }

    // Devolver préstamo: aumenta stock por cada detalle y marca fechaDevolucion
    @PreAuthorize("hasAnyRole('BIBLIOTECARIO','ADMIN')")
    @PostMapping("/{id}/devolver")
    @Transactional
    public ResponseEntity<?> devolverPrestamo(@PathVariable Long id, Authentication authentication) {
        Optional<Prestamo> pOpt = prestamoRepository.findById(id);
        if (pOpt.isEmpty()) return ResponseEntity.notFound().build();
        Prestamo p = pOpt.get();
        if (!"ACTIVO".equalsIgnoreCase(p.getEstado())) {
            return ResponseEntity.badRequest().body("Prestamo ya devuelto o no activo");
        }

        List<DetallePrestamo> detalles = detalleRepository.findByPrestamo(p);
        for (DetallePrestamo d : detalles) {
            Libro libro = d.getLibro();
            int actuales = libro.getNumCopias() == null ? 0 : libro.getNumCopias();
            libro.setNumCopias(actuales + (d.getCantidad() == null ? 1 : d.getCantidad()));
            libroRepository.save(libro);
        }
        p.setFechaDevolucion(LocalDateTime.now());
        p.setEstado("FINALIZADO");
        prestamoRepository.save(p);

        // Historial
        Historial h = new Historial();
        h.setPrestamo(p);
        h.setUsuario(p.getUsuario());
        h.setFechaEvento(LocalDateTime.now());
        h.setEvento("Devolución realizada por: " + authentication.getName());
        historialRepository.save(h);

        return ResponseEntity.ok("Devolución registrada correctamente");
    }

    // Listar prestamos de un usuario (usuario puede ver los suyos; admin/biblio pueden ver cualquiera)
    @PreAuthorize("hasAnyRole('USUARIO','BIBLIOTECARIO','ADMIN')")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> prestamosPorUsuario(@PathVariable Long id, Authentication authentication) {
        Optional<Usuario> uOpt = usuarioRepository.findById(id);
        if (uOpt.isEmpty()) return ResponseEntity.notFound().build();
        Usuario u = uOpt.get();

        // si el requester no es admin/biblio y está pidiendo otro usuario -> deny
        String requester = authentication.getName();
        Optional<Usuario> reqOpt = usuarioRepository.findByNombre(requester);
        if (reqOpt.isPresent()) {
            String role = reqOpt.get().getRol().getNombreRol();
            if (!role.equals("ADMIN") && !role.equals("BIBLIOTECARIO") && !Objects.equals(requester, u.getNombre())) {
                return ResponseEntity.status(403).body("No autorizado");
            }
        }

        List<Prestamo> prestamos = prestamoRepository.findByUsuario(u);
        return ResponseEntity.ok(prestamos);
    }
}
