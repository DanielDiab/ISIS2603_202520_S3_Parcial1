package co.edu.uniandes.dse.parcial1.services;


import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import java.util.ArrayList;
import java.util.List;



@DataJpaTest
@Transactional
@Import(EstacionRutaService.class)
class EstacionRutaServiceTest {

    @Autowired
    private EstacionRutaService service;

    @Autowired
    private TestEntityManager entityManager;

    private final PodamFactory factory = new PodamFactoryImpl();
    private final List<RutaEntity> rutas = new ArrayList<>();
    private final List<EstacionEntity> estaciones = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from RutaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EstacionEntity").executeUpdate();
        rutas.clear();
        estaciones.clear();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            RutaEntity r = factory.manufacturePojo(RutaEntity.class);
            r.setNombre("Ruta_" + i);
            entityManager.persist(r);
            rutas.add(r);

            EstacionEntity e = factory.manufacturePojo(EstacionEntity.class);
            e.setNombre("Estación_" + i);
            entityManager.persist(e);
            estaciones.add(e);
        }
    }

    // ============ addEstacionRuta ============

    @Test
    void testAddEstacionRuta_ok() throws EntityNotFoundException, IllegalOperationException {
        RutaEntity ruta = rutas.get(0);
        EstacionEntity estacion = estaciones.get(0);

        RutaEntity result = service.addEstacionRuta(estacion.getId(), ruta.getId());

        assertNotNull(result);
        assertTrue(result.getEstaciones().contains(estacion));

        RutaEntity enBD = entityManager.find(RutaEntity.class, ruta.getId());
        assertTrue(enBD.getEstaciones().contains(estacion));
    }

    @Test
    void testAddEstacionRuta_estacionNoExiste_debeFallar() {
        RutaEntity ruta = rutas.get(0);

        assertThrows(EntityNotFoundException.class, () ->
                service.addEstacionRuta(999999L, ruta.getId()));
    }

    @Test
    void testAddEstacionRuta_rutaNoExiste_debeFallar() {
        EstacionEntity estacion = estaciones.get(0);

        assertThrows(EntityNotFoundException.class, () ->
                service.addEstacionRuta(estacion.getId(), 999999L));
    }

    @Test
    void testAddEstacionRuta_yaAsociada_debeFallar() throws EntityNotFoundException, IllegalOperationException {
        RutaEntity ruta = rutas.get(1);
        EstacionEntity estacion = estaciones.get(1);

        service.addEstacionRuta(estacion.getId(), ruta.getId());

        IllegalOperationException ex = assertThrows(IllegalOperationException.class, () ->
                service.addEstacionRuta(estacion.getId(), ruta.getId()));
        assertTrue(ex.getMessage().toLowerCase().contains("ya"));
    }

    // ============ removeEstacionRuta ============

    @Test
    void testRemoveEstacionRuta_ok() throws EntityNotFoundException, IllegalOperationException {
        RutaEntity ruta = rutas.get(2);
        EstacionEntity estacion = estaciones.get(2);

        service.addEstacionRuta(estacion.getId(), ruta.getId());

        RutaEntity result = service.removeEstacionRuta(estacion.getId(), ruta.getId());

        assertFalse(result.getEstaciones().contains(estacion));

        RutaEntity enBD = entityManager.find(RutaEntity.class, ruta.getId());
        assertFalse(enBD.getEstaciones().contains(estacion));
    }

    @Test
    void testRemoveEstacionRuta_estacionNoExiste_debeFallar() {
        RutaEntity ruta = rutas.get(0);

        assertThrows(EntityNotFoundException.class, () ->
                service.removeEstacionRuta(0L, ruta.getId()));
    }

    @Test
    void testRemoveEstacionRuta_rutaNoExiste_debeFallar() {
        EstacionEntity estacion = estaciones.get(0);

        assertThrows(EntityNotFoundException.class, () ->
                service.removeEstacionRuta(estacion.getId(), 0L));
    }

    @Test
    void testRemoveEstacionRuta_noAsociada_debeFallar() {
        RutaEntity ruta = rutas.get(1);
        EstacionEntity estacion = estaciones.get(2);

        assertThrows(IllegalOperationException.class, () ->
                service.removeEstacionRuta(estacion.getId(), ruta.getId()));
    }
}
