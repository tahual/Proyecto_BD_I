/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.GeneroLibro;

/**
 *
 * @author danyt
 */
public interface GeneroLibroService {
    List<GeneroLibro> findAll();
    Optional<GeneroLibro> findById(Long id);
    GeneroLibro save(GeneroLibro generoLibro);
    void deleteById(Long id);
    Optional<GeneroLibro> findByNombreGenero(String nombreGenero);
}