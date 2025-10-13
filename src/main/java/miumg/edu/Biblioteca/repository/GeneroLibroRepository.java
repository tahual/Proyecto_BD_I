/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.repository;

import java.util.Optional;
import miumg.edu.Biblioteca.entity.GeneroLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author danyt
 */

@Repository
public interface GeneroLibroRepository extends JpaRepository<GeneroLibro, Long> {
    Optional<GeneroLibro> findByNombreGenero(String nombreGenero);
}