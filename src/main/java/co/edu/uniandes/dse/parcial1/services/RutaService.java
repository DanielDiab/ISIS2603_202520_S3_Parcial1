package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.RutaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    @Transactional
    public RutaEntity crearRuta(RutaEntity ruta) throws IllegalOperationException {
        if (ruta.getNombre() == null || ruta.getNombre().trim().length() < 3)
            throw new IllegalOperationException("El nombre de la ruta debe tener al menos 3 caracteres");

        if (ruta.getColor() == null || ruta.getColor().trim().isEmpty())
            throw new IllegalOperationException("El color de la ruta es obligatorio");

        if (ruta.getTipo() == null || ruta.getTipo().trim().isEmpty())
            throw new IllegalOperationException("El tipo de la ruta es obligatorio"); 

        return rutaRepository.save(ruta);
    }
}
