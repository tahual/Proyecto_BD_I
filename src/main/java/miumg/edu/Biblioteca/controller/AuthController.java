/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.controller;

import miumg.edu.Biblioteca.dto.AuthRequest;
import miumg.edu.Biblioteca.dto.AuthResponse;
import miumg.edu.Biblioteca.dto.RegisterDTO;
import miumg.edu.Biblioteca.entity.Rol;
import miumg.edu.Biblioteca.entity.Usuario;
import miumg.edu.Biblioteca.repository.RolRepository;
import miumg.edu.Biblioteca.repository.UsuarioRepository;
import miumg.edu.Biblioteca.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author danyt
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // Autenticamos al usuario
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Buscamos el usuario directamente
            Usuario usuario = usuarioRepository.findByNombre(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + request.getUsername()));

            // Obtenemos el rol
            String role = usuario.getRol().getNombreRol();

            // Generamos el token JWT
            String token = jwtTokenUtil.generateToken(usuario.getNombre(), role);

            // Devolvemos respuesta al frontend
            return ResponseEntity.ok(new AuthResponse(token, usuario.getNombre(), role));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    // Registro público: crea usuario con rol LECTOR
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        if (usuarioRepository.findByNombre(dto.getNombre()).isPresent()) {
            return ResponseEntity.badRequest().body("Usuario ya existe");
        }
        Rol rol = rolRepository.findByNombreRol("USUARIO");
        if (rol == null) {
            return ResponseEntity.status(500).body("Roles no inicializados");
        }
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setContrasena(passwordEncoder.encode(dto.getPassword()));
        u.setRol(rol);
        usuarioRepository.save(u);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }
}
