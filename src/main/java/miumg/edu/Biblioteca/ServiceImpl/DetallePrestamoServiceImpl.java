/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.ServiceImpl;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.DetallePrestamo;
import miumg.edu.Biblioteca.entity.Prestamo;
import miumg.edu.Biblioteca.repository.DetallePrestamoRepository;
import miumg.edu.Biblioteca.service.DetallePrestamoService;
import org.springframework.stereotype.Service;

/**
 *
 * @author danyt
 */
@Service
public class DetallePrestamoServiceImpl implements DetallePrestamoService {

    private final DetallePrestamoRepository detallePrestamoRepository;

    public DetallePrestamoServiceImpl(DetallePrestamoRepository detallePrestamoRepository) {
        this.detallePrestamoRepository = detallePrestamoRepository;
    }

    @Override
    public List<DetallePrestamo> findAll() {
        return detallePrestamoRepository.findAll();
    }

    @Override
    public Optional<DetallePrestamo> findById(Long id) {
        return detallePrestamoRepository.findById(id);
    }

    @Override
    public DetallePrestamo save(DetallePrestamo detalle) {
        return detallePrestamoRepository.save(detalle);
    }

    @Override
    public void deleteById(Long id) {
        detallePrestamoRepository.deleteById(id);
    }

    @Override
    public List<DetallePrestamo> findByPrestamo(Prestamo prestamo) {
        return detallePrestamoRepository.findByPrestamo(prestamo);
    }
}