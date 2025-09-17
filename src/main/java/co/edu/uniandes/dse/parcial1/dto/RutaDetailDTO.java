package co.edu.uniandes.dse.parcial1.dto;
import lombok.Data;
import java.util.List;

@Data
public class RutaDetailDTO extends RutaDTO {
    private List<EstacionDTO> estaciones;
}
