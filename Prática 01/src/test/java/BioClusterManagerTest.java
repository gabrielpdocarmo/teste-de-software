import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BioClusterManagerTest {

    private final BioClusterManager manager = new BioClusterManager();

    @Test
    void deveRetornarListaVaziaQuandoMenosDeDuasObservacoes() {
        List<Observation> obs = List.of(
                new Observation(1, 1, 0, 0, 90, false)
        );

        List<String> resultado = manager.processarClusters(obs, 10, 50, false, 10);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveCriarClusterValido() {
        List<Observation> obs = List.of(
                new Observation(1, 1, 0, 0, 90, false),
                new Observation(2, 1, 3, 4, 80, false)
        );

        List<String> resultado = manager.processarClusters(obs, 10, 50, false, 10);

        assertEquals(List.of("Cluster:1-2"), resultado);
    }

    @Test
    void naoDeveCriarClusterForaDoRaio() {
        List<Observation> obs = List.of(
                new Observation(1, 1, 0, 0, 90, false),
                new Observation(2, 1, 20, 20, 80, false)
        );

        List<String> resultado = manager.processarClusters(obs, 10, 50, false, 10);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void naoDeveCriarClusterEspeciesDiferentesSemModoInter() {
        List<Observation> obs = List.of(
                new Observation(1, 1, 0, 0, 90, false),
                new Observation(2, 2, 3, 4, 80, false)
        );

        List<String> resultado = manager.processarClusters(obs, 10, 50, false, 10);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveCriarClusterComModoInter() {
        List<Observation> obs = List.of(
                new Observation(1, 1, 0, 0, 90, false),
                new Observation(2, 2, 3, 4, 80, false)
        );

        List<String> resultado = manager.processarClusters(obs, 10, 50, true, 10);

        assertEquals(List.of("Cluster:1-2"), resultado);
    }

    @Test
    void naoDeveCriarClusterSeAmbasInvasoras() {
        List<Observation> obs = List.of(
                new Observation(1, 1, 0, 0, 90, true),
                new Observation(2, 1, 3, 4, 80, true)
        );

        List<String> resultado = manager.processarClusters(obs, 10, 50, false, 10);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveEvidenciarErroNaSaude() {
        List<Observation> obs = List.of(
                new Observation(1, 1, 0, 0, 40, false),
                new Observation(2, 1, 3, 4, 90, false)
        );

        List<String> resultado = manager.processarClusters(obs, 10, 50, false, 10);

        assertEquals(List.of("Cluster:1-2"), resultado);
    }
}