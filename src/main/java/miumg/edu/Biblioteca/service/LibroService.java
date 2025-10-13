/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.Libro;

/**
 *
 * @author danyt
 */
public interface LibroService {
    List<Libro> findAll();
    Optional<Libro> findById(Long id);
    Libro save(Libro libro);
    void deleteById(Long id);
    List<Libro> findByTitulo(String titulo);
    List<Libro> findByAutor(String autor);
}
