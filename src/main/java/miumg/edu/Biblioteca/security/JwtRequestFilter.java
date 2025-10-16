/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author danyt
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    // ESTO ES LO CLAVE PARA ELIMINAR EL 403 EN EL REGISTRO
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // Excluye /api/auth/register y /api/auth/login
        return path.startsWith("/api/auth/");
    }

   @Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain chain)
        throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");
    String username = null;
    String jwt = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        jwt = authHeader.substring(7);
        try {
            username = jwtTokenUtil.extractUsername(jwt);
        } catch (Exception e) {
            System.out.println("Token inválido o expirado: " + e.getMessage());
            // No bloqueamos la solicitud, simplemente no autenticamos
            username = null;
        }
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    chain.doFilter(request, response);
}

}