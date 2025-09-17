package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;

    @Transactional
    public EstacionEntity crearEstacion(EstacionEntity estacion) throws IllegalOperationException {
        if (estacion.getNombre() == null || estacion.getNombre().trim().length() < 3)
            throw new IllegalOperationException("El nombre de la estación debe tener al menos 3 caracteres");

        if (estacion.getDireccion() == null || estacion.getDireccion().trim().length() < 5)
            throw new IllegalOperationException("La dirección de la estación es obligatoria");

        if (estacion.getCapacidad() == null || estacion.getCapacidad() <= 0)
            throw new IllegalOperationException("La capacidad de la estación debe ser mayor que 0");

        return estacionRepository.save(estacion);
    }
}