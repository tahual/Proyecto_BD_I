/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.controller;

import java.util.List;
import miumg.edu.Biblioteca.entity.Rol;
import miumg.edu.Biblioteca.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author danyt
 */
@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired private RolRepository rolRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createRol(@RequestBody Rol rol) {
        if (rolRepository.findByNombreRol(rol.getNombreRol()) != null) {
            return ResponseEntity.badRequest().body("Rol ya existe");
        }
        return ResponseEntity.ok(rolRepository.save(rol));
    }
}
