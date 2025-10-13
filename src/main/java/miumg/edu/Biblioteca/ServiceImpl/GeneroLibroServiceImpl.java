/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.ServiceImpl;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.GeneroLibro;
import miumg.edu.Biblioteca.repository.GeneroLibroRepository;
import miumg.edu.Biblioteca.service.GeneroLibroService;
import org.springframework.stereotype.Service;

/**
 *
 * @author danyt
 */
@Service
public class GeneroLibroServiceImpl implements GeneroLibroService {

    private final GeneroLibroRepository generoLibroRepository;

    public GeneroLibroServiceImpl(GeneroLibroRepository generoLibroRepository) {
        this.generoLibroRepository = generoLibroRepository;
    }

    @Override
    public List<GeneroLibro> findAll() {
        return generoLibroRepository.findAll();
    }

    @Override
    public Optional<GeneroLibro> findById(Long id) {
        return generoLibroRepository.findById(id);
    }

    @Override
    public GeneroLibro save(GeneroLibro generoLibro) {
        return generoLibroRepository.save(generoLibro);
    }

    @Override
    public void deleteById(Long id) {
        generoLibroRepository.deleteById(id);
    }

    @Override
    public Optional<GeneroLibro> findByNombreGenero(String nombreGenero) {
        return generoLibroRepository.findByNombreGenero(nombreGenero);
    }
}