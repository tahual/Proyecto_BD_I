/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.entity;

/**
 *
 * @author danyt
 */

import jakarta.persistence.*;
import java.util.List;

import javax.persistence.Entity;

@Entity
@Table(name = "GENERO_LIBROS")
public class GeneroLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GENERO_LIBRO")
    private Long idGeneroLibro;

    @Column(name = "NOMBRE_GENERO", nullable = false, length = 100)
    private String nombreGenero;

    @OneToMany(mappedBy = "genero", fetch = FetchType.LAZY)
    private List<Libro> libros;

    public GeneroLibro() {}

    public Long getIdGeneroLibro() {
        return idGeneroLibro;
    }

    public void setIdGeneroLibro(Long idGeneroLibro) {
        this.idGeneroLibro = idGeneroLibro;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
