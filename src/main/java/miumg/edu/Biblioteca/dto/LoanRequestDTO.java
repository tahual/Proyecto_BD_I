/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.Biblioteca.dto;

import java.util.List;

/**
 *
 * @author danyt
 */
public class LoanRequestDTO {
    private Long usuarioId; // opcional si usuario logueado
    private List<LoanItemDTO> items;

    public LoanRequestDTO() {}
    public LoanRequestDTO(Long usuarioId, List<LoanItemDTO> items) {
        this.usuarioId = usuarioId; this.items = items;
    }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public List<LoanItemDTO> getItems() { return items; }
    public void setItems(List<LoanItemDTO> items) { this.items = items; }
}