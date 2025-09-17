package co.edu.uniandes.dse.parcial1.services;


import static org.junit.jupiter.api.Assertions.*;

import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(RutaService.class)
class RutaServiceTest {

    @Autowired
    private RutaService rutaService;

    @Autowired
    private TestEntityManager entityManager;

    private final PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void clearData() {
        entityManager.getEntityManager().createQuery("delete from RutaEntity").executeUpdate();
    }

    @Test
    void testCrearRuta_ok() throws IllegalOperationException {
        RutaEntity nueva = factory.manufacturePojo(RutaEntity.class);
        nueva.setNombre("Ruta Principal");
        nueva.setColor("Rojo");
        nueva.setTipo("diurna");

        RutaEntity resultado = rutaService.crearRuta(nueva);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("Ruta Principal", resultado.getNombre());

        RutaEntity enBD = entityManager.find(RutaEntity.class, resultado.getId());
        assertNotNull(enBD);
        assertEquals(resultado.getNombre(), enBD.getNombre());
    }
}

    