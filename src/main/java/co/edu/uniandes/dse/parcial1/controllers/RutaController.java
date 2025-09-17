package co.edu.uniandes.dse.parcial1.controllers;

import co.edu.uniandes.dse.parcial1.dto.RutaDTO;
import co.edu.uniandes.dse.parcial1.dto.RutaDetailDTO;
import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.services.RutaService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rutas")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RutaDetailDTO crearRuta(@RequestBody RutaDTO dto) throws IllegalOperationException {
        RutaEntity entidad = modelMapper.map(dto, RutaEntity.class);
        RutaEntity creada = rutaService.crearRuta(entidad);
        return modelMapper.map(creada, RutaDetailDTO.class);
    }
}