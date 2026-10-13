package physics;

import processing.core.PVector;

public class PSControl {
    private float averageAngle;
    private float dispersionAngle;
    private float minVelocity;
    private float maxVelocity;
    private float minLifetime;
    private float maxLifetime;
    private float minRadius;
    private float maxRadius;
    private float flow;
    private int color;

    // Construtor de um "PSControl"
    public PSControl(float[] velControl, float[] lifetime, float[] radius,
                     float flow, int color) {
        setVelParams(velControl);
        setLifetimeParams(lifetime);
        setRadiusParams(radius);
        setFlow(flow);
        setColor(color);
    }

    // Método para definir os parâmetros de velocidade
    public void setVelParams (float[] velControl) {
        averageAngle = velControl[0];
        dispersionAngle = velControl[1];
        minVelocity = velControl[2];
        maxVelocity = velControl[3];
    }

    // Método para definir o fluxo
    public void setFlow(float flow) {
        this.flow = flow;
    }

    // Método para definir a cor
    public void setColor(int color) {
        this.color = color;
    }

    // Método para obter o fluxo
    public float getFlow() {
        return flow;
    }

    // Método para obter a cor
    public int getColor() {
        return color;
    }

    // Método para obter um raio aleatório
    public float getRndRadius() {
        return getRnd(minRadius, maxRadius);
    }

    // Método para obter um tempo de vida aleatório
    public float getRndLifetime() {
        return getRnd(minLifetime, maxLifetime);
    }

    // Método ppara definir os parâmetros do raio
    public void setRadiusParams(float[] radius) {
        minRadius = radius[0];
        maxRadius = radius[1];
    }

    // Método ppara definir os parâmetros do tempo de vida
    public void setLifetimeParams(float[] lifetime) {
        minLifetime = lifetime[0];
        maxLifetime = lifetime[1];
    }

    // Método para obter um vetor de velocidade aleatório dentro dos parâmetros definidos
    public PVector getRndVel() {
        float angle = getRnd(averageAngle - dispersionAngle/2, averageAngle + dispersionAngle/2);
        PVector v = PVector.fromAngle(angle);
        return v.mult(getRnd(minVelocity, maxVelocity));
    }

    // Método estático para obter um número aleatório dentro dos limites
    public static float getRnd(float min, float max) {
        return min + (float) (Math.random() * (max - min));
    }
}
