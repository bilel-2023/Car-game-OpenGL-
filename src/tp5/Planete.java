package tp5;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class Planete {
    float rayon, orbite, frequence, r, g, b, angle;
    Planete parent;

    public Planete(float rayon, float orbite, float frequence, float r, float g, float b, Planete parent) {
        this.rayon = rayon;
        this.orbite = orbite;
        this.frequence = frequence;
        this.r = r;
        this.g = g;
        this.b = b;
        this.parent = parent;
        this.angle = 0;
    }

    public void update(float delta) {
        angle += frequence * delta;
    }
}