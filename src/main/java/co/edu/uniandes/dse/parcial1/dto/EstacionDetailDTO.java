package co.edu.uniandes.dse.parcial1.dto;

import lombok.Data;
import java.util.List;

@Data
public class EstacionDetailDTO extends EstacionDTO {
    private List<RutaDTO> rutas;
}
