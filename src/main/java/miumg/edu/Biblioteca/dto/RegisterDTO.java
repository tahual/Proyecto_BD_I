/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.dto;

/**
 *
 * @author danyt
 */
public class RegisterDTO {
    private String nombre;
    private String password;

    public RegisterDTO() {}
    public RegisterDTO(String nombre, String password) {
        this.nombre = nombre; this.password = password;
    }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}