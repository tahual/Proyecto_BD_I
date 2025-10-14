/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.Libro;
import miumg.edu.Biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author danyt
 */
@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired private LibroRepository libroRepository;

    // Público: búsqueda
    @GetMapping
    public List<Libro> listAll(@RequestParam(required = false) String q) {
        if (q == null || q.isBlank()) return libroRepository.findAll();
        List<Libro> byTitle = libroRepository.findByTituloContainingIgnoreCase(q);
        if (!byTitle.isEmpty()) return byTitle;
        return libroRepository.findByAutorContainingIgnoreCase(q);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Libro> l = libroRepository.findById(id);
        return l.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('BIBLIOTECARIO','ADMIN')")
    @PostMapping
    public ResponseEntity<?> createLibro(@RequestBody Libro libro) {
        libro.setFechaRegistro(LocalDateTime.now());
        if (libro.getNumCopias() == null) libro.setNumCopias(1);
        Libro saved = libroRepository.save(libro);
        return ResponseEntity.ok(saved);
    }

    @PreAuthorize("hasAnyRole('BIBLIOTECARIO','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLibro(@PathVariable Long id, @RequestBody Libro update) {
        Optional<Libro> lOpt = libroRepository.findById(id);
        if (lOpt.isEmpty()) return ResponseEntity.notFound().build();
        Libro l = lOpt.get();
        l.setTitulo(update.getTitulo() != null ? update.getTitulo() : l.getTitulo());
        l.setAutor(update.getAutor() != null ? update.getAutor() : l.getAutor());
        l.setAnioPublicacion(update.getAnioPublicacion() != null ? update.getAnioPublicacion() : l.getAnioPublicacion());
        l.setEditorial(update.getEditorial() != null ? update.getEditorial() : l.getEditorial());
        if (update.getNumCopias() != null) l.setNumCopias(update.getNumCopias());
        libroRepository.save(l);
        return ResponseEntity.ok(l);
    }

    @PreAuthorize("hasAnyRole('BIBLIOTECARIO','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable Long id) {
        if (!libroRepository.existsById(id)) return ResponseEntity.notFound().build();
        libroRepository.deleteById(id);
        return ResponseEntity.ok("Libro eliminado");
    }

    // Endpoints de ajuste de stock: ingresar y sacar
    @PreAuthorize("hasAnyRole('BIBLIOTECARIO','ADMIN')")
    @PostMapping("/{id}/ingresar")
    public ResponseEntity<?> ingresarEjemplares(@PathVariable Long id, @RequestParam int cantidad) {
        Optional<Libro> lOpt = libroRepository.findById(id);
        if (lOpt.isEmpty()) return ResponseEntity.notFound().build();
        Libro l = lOpt.get();
        l.setNumCopias((l.getNumCopias() == null ? 0 : l.getNumCopias()) + cantidad);
        libroRepository.save(l);
        return ResponseEntity.ok(l);
    }

    @PreAuthorize("hasAnyRole('BIBLIOTECARIO','ADMIN')")
    @PostMapping("/{id}/sacar")
    public ResponseEntity<?> sacarEjemplares(@PathVariable Long id, @RequestParam int cantidad) {
        Optional<Libro> lOpt = libroRepository.findById(id);
        if (lOpt.isEmpty()) return ResponseEntity.notFound().build();
        Libro l = lOpt.get();
        int actuales = l.getNumCopias() == null ? 0 : l.getNumCopias();
        if (cantidad > actuales) return ResponseEntity.badRequest().body("No hay suficientes ejemplares");
        l.setNumCopias(actuales - cantidad);
        libroRepository.save(l);
        return ResponseEntity.ok(l);
    }
}
