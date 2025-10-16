/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.entity;

/**
 *
 * @author danyt
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORIAL")
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HISTORIAL")
    private Long idHistorial;

    @ManyToOne
    @JoinColumn(name = "ID_PRESTAMO", nullable = false)
    @JsonIgnoreProperties({"historiales","detalles","usuario"})
    private Prestamo prestamo;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    @JsonIgnoreProperties({"prestamos","historiales","contrasena"})
    private Usuario usuario;

    @Column(name = "FECHA_EVENTO")
    private LocalDateTime fechaEvento;

    @Column(name = "EVENTO", nullable = false, length = 200)
    private String evento;

    public Historial() {}

    public Long getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Long idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }
}

