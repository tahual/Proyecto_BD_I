/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.ServiceImpl;

import java.util.List;
import java.util.Optional;
import miumg.edu.Biblioteca.entity.Rol;
import miumg.edu.Biblioteca.repository.RolRepository;
import miumg.edu.Biblioteca.service.RolService;
import org.springframework.stereotype.Service;

/**
 *
 * @author danyt
 */
@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @Override
    public Optional<Rol> findById(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void deleteById(Long id) {
        rolRepository.deleteById(id);
    }

    @Override
    public Optional<Rol> findByNombreRol(String nombreRol) {
        return Optional.ofNullable(rolRepository.findByNombreRol(nombreRol));
    }
}