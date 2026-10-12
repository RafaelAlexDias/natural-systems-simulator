package dla;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PApplet.constrain;

public class DLA implements setup.IProcessingApp {

    private boolean paused = false;

    // Lista de walkers
    List<Walker> walkers = new ArrayList<Walker>();

    // Número de walkers em movimento
    private final int NUM_WALKERS = 20;

    // Controle da velocidade
    private int NUM_STEPS_PER_FRAME = 100;

    // Número máximo de walker na figura
    private final int NUM_MAX=100;
    private int nStoppedWalker;


    @Override
    public void setup(PApplet p) {
        // Escolher como quer a inicialização e descomentar o código correspondente
        // (e comentar se houver outro descomentado)

        // Para criar apenas um particula central
		Walker walker;
		walker = new Walker(p, p.width / 2, p.height / 2);
		walkers.add(walker);

		for(int i = 0; i != NUM_WALKERS; ++i) {
            if(!paused) {
                walker = new Walker(p);
                walkers.add(walker);
            }
		}

        /*
        // Para criar em linha reta:
        Walker walker;
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                walker = new Walker(p, p.width / 2 - 10, p.height / 2);
                walkers.add(walker);
            }
            if (i == 1) {
                walker = new Walker(p, p.width / 2, p.height / 2);
                walkers.add(walker);
            }
            if (i == 2) {
                walker = new Walker(p, p.width / 2 + 10, p.height / 2);
                walkers.add(walker);
            }
        }

        for(int i = 0; i != NUM_WALKERS; ++i) {
            walker = new Walker(p);
            walkers.add(walker);
        }
        */


        // Para criar em retângulo:
		/*
        // Tamanho do retângulo
        int rectWidth = 5;
        int rectHeight = 20;

        // Calcula a posição inicial para que o retângulo seja centrado
        int startX = p.width / 2 - (rectWidth * 10) / 2;
        int startY = p.height / 2 - (rectHeight * 10) / 2;

        // Adiciona walkers para formar um retângulo centrado
        for (int i = 0; i < rectWidth; i++) {
            for (int j = 0; j < rectHeight; j++) {
                Walker walker = new Walker(p, startX + i * 10, startY + j * 10);
                walkers.add(walker);
            }
        }

        // Adiciona walkers adicionais se necessário
        for (int i = 0; i < NUM_WALKERS - (rectWidth * rectHeight); i++) {
            Walker walker = new Walker(p);
            walkers.add(walker);
        }
        */
    }

    @Override
    public void draw(PApplet p, float dt) {
        for (int i = 0; i != NUM_STEPS_PER_FRAME; ++i) {
            p.background(0);

            // Atualiza o estado e a posição dos walkers
            for (Walker w : walkers) {
                if(!paused) {
                    w.updateState(walkers);
                    w.wander(p);
                }
            }

            // Faz display aos walkers
            for (Walker w : walkers) {
                if (w.getState() == Walker.State.WANDER) {
                    // Define a cor para walkers em movimento
                    p.fill(50, 168, 149);
                    w.display(p);
                } else {
                    // Define a cor para walkers parados
                    p.fill(119, 50, 168);
                    w.display(p);
                }
            }
        }

        int aux = updateNumstopped();
        if (nStoppedWalker != aux && NUM_MAX!=(nStoppedWalker+NUM_WALKERS) && !paused) {
            nStoppedWalker = aux;
            Walker wplus = new Walker(p);
            walkers.add(wplus);
        }
    }

    public int updateNumstopped(){
        int num=0;
        for(Walker w : walkers) {
            if (w.getState()== Walker.State.STOPPED){
                num++;
            }
        }
        return num;
    }

    @Override
    public void keyPressed(PApplet parent) {
        if (parent.key == ' ') {
            paused = !paused;
        }
        // Controle de velocidade - diminui
        if (parent.key == 's') {
            NUM_STEPS_PER_FRAME = constrain(NUM_STEPS_PER_FRAME + 10, 50, 150);
        }
        // Controle de velocidade - aumenta
        if (parent.key == 'w') {
            NUM_STEPS_PER_FRAME = constrain(NUM_STEPS_PER_FRAME - 10, 50, 300);
        }
    }

    @Override
    public void mousePressed(PApplet parent) {
        // Gera outra vez os walkers
        setup(parent);
    }

    @Override
    public void mouseDragged(PApplet parent) {
    }
}
