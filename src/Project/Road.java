package Project;

import com.jogamp.opengl.GL2;

public class Road {
    private float positionZ = 0.0f;
    private float speed = 0.3f;
    private final float resetPositionZ = 500.0f;

    public Road() {
        // Constructeur vide
    }

    public void update() {
        positionZ += speed;
        if (positionZ > resetPositionZ) {
            positionZ = 0.0f;
        }
    }

    public void setSpeed(float newSpeed) {
        speed = newSpeed;
    }

    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, positionZ);  // Translate the road in Z axis without affecting its width

        // ====== Route principale (asphalte) ======
        gl.glColor3f(0.1f, 0.1f, 0.1f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3f(-6.0f, 0.0f, 200.0f);  // Narrow road
            gl.glVertex3f(6.0f, 0.0f, 200.0f);   // Narrow road
            gl.glVertex3f(6.0f, 0.0f, -500.0f);  // Narrow road
            gl.glVertex3f(-6.0f, 0.0f, -500.0f); // Narrow road
        gl.glEnd();

        // ====== Bordures noires foncées ======
        gl.glColor3f(0.05f, 0.05f, 0.05f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3f(-6.2f, 0.0f, 200.0f);  // Adjusted for narrower road
            gl.glVertex3f(-6.0f, 0.0f, 200.0f);
            gl.glVertex3f(-6.0f, 0.0f, -500.0f);
            gl.glVertex3f(-6.2f, 0.0f, -500.0f);

            gl.glVertex3f(6.0f, 0.0f, 200.0f);   // Adjusted for narrower road
            gl.glVertex3f(6.2f, 0.0f, 200.0f);
            gl.glVertex3f(6.2f, 0.0f, -500.0f);
            gl.glVertex3f(6.0f, 0.0f, -500.0f);
        gl.glEnd();

        // ====== Lignes blanches latérales ======
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glLineWidth(4.0f);
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(-5.8f, 0.01f, 200.0f); // Adjusted for narrower road
            gl.glVertex3f(-5.8f, 0.01f, -500.0f);

            gl.glVertex3f(5.8f, 0.01f, 200.0f);  // Adjusted for narrower road
            gl.glVertex3f(5.8f, 0.01f, -500.0f);
        gl.glEnd();

        // ====== Ligne centrale pointillée ======
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glLineWidth(3.0f);
        gl.glBegin(GL2.GL_LINES);
            for (float z = -1000 + positionZ; z < 300 + positionZ; z += 7) {
                gl.glVertex3f(0.0f, 0.02f, z);
                gl.glVertex3f(0.0f, 0.02f, z + 3.5f);
            }
        gl.glEnd();

        // ====== Trottoirs gris clair ======
        gl.glColor3f(0.6f, 0.6f, 0.6f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3f(-7.5f, 0.0f, 200.0f);  // Adjusted for narrower road
            gl.glVertex3f(-6.2f, 0.0f, 200.0f);
            gl.glVertex3f(-6.2f, 0.0f, -500.0f);
            gl.glVertex3f(-7.5f, 0.0f, -500.0f);

            gl.glVertex3f(6.2f, 0.0f, 200.0f);   // Adjusted for narrower road
            gl.glVertex3f(7.5f, 0.0f, 200.0f);
            gl.glVertex3f(7.5f, 0.0f, -500.0f);
            gl.glVertex3f(6.2f, 0.0f, -500.0f);
        gl.glEnd();

        // ====== Lampadaires ======
        for (float z = -500 + positionZ; z < 200 + positionZ; z += 50) {  // Réduit l'intervalle à 10 unités
            // Poteau gauche
            gl.glColor3f(0.4f, 0.4f, 0.4f);
            gl.glBegin(GL2.GL_LINES);
                gl.glVertex3f(-6.0f, 0.0f, z);  // Adjusted for narrower road
                gl.glVertex3f(-6.0f, 1.5f, z);
            gl.glEnd();
            // Lumière gauche
            gl.glColor3f(0.9f, 0.9f, 0.0f);
            gl.glBegin(GL2.GL_POINTS);
                gl.glVertex3f(-6.0f, 1.5f, z);
            gl.glEnd();

            // Poteau droite
            gl.glColor3f(0.4f, 0.4f, 0.4f);
            gl.glBegin(GL2.GL_LINES);
                gl.glVertex3f(6.0f, 0.0f, z);   // Adjusted for narrower road
                gl.glVertex3f(6.0f, 1.5f, z);
            gl.glEnd();
            // Lumière droite
            gl.glColor3f(0.9f, 0.9f, 0.0f);
            gl.glBegin(GL2.GL_POINTS);
                gl.glVertex3f(6.0f, 1.5f, z);
            gl.glEnd();
        }



        gl.glPopMatrix();
    }
}
