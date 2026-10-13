package core;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimulationsTest {

    @Test
    void containsAtLeastOneSimulation() {
        assertFalse(Simulations.all().isEmpty());
    }

    @Test
    void identifiersAreUnique() {
        Set<String> seen = new HashSet<>();

        for (Simulation simulation : Simulations.all()) {
            assertTrue(
                    seen.add(simulation.id()),
                    "Duplicate simulation id: " + simulation.id()
            );
        }
    }

    @Test
    void byIdResolvesEveryEntry() {
        for (Simulation simulation : Simulations.all()) {
            assertEquals(
                    simulation,
                    Simulations.byId(simulation.id()).orElseThrow()
            );
        }
    }

    @Test
    void unknownIdentifierReturnsEmpty() {
        assertTrue(Simulations.byId("does-not-exist").isEmpty());
    }

    @Test
    void runnableSimulationsCanBeCreated() {
        assertFalse(Simulations.runnable().isEmpty());

        Simulations.runnable().forEach(
                simulation -> assertDoesNotThrow(simulation::create)
        );
    }

    @Test
    void nonRunnableSimulationCannotBeCreated() {
        Simulations.all().stream()
                .filter(simulation -> !simulation.isRunnable())
                .forEach(simulation -> assertThrows(
                        IllegalStateException.class,
                        simulation::create
                ));
    }
}
