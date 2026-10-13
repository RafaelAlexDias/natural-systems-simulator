package aa;

import processing.core.PVector;

public interface IBehavior {
    // Método para obter a velocidade desejada com base no objeto Boid fornecido
    public PVector getDesiredVelocity(Boid me);
    // Método para definir o peso do comportamento
    public void setWeight(float weight);
    // Método para obter o peso do comportamento
    public float getWeight();
}
