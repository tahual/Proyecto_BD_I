/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.config;

import miumg.edu.Biblioteca.entity.Rol;
import miumg.edu.Biblioteca.entity.Usuario;
import miumg.edu.Biblioteca.repository.RolRepository;
import miumg.edu.Biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author danyt
 */
@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired private RolRepository rolRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (rolRepository.count() == 0) {
            Rol admin = new Rol();
            admin.setNombreRol("ADMIN");
            rolRepository.save(admin);

            Rol biblio = new Rol();
            biblio.setNombreRol("BIBLIOTECARIO");
            rolRepository.save(biblio);

            Rol usuario = new Rol();
            usuario.setNombreRol("USUARIO");
            rolRepository.save(usuario);

            // Crear usuario admin por defecto si no existe
            if (usuarioRepository.findByNombre("admin").isEmpty()) {
                Usuario u = new Usuario();
                u.setNombre("admin");
                u.setContrasena(passwordEncoder.encode("admin123"));
                u.setRol(admin);
                usuarioRepository.save(u);
                System.out.println("Usuario admin creado: admin/admin123");
            }
        }
    }
}
