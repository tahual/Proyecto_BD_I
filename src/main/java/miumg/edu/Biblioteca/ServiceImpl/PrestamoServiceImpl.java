/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.ServiceImpl;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.Prestamo;
import miumg.edu.Biblioteca.entity.Usuario;
import miumg.edu.Biblioteca.repository.PrestamoRepository;
import miumg.edu.Biblioteca.service.PrestamoService;
import org.springframework.stereotype.Service;

/**
 *
 * @author danyt
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }

    @Override
    public Optional<Prestamo> findById(Long id) {
        return prestamoRepository.findById(id);
    }

    @Override
    public Prestamo save(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    public void deleteById(Long id) {
        prestamoRepository.deleteById(id);
    }

    @Override
    public List<Prestamo> findByUsuario(Usuario usuario) {
        return prestamoRepository.findByUsuario(usuario);
    }
}