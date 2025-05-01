package Project;

import com.jogamp.opengl.GL2;

public class Person extends GameObject {
    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glScalef(1.2f, 1.2f, 1.2f); // Zoom léger

        // Tête (cercle)
        gl.glColor3f(1.0f, 0.8f, 0.6f);
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glVertex3f(0.0f, 1.0f, 0.0f); // centre
            for (int i = 0; i <= 360; i += 10) {
                double angle = Math.toRadians(i);
                float x = (float)(0.15f * Math.cos(angle));
                float y = (float)(0.15f * Math.sin(angle)) + 1.0f;
                gl.glVertex3f(x, y, 0.0f);
            }
        gl.glEnd();

        // Nez (petit triangle)
        gl.glColor3f(1.0f, 0.6f, 0.4f);
        gl.glBegin(GL2.GL_TRIANGLES);
            gl.glVertex3f(0.0f, 0.95f, 0.0f);
            gl.glVertex3f(0.05f, 0.93f, 0.0f);
            gl.glVertex3f(-0.05f, 0.93f, 0.0f);
        gl.glEnd();

        // Cou
        gl.glColor3f(1.0f, 0.8f, 0.6f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3f(-0.05f, 0.9f, 0.0f);
            gl.glVertex3f(0.05f, 0.9f, 0.0f);
            gl.glVertex3f(0.05f, 0.85f, 0.0f);
            gl.glVertex3f(-0.05f, 0.85f, 0.0f);
        gl.glEnd();

        // Corps (torse)
        gl.glColor3f(0.0f, 0.5f, 1.0f); // bleu clair
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3f(-0.2f, 0.5f, 0.0f);
            gl.glVertex3f(0.2f, 0.5f, 0.0f);
            gl.glVertex3f(0.2f, 0.85f, 0.0f);
            gl.glVertex3f(-0.2f, 0.85f, 0.0f);
        gl.glEnd();

        // Bras gauche
        gl.glColor3f(1.0f, 0.8f, 0.6f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3f(-0.35f, 0.75f, 0.0f);
            gl.glVertex3f(-0.2f, 0.75f, 0.0f);
            gl.glVertex3f(-0.2f, 0.55f, 0.0f);
            gl.glVertex3f(-0.35f, 0.55f, 0.0f);
        gl.glEnd();

        // Bras droit
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3f(0.2f, 0.75f, 0.0f);
            gl.glVertex3f(0.35f, 0.75f, 0.0f);
            gl.glVertex3f(0.35f, 0.55f, 0.0f);
            gl.glVertex3f(0.2f, 0.55f, 0.0f);
        gl.glEnd();

        // Jambes (pantalon)
        gl.glColor3f(0.1f, 0.1f, 0.1f); // gris foncé
        gl.glBegin(GL2.GL_QUADS);
            // jambe gauche
            gl.glVertex3f(-0.15f, 0.0f, 0.0f);
            gl.glVertex3f(-0.05f, 0.0f, 0.0f);
            gl.glVertex3f(-0.05f, 0.5f, 0.0f);
            gl.glVertex3f(-0.15f, 0.5f, 0.0f);

            // jambe droite
            gl.glVertex3f(0.05f, 0.0f, 0.0f);
            gl.glVertex3f(0.15f, 0.0f, 0.0f);
            gl.glVertex3f(0.15f, 0.5f, 0.0f);
            gl.glVertex3f(0.05f, 0.5f, 0.0f);
        gl.glEnd();

        // Chaussures
        gl.glColor3f(0.3f, 0.0f, 0.0f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3f(-0.15f, -0.05f, 0.0f);
            gl.glVertex3f(-0.05f, -0.05f, 0.0f);
            gl.glVertex3f(-0.05f, 0.0f, 0.0f);
            gl.glVertex3f(-0.15f, 0.0f, 0.0f);

            gl.glVertex3f(0.05f, -0.05f, 0.0f);
            gl.glVertex3f(0.15f, -0.05f, 0.0f);
            gl.glVertex3f(0.15f, 0.0f, 0.0f);
            gl.glVertex3f(0.05f, 0.0f, 0.0f);
        gl.glEnd();

        // Yeux
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glPointSize(4.0f);
        gl.glBegin(GL2.GL_POINTS);
            gl.glVertex3f(-0.05f, 1.05f, 0.0f);
            gl.glVertex3f(0.05f, 1.05f, 0.0f);
        gl.glEnd();

        // Bouche
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(-0.04f, 1.00f, 0.0f);
            gl.glVertex3f(0.04f, 1.00f, 0.0f);
        gl.glEnd();

        gl.glPopMatrix();
    }

    @Override
    public void update() {
        // Move person forward on the z-axis
        z += 0.1f; // Adjust the speed of the person's movement

        // Reset the person position when it passes a threshold
        if (z > 5.0f) {
            reset();
        }
    }

    private void reset() {
        z = -50.0f; // Reset position of the person
        passed = false;
    }
}
