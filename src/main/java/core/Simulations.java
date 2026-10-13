package core;

import aa.BoidAceleradorTravaoApp;
import aa.BoidApp;
import aa.BoidWanderSeekApp;
import aa.DebuggingApp;
import aa.FlockPredatorApp;
import aa.FlockWASDApp;
import aa.ReynoldsTestApp;
import chaos_game.ChaosGame;
import chaos_game.PentagonChaosGame;
import chaos_game.XSidesChaosGame;
import dla.DLA;
import physics.ControlGUIApp;
import physics.FallingBodyApp;
import physics.ParticleSystemApp;
import physics.SolarSystemApp;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Central registry of every simulation known by the application.
 *
 * <p>This replaces the several hard-coded switch statements and duplicated
 * lists that used to live in {@code ProcessingSketch}, {@code HomeController}
 * and {@code SimulationController}. Adding a new simulation now means adding a
 * single entry here.
 */
public final class Simulations {

    private static final List<Simulation> ALL = List.of(
            new Simulation(
                    "chaos-game",
                    "Chaos Game - Triangle",
                    "Chaos Game",
                    "Generate geometric fractals by repeatedly moving towards "
                            + "randomly selected vertices of a triangle.",
                    "\u25B3",
                    "/images/simulations/chaos-game.png",
                    LocalDate.of(2026, 10, 9),
                    ChaosGame::new
            ),
            new Simulation(
                    "pentagon-chaos-game",
                    "Chaos Game - Pentagon",
                    "Chaos Game",
                    "Explore fractal patterns generated using the vertices "
                            + "of a pentagon.",
                    "\u2B20",
                    null,
                    LocalDate.of(2026, 10, 8),
                    PentagonChaosGame::new
            ),
            new Simulation(
                    "x-sides-chaos-game",
                    "Chaos Game - Variable Sides",
                    "Chaos Game",
                    "Experiment with chaos-game rules using polygons with "
                            + "different numbers of sides (press 5-9).",
                    "\u2B21",
                    null,
                    LocalDate.of(2026, 10, 7),
                    XSidesChaosGame::new
            ),
            new Simulation(
                    "dla-simulation",
                    "DLA",
                    "Fractals",
                    "Diffusion-Limited Aggregation forms branching structures "
                            + "as randomly moving particles stick together.",
                    "DLA",
                    "/images/simulations/dla.png",
                    LocalDate.of(2026, 10, 10),
                    DLA::new
            ),
            new Simulation(
                    "boid-acelerator-app",
                    "BoidAceleradorTravao",
                    "AA",
                    "Boids simulation",
                    "Boid",
                    null,
                    LocalDate.of(2026, 10, 13),
                    BoidAceleradorTravaoApp::new
            ),
            new Simulation(
                    "boid-app",
                    "Boid - Wander",
                    "AA",
                    "A single autonomous agent wandering inside a bounded world.",
                    "BOID",
                    null,
                    LocalDate.of(2026, 10, 13),
                    BoidApp::new
            ),
            new Simulation(
                    "boid-wander-seek-app",
                    "Boid - Wander & Seek",
                    "AA",
                    "Combines the wander and seek steering behaviours on a "
                            + "moving target.",
                    "BOID",
                    null,
                    LocalDate.of(2026, 10, 13),
                    BoidWanderSeekApp::new
            ),
            new Simulation(
                    "flock-predator-app",
                    "Flock - Predator",
                    "AA",
                    "A flock of boids evading a predator that pursues the "
                            + "nearest neighbour.",
                    "FLOCK",
                    null,
                    LocalDate.of(2026, 10, 12),
                    FlockPredatorApp::new
            ),
            new Simulation(
                    "flock-wasd-app",
                    "Flock - WASD",
                    "AA",
                    "A flock where one boid is steered manually with the WASD "
                            + "keys.",
                    "FLOCK",
                    null,
                    LocalDate.of(2026, 10, 12),
                    FlockWASDApp::new
            ),
            new Simulation(
                    "reynolds-test-app",
                    "Reynolds Test",
                    "AA",
                    "Three panels exercising wander, pursuit and the seek/flee "
                            + "switch (press T).",
                    "REY",
                    null,
                    LocalDate.of(2026, 10, 12),
                    ReynoldsTestApp::new
            ),
            new Simulation(
                    "debugging-app",
                    "Flock - Debugging",
                    "AA",
                    "Visualises the field of view of a boid and highlights the "
                            + "neighbours it can see.",
                    "FLOCK",
                    null,
                    LocalDate.of(2026, 10, 12),
                    DebuggingApp::new
            ),
            new Simulation(
                    "falling-body-app",
                    "Falling Body",
                    "Physics",
                    "A body falling through air and water, showing the effect of "
                            + "fluid drag.",
                    "PHYS",
                    null,
                    LocalDate.of(2026, 10, 11),
                    FallingBodyApp::new
            ),
            new Simulation(
                    "particle-system-app",
                    "Particle System",
                    "Physics",
                    "Click to spawn particle systems and change their launch "
                            + "direction with the mouse.",
                    "PHYS",
                    null,
                    LocalDate.of(2026, 10, 11),
                    ParticleSystemApp::new
            ),
            new Simulation(
                    "solar-system-app",
                    "Solar System",
                    "Physics",
                    "Newtonian simulation of the Sun and the eight planets "
                            + "(press 1-3 to zoom).",
                    "PHYS",
                    null,
                    LocalDate.of(2026, 10, 11),
                    SolarSystemApp::new
            ),
            new Simulation(
                    "control-gui-app",
                    "Motion Control",
                    "Physics",
                    "A rigid body driven by position, velocity or force control "
                            + "(press P/V/F).",
                    "PHYS",
                    null,
                    LocalDate.of(2026, 10, 11),
                    ControlGUIApp::new
            )
    );

    private static final Map<String, Simulation> BY_ID = buildIndex(ALL);

    private Simulations() {
    }

    private static Map<String, Simulation> buildIndex(List<Simulation> simulations) {
        return simulations.stream().collect(Collectors.toUnmodifiableMap(
                Simulation::id,
                Function.identity(),
                (first, duplicate) -> {
                    throw new IllegalStateException(
                            "Duplicate simulation id: " + first.id()
                    );
                }
        ));
    }

    /**
     * @return every known simulation, in display order, including the ones
     *         that cannot be launched yet.
     */
    public static List<Simulation> all() {
        return ALL;
    }

    /**
     * @return only the simulations that can be launched.
     */
    public static List<Simulation> runnable() {
        return ALL.stream().filter(Simulation::isRunnable).toList();
    }

    public static Optional<Simulation> byId(String id) {
        return Optional.ofNullable(BY_ID.get(id));
    }

    /**
     * @param limit maximum number of entries to return
     * @return the most recently added simulations, newest first
     */
    public static List<Simulation> latest(int limit) {
        return ALL.stream()
                .sorted(Comparator.comparing(Simulation::dateAdded).reversed())
                .limit(limit)
                .toList();
    }
}
