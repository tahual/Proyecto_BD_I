/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.dto;

/**
 *
 * @author danyt
 */
public class LoanItemDTO {
    private Long libroId;
    private Integer cantidad;

    public LoanItemDTO() {}
    public LoanItemDTO(Long libroId, Integer cantidad) {
        this.libroId = libroId; this.cantidad = cantidad;
    }
    public Long getLibroId() { return libroId; }
    public void setLibroId(Long libroId) { this.libroId = libroId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
