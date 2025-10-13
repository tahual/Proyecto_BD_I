/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.Historial;
import miumg.edu.Biblioteca.entity.Prestamo;
import miumg.edu.Biblioteca.entity.Usuario;

/**
 *
 * @author danyt
 */
public interface HistorialService {
    List<Historial> findAll();
    Optional<Historial> findById(Long id);
    Historial save(Historial historial);
    void deleteById(Long id);
    List<Historial> findByUsuario(Usuario usuario);
    List<Historial> findByPrestamo(Prestamo prestamo);
}