/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.repository;

import java.util.List;
import miumg.edu.Biblioteca.entity.DetallePrestamo;
import miumg.edu.Biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author danyt
 */
@Repository
public interface DetallePrestamoRepository extends JpaRepository<DetallePrestamo, Long> {
    List<DetallePrestamo> findByPrestamo(Prestamo prestamo);
}
