/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.ServiceImpl;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.Historial;
import miumg.edu.Biblioteca.entity.Prestamo;
import miumg.edu.Biblioteca.entity.Usuario;
import miumg.edu.Biblioteca.repository.HistorialRepository;
import miumg.edu.Biblioteca.service.HistorialService;
import org.springframework.stereotype.Service;

/**
 *
 * @author danyt
 */
@Service
public class HistorialServiceImpl implements HistorialService {

    private final HistorialRepository historialRepository;

    public HistorialServiceImpl(HistorialRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    @Override
    public List<Historial> findAll() {
        return historialRepository.findAll();
    }

    @Override
    public Optional<Historial> findById(Long id) {
        return historialRepository.findById(id);
    }

    @Override
    public Historial save(Historial historial) {
        return historialRepository.save(historial);
    }

    @Override
    public void deleteById(Long id) {
        historialRepository.deleteById(id);
    }

    @Override
    public List<Historial> findByUsuario(Usuario usuario) {
        return historialRepository.findByUsuario(usuario);
    }

    @Override
    public List<Historial> findByPrestamo(Prestamo prestamo) {
        return historialRepository.findByPrestamo(prestamo);
    }
}