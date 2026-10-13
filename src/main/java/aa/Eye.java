package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Eye {
    private List<Body> allTrackingBodies;
    private List<Body> farSight;
    private List<Body> nearSight;
    private Boid me;
    protected Body target;

    // Construtor de um "Eye"
    public Eye(Boid me, List<Body> allTrackingBodies) {
        this.me = me;
        this.allTrackingBodies = allTrackingBodies;
        target = allTrackingBodies.get(0);
    }

    public Eye(Boid me, Eye eye) {
        allTrackingBodies = eye.allTrackingBodies;
        this.me = me;
        target = eye.target;
    }

    public List<Body> getAllTrackingBodies() {
        return this.allTrackingBodies;
    }

    public void setAllTrackingBodies(List<Body> allTrackingBodies) {
        this.allTrackingBodies = allTrackingBodies;
    }

    // Método para obter a lista de corpos vistos a uma longa distância
    public List<Body> getFarSight() {
        return farSight;
    }

    // Método para obter a lista de corpos vistos a uma curta distância
    public List<Body> getNearSight() {
        return nearSight;
    }

    // Método para "olhar" e determinar quais corpos estão na visão
    public void look() {
        farSight = new ArrayList<Body>();
        nearSight = new ArrayList<Body>();
        for (Body b : allTrackingBodies) {
            if (farSight(b.getPos())) {
                farSight.add(b);
            }
            if (nearSight(b.getPos())) {
                nearSight.add(b);
            }
        }
    }

    // Método para verificar se um ponto está na visão com base na maxDistance e no maxAngle
    public boolean inSight(PVector t, float maxDistance, float maxAngle) {
        PVector r = PVector.sub(t, me.getPos());
        float d = r.mag();
        float angle = PVector.angleBetween(r, me.getVel());
        return ((d > 0) && (d < maxDistance) && (angle < maxAngle));
    }

    // Método para verficar se um ponto está na visão de longa distância
    private boolean farSight(PVector t) {
        return inSight(t, me.dna.visionDistance, me.dna.visionAngle);
    }

    // Método para verficar se um ponto está na visão de curta distância
    private boolean nearSight(PVector t) {
        return inSight(t, me.dna.visionSafeDistance, (float)Math.PI);
    }

    public Body getClosestBoid() {
        Body closestBoid = null;
        float closestDistance = 1000;
        for(Body b : getBoidsInSight()) {
            float distance = PVector.dist(me.getPos(), b.getPos());
            if(distance <= closestDistance) {
                closestDistance = distance;
                closestBoid = b;
                target = b;
            }
        }
        return closestBoid;
    }

    public Body nextTarget(){
        if (allTrackingBodies.size() == 0){
            return null;
        }
        //começamos por ver a distância entre primeiro o primeiro body e o hunter (no caso do hunter)
        Body body = allTrackingBodies.get(0);
        //vemos a distancia de referencia
        float dist = PVector.sub(me.getPos(), body.getPos()).mag();
        for (Body aux : allTrackingBodies){
            float newdist =  PVector.sub(me.getPos(),aux.getPos()).mag();
            //se a distacia for menor que a anterior atualizamos a menor distancia e atualizamos o body certo
            if (newdist < dist){
                dist =  newdist;
                body = aux;
            }
        }
        target = body;
        return body;
    }

    // Método para obter a lista de "Boids" dentro do campo de visão
    public List<Body> getBoidsInSight() {
        List<Body> boidsInSight = new ArrayList<>();
        for (Body b : allTrackingBodies) {
            if (inSight(b.getPos(), me.dna.visionDistance, me.dna.visionAngle)) {
                boidsInSight.add(b);
            }
        }
        return boidsInSight;
    }

    // Método "display"
    public void display(PApplet p, SubPlot plt) {
        p.pushStyle();
        p.pushMatrix();
        float[] pp = plt.getPixelCoord(me.getPos().x, me.getPos().y);
        p.translate(pp[0], pp[1]);
        p.rotate(-me.getVel().heading());
        p.stroke(0, 255, 0);
        p.strokeWeight(2);
        float velLineLength = 20;
        p.line(0, 0, velLineLength, 0);
        p.noFill();
        p.stroke(255, 0, 0);
        p.strokeWeight(3);
        float[] dd = plt.getVectorCoord(me.dna.visionDistance, me.dna.visionSafeDistance);
        p.rotate(me.dna.visionAngle);
        p.line(0, 0, dd[0], 0);
        p.rotate(-2 * me.dna.visionAngle);
        p.line(0, 0, dd[0], 0);
        p.rotate(me.dna.visionAngle);
        p.arc(0, 0, 2 * dd[0], 2 * dd[0], -me.dna.visionAngle, me.dna.visionAngle);
        p.stroke(255, 0, 255);
        p.circle(0, 0, 2 * dd[1]);
        p.popMatrix();
        p.popStyle();
    }
}