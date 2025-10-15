/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.controller;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.Rol;
import miumg.edu.Biblioteca.entity.Usuario;
import miumg.edu.Biblioteca.repository.RolRepository;
import miumg.edu.Biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private RolRepository rolRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Usuario> listAll() {
        return usuarioRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Usuario> u = usuarioRepository.findById(id);
        return u.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Admin crea usuario con rol especificado
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Usuario usuario, @RequestParam(required = false) String roleName) {
        if (usuarioRepository.findByNombre(usuario.getNombre()).isPresent()) {
            return ResponseEntity.badRequest().body("Usuario ya existe");
        }
        Rol rol = roleName != null ? rolRepository.findByNombreRol(roleName) : rolRepository.findByNombreRol("USUARIO");
        if (rol == null) return ResponseEntity.badRequest().body("Rol inválido");
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuario.setRol(rol);
        Usuario saved = usuarioRepository.save(usuario);
        return ResponseEntity.ok(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Usuario update) {
        Optional<Usuario> uOpt = usuarioRepository.findById(id);
        if (uOpt.isEmpty()) return ResponseEntity.notFound().build();
        Usuario u = uOpt.get();
        u.setNombre(update.getNombre() != null ? update.getNombre() : u.getNombre());
        if (update.getContrasena() != null && !update.getContrasena().isBlank()) {
            u.setContrasena(passwordEncoder.encode(update.getContrasena()));
        }
        if (update.getRol() != null) {
            Rol rol = rolRepository.findById(update.getRol().getIdRol()).orElse(null);
            if (rol != null) u.setRol(rol);
        }
        usuarioRepository.save(u);
        return ResponseEntity.ok(u);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) return ResponseEntity.notFound().build();
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("El usuario fue eliminado");
    }
}
