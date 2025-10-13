/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.Prestamo;
import miumg.edu.Biblioteca.entity.Usuario;

/**
 *
 * @author danyt
 */
public interface PrestamoService {
    List<Prestamo> findAll();
    Optional<Prestamo> findById(Long id);
    Prestamo save(Prestamo prestamo);
    void deleteById(Long id);
    List<Prestamo> findByUsuario(Usuario usuario);
}

