package co.edu.uniandes.dse.parcial1.controllers;

import co.edu.uniandes.dse.parcial1.dto.EstacionDTO;
import co.edu.uniandes.dse.parcial1.dto.EstacionDetailDTO;
import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.services.EstacionService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estaciones")
public class EstacionController {

    @Autowired
    private EstacionService estacionService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstacionDetailDTO crearEstacion(@RequestBody EstacionDTO dto) throws IllegalOperationException {
        EstacionEntity entidad = modelMapper.map(dto, EstacionEntity.class);
        EstacionEntity creada = estacionService.crearEstacion(entidad);
        return modelMapper.map(creada, EstacionDetailDTO.class);
    }
}
