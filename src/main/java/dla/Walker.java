package dla;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;

public class Walker {

	// Estados de um Walker
	public enum State {
		STOPPED,
		WANDER
	}

	// Posição de um Walker
	private PVector pos;

	// Raio de um Walker
	private final int radius = 5;

	// Estado atual de um Walker
	private State state;

	// Método para obter o estado atual de um Walker
	public State getState() {
		return state;
	}

	// Método para atualizar o estado de um Walker com base na interação com outros Walkers
	public void updateState(List<Walker> walkers) {
		// Se o estado for STOPPED, não há necessidade de verificar
		if (state == State.STOPPED) return;

		// Loop através de outros Walkers
		for (Walker w : walkers) {
			// Verifica se o Walker atual está parado
			if (w.state == State.STOPPED) {
				// Calcula a distância entre os Walkers
				float dist = PVector.dist(pos, w.pos);
				// Se a distância for menor que duas vezes o raio, define o estado como STOPPED
				if (dist < 2 * radius) {
					state = State.STOPPED;
					break;
				}
			}
		}
	}

	// Construtor para criar um Walker parado em uma posição específica
	public Walker(PApplet p, int x, int y) {
		pos = new PVector(x, y);
		state = State.STOPPED;
	}

	// Construtor para criar um Walker em uma posição aleatória para movimento aleatório
	public Walker(PApplet p) {
		pos = new PVector(p.random(p.width), p.random(p.height));
		state = State.WANDER;
	}

	// Método para o movimento aleatório
	public void wander(PApplet p) {
		if (state == State.WANDER) {
			PVector step = PVector.random2D();
			pos.add(step);
			pos.lerp(new PVector(p.width / 2, p.height / 2), 0.0002f);
			pos.x = PApplet.constrain(pos.x, 0, p.width);
			pos.y = PApplet.constrain(pos.y, 0, p.height);
		}
	}

	// Método para desenhar um Walker
	public void display(PApplet p) {
		p.circle(pos.x, pos.y, radius * 2);
	}

	public boolean isEquals(Walker w){
		return this.pos==w.pos && this.state==w.state;
	}
}


