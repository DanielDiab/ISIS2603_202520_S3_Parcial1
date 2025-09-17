package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import co.edu.uniandes.dse.parcial1.repositories.RutaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EstacionRutaService {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private EstacionRepository estacionRepository;

    @Transactional
    public RutaEntity addEstacionRuta(Long idEstacion, Long idRuta)
            throws EntityNotFoundException, IllegalOperationException {

        EstacionEntity estacion = estacionRepository.findById(idEstacion)
            .orElseThrow(() -> new EntityNotFoundException("La estacion con id " + idEstacion + " no existe"));

        RutaEntity ruta = rutaRepository.findById(idRuta)
            .orElseThrow(() -> new EntityNotFoundException("La ruta con id " + idRuta + " no existe"));

        if (ruta.getEstaciones().contains(estacion)) {
            throw new IllegalOperationException("La estacion ya está asociada a la ruta");
        }

        ruta.getEstaciones().add(estacion);
        estacion.getRutas().add(ruta);

        return rutaRepository.save(ruta);
    }

    @Transactional
    public RutaEntity removeEstacionRuta(Long idEstacion, Long idRuta)
            throws EntityNotFoundException, IllegalOperationException {

        EstacionEntity estacion = estacionRepository.findById(idEstacion)
            .orElseThrow(() -> new EntityNotFoundException("La estacion con id " + idEstacion + " no existe"));

        RutaEntity ruta = rutaRepository.findById(idRuta)
            .orElseThrow(() -> new EntityNotFoundException("La ruta con id " + idRuta + " no existe"));

        if (!ruta.getEstaciones().contains(estacion)) {
            throw new IllegalOperationException("La estación no pertenece a la ruta");
        }

        ruta.getEstaciones().remove(estacion);
        estacion.getRutas().remove(ruta);

        return rutaRepository.save(ruta);
    }
}