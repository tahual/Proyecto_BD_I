/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.Rol;

/**
 *
 * @author danyt
 */
public interface RolService {
    List<Rol> findAll();
    Optional<Rol> findById(Long id);
    Rol save(Rol rol);
    void deleteById(Long id);
    Optional<Rol> findByNombreRol(String nombreRol);
}