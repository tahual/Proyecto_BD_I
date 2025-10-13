/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.Usuario;

/**
 *
 * @author danyt
 */

public interface UsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Usuario save(Usuario usuario);
    void deleteById(Long id);
    Optional<Usuario> findByNombre(String nombre);
}