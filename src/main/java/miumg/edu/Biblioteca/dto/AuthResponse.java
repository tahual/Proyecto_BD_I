/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.dto;

/**
 *
 * @author danyt
 */
public class AuthResponse {
    private String token;
    private String username;
    private String role;

    public AuthResponse() {}
    public AuthResponse(String token, String username, String role) {
        this.token = token; this.username = username; this.role = role;
    }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
