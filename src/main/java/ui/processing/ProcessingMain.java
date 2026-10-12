package ui.processing;

import processing.core.PApplet;

public class ProcessingMain {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Missing simulation ID.");
            return;
        }

        ProcessingSketch.setSimulationId(args[0]);

        PApplet.main(ProcessingSketch.class.getName());
    }
}