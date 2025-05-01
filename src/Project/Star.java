package Project;

import com.jogamp.opengl.GL2;

public class Star extends GameObject {
    private float angle = 0.0f;

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
        gl.glScalef(1.0f, 1.0f, 1.0f);

        // Dégradé du centre jaune vers orange/rouge
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glColor3f(1.0f, 1.0f, 0.0f); // Jaune au centre
            gl.glVertex3f(0.0f, 0.0f, 0.0f); // Centre

            for (int i = 0; i <= 10; i++) {
                double angle = i * Math.PI / 5;
                float r = (i % 2 == 0) ? 0.6f : 0.3f;
                float x = (float)(r * Math.cos(angle));
                float y = (float)(r * Math.sin(angle));

                // Dégradé vers rouge
                gl.glColor3f(1.0f, 0.5f, 0.0f);
                gl.glVertex3f(x, y, 0.0f);
            }
        gl.glEnd();

        // Contour doux noir légèrement transparent
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glColor4f(0.0f, 0.0f, 0.0f, 0.3f);
        gl.glLineWidth(2.5f);
        gl.glBegin(GL2.GL_LINE_LOOP);
            for (int i = 0; i < 10; i++) {
                double angle = i * Math.PI / 5;
                float r = (i % 2 == 0) ? 0.6f : 0.3f;
                float x = (float)(r * Math.cos(angle));
                float y = (float)(r * Math.sin(angle));
                gl.glVertex3f(x, y, 0.01f);
            }
        gl.glEnd();
        gl.glDisable(GL2.GL_BLEND);

        gl.glPopMatrix();
    }


    @Override
    public void update() {
        z += 0.1f;
        angle += 1.5f; // fait tourner l’étoile

        if (z > 5.0f) {
            reset();
        }
    }

    private void reset() {
        z = -50.0f;
        passed = false;
    }
}
