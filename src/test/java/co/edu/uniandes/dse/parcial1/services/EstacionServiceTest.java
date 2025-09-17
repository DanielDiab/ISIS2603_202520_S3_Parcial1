package co.edu.uniandes.dse.parcial1.services;


import static org.junit.jupiter.api.Assertions.*;

import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
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
@Import(EstacionService.class)
class EstacionServiceTest {

    @Autowired
    private EstacionService estacionService;

    @Autowired
    private TestEntityManager entityManager;

    private final PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void clearData() {
        entityManager.getEntityManager().createQuery("delete from EstacionEntity").executeUpdate();
    }

    @Test
    void testCrearEstacion_ok() throws IllegalOperationException {
        EstacionEntity nueva = factory.manufacturePojo(EstacionEntity.class);
        nueva.setNombre("Central");
        nueva.setDireccion("Calle 45 # 10-20");
        nueva.setCapacidad(500);

        EstacionEntity resultado = estacionService.crearEstacion(nueva);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("Central", resultado.getNombre());

        EstacionEntity enBD = entityManager.find(EstacionEntity.class, resultado.getId());
        assertEquals("Central", enBD.getNombre());
        assertEquals("Calle 45 # 10-20", enBD.getDireccion());
    }
}
