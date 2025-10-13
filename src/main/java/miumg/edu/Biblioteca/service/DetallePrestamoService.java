/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.DetallePrestamo;
import miumg.edu.Biblioteca.entity.Prestamo;

/**
 *
 * @author danyt
 */
public interface DetallePrestamoService {
    List<DetallePrestamo> findAll();
    Optional<DetallePrestamo> findById(Long id);
    DetallePrestamo save(DetallePrestamo detalle);
    void deleteById(Long id);
    List<DetallePrestamo> findByPrestamo(Prestamo prestamo);
}