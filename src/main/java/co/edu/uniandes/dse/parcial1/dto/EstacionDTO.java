package co.edu.uniandes.dse.parcial1.dto;

import lombok.Data;

@Data
public class EstacionDTO {
    private long id;
    private String nombre;
    private String direccion;
    private int capacidad;
}
