package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
public class RutaEntity extends BaseEntity {

    private String nombre;
    private String color;
    private String tipo;

    @PodamExclude
    @ManyToMany
    private List<EstacionEntity> estaciones = new ArrayList<>();
}
