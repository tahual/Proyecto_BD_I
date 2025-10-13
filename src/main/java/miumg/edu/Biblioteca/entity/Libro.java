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
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "LIBROS")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LIBRO")
    private Long idLibro;

    @Column(name = "TITULO", nullable = false, length = 200)
    private String titulo;

    @Column(name = "AUTOR", nullable = false, length = 100)
    private String autor;

    @Column(name = "ANIO_PUBLICACION")
    private Integer anioPublicacion;

    @ManyToOne
    @JoinColumn(name = "ID_GENERO_LIBRO")
    private GeneroLibro genero;

    @Column(name = "NUM_COPIAS")
    private Integer numCopias;

    @Column(name = "FECHA_REGISTRO")
    private LocalDateTime fechaRegistro;

    @Column(name = "EDITORIAL", nullable = false, length = 100)
    private String editorial;

    @OneToMany(mappedBy = "libro", fetch = FetchType.LAZY)
    private List<DetallePrestamo> detallesPrestamo;

    public Libro() {}

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public GeneroLibro getGenero() {
        return genero;
    }

    public void setGenero(GeneroLibro genero) {
        this.genero = genero;
    }

    public Integer getNumCopias() {
        return numCopias;
    }

    public void setNumCopias(Integer numCopias) {
        this.numCopias = numCopias;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public List<DetallePrestamo> getDetallesPrestamo() {
        return detallesPrestamo;
    }

    public void setDetallesPrestamo(List<DetallePrestamo> detallesPrestamo) {
        this.detallesPrestamo = detallesPrestamo;
    }
}
