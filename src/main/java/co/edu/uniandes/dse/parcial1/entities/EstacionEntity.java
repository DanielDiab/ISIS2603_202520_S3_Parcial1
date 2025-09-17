package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.FetchType;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
public class EstacionEntity extends BaseEntity {

    private String nombre;
    private String direccion;
    private Integer capacidad;

    @PodamExclude
    @ManyToMany(mappedBy = "estaciones", fetch = FetchType.LAZY)
    private List<RutaEntity> rutas = new ArrayList<>();
}
