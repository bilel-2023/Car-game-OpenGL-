package Project;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;

import java.awt.event.KeyEvent;

public class Car extends GameObject {
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private float speed = 0.3f;
    private float maxX = 5.0f;
    private final GLU glu = new GLU();
    private final GLUT glut = new GLUT();

    public Car() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    @Override
    public void update() {
        if (moveLeft && x > -maxX) x -= speed;
        if (moveRight && x < maxX) x += speed;
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);

        // Add a slight rotation to make it look like the car is moving in the direction of the arrow key
        if (moveLeft) {
            gl.glRotatef(15.0f, 0, 1, 0);  // Rotate to the left when moving left
        }
        if (moveRight) {
            gl.glRotatef(-15.0f, 0, 1, 0); // Rotate to the right when moving right
        }

        // Draw car parts
        drawBody(gl);
        drawCabin(gl);
        drawWindshield(gl);
        drawWindows(gl);
        drawWheels(gl);
        drawRims(gl);
        drawHeadlights(gl);
        drawTaillights(gl);
        drawExhaust(gl);
        drawBumpers(gl);
        drawMirrors(gl);
        drawGrille(gl);
        drawSpoiler(gl);
        drawDoorHandles(gl);

        gl.glPopMatrix();
    }


    private void drawBody(GL2 gl) {
        gl.glPushMatrix();
        gl.glColor3f(0.2f, 0.4f, 0.7f);
        gl.glTranslatef(0.0f, 0.2f, 0.0f);
        gl.glScalef(1.6f, 0.3f, 3.2f);
        glut.glutSolidCube(1.0f);
        gl.glPopMatrix();
    }

    private void drawCabin(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.55f, 0.4f);
        gl.glColor3f(0.1f, 0.2f, 0.4f);
        gl.glScalef(1.2f, 0.5f, 1.5f);
        glut.glutSolidCube(1.0f);
        gl.glPopMatrix();
    }

    private void drawWindshield(GL2 gl) {
        gl.glPushMatrix();
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glColor4f(0.6f, 0.9f, 1.0f, 0.5f);
        gl.glTranslatef(0.0f, 0.65f, 0.95f);
        gl.glRotatef(-30.0f, 1, 0, 0);
        gl.glScalef(1.1f, 0.02f, 0.5f);
        glut.glutSolidCube(1.0f);
        gl.glDisable(GL2.GL_BLEND);
        gl.glPopMatrix();
    }

    private void drawWindows(GL2 gl) {
        gl.glPushMatrix();
        gl.glColor4f(0.6f, 0.8f, 1.0f, 0.4f);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        float[][] windowPos = {
            {-0.6f, 0.55f, 0.4f}, {0.6f, 0.55f, 0.4f}
        };

        for (float[] pos : windowPos) {
            gl.glPushMatrix();
            gl.glTranslatef(pos[0], pos[1], pos[2]);
            gl.glScalef(0.3f, 0.3f, 1.2f);
            glut.glutSolidCube(1.0f);
            gl.glPopMatrix();
        }

        gl.glDisable(GL2.GL_BLEND);
        gl.glPopMatrix();
    }

    private void drawWheels(GL2 gl) {
        float[][] positions = {
            {-0.9f, -0.05f, 1.4f}, {0.9f, -0.05f, 1.4f},
            {-0.9f, -0.05f, -1.4f}, {0.9f, -0.05f, -1.4f}
        };

        for (float[] pos : positions) {
            drawWheel(gl, pos[0], pos[1], pos[2]);
        }
    }

    private void drawWheel(GL2 gl, float offsetX, float offsetY, float offsetZ) {
        gl.glPushMatrix();
        gl.glTranslatef(offsetX, offsetY, offsetZ);
        gl.glRotatef(90, 0, 1, 0);
        gl.glColor3f(0.15f, 0.15f, 0.15f);
        glut.glutSolidTorus(0.1f, 0.3f, 20, 30);

        // Bolt center
        gl.glColor3f(0.7f, 0.7f, 0.7f);
        glut.glutSolidSphere(0.05f, 10, 10);
        gl.glPopMatrix();
    }

    private void drawRims(GL2 gl) {
        float[][] positions = {
            {-0.9f, -0.05f, 1.4f}, {0.9f, -0.05f, 1.4f},
            {-0.9f, -0.05f, -1.4f}, {0.9f, -0.05f, -1.4f}
        };

        for (float[] pos : positions) {
            gl.glPushMatrix();
            gl.glTranslatef(pos[0], pos[1], pos[2]);
            gl.glRotatef(90, 0, 1, 0);
            gl.glColor3f(0.9f, 0.9f, 0.9f); // silver
            glut.glutSolidCylinder(0.05f, 0.05f, 10, 10);
            gl.glPopMatrix();
        }
    }

    private void drawHeadlights(GL2 gl) {
        gl.glPushMatrix();
        gl.glColor3f(1.0f, 1.0f, 0.8f);
        float[][] pos = {
            {-0.5f, 0.25f, 1.6f}, {0.5f, 0.25f, 1.6f}
        };
        for (float[] p : pos) {
            gl.glPushMatrix();
            gl.glTranslatef(p[0], p[1], p[2]);
            glut.glutSolidSphere(0.1f, 10, 10);
            gl.glPopMatrix();
        }
        gl.glPopMatrix();
    }

    private void drawTaillights(GL2 gl) {
        gl.glPushMatrix();
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        float[][] pos = {
            {-0.5f, 0.25f, -1.6f}, {0.5f, 0.25f, -1.6f}
        };
        for (float[] p : pos) {
            gl.glPushMatrix();
            gl.glTranslatef(p[0], p[1], p[2]);
            glut.glutSolidSphere(0.1f, 10, 10);
            gl.glPopMatrix();
        }
        gl.glPopMatrix();
    }

    private void drawExhaust(GL2 gl) {
        gl.glPushMatrix();
        gl.glColor3f(0.3f, 0.3f, 0.3f);
        gl.glTranslatef(-0.3f, 0.1f, -1.65f);
        gl.glRotatef(90, 1, 0, 0);
        GLUquadric quad = glu.gluNewQuadric();
        glu.gluCylinder(quad, 0.05f, 0.05f, 0.3f, 10, 10);
        glu.gluDeleteQuadric(quad);
        gl.glPopMatrix();
    }

    private void drawBumpers(GL2 gl) {
        gl.glPushMatrix();
        gl.glColor3f(0.2f, 0.2f, 0.2f);
        // Front
        gl.glTranslatef(0.0f, 0.2f, 1.65f);
        gl.glScalef(1.5f, 0.2f, 0.2f);
        glut.glutSolidCube(1.0f);
        gl.glPopMatrix();

        gl.glPushMatrix();
        // Back
        gl.glTranslatef(0.0f, 0.2f, -1.65f);
        gl.glScalef(1.5f, 0.2f, 0.2f);
        glut.glutSolidCube(1.0f);
        gl.glPopMatrix();
    }

    private void drawMirrors(GL2 gl) {
        float[][] pos = {{-0.85f, 0.6f, 0.6f}, {0.85f, 0.6f, 0.6f}};
        for (float[] p : pos) {
            gl.glPushMatrix();
            gl.glColor3f(0.6f, 0.6f, 0.6f);
            gl.glTranslatef(p[0], p[1], p[2]);
            gl.glScalef(0.1f, 0.05f, 0.2f);
            glut.glutSolidCube(1.0f);
            gl.glPopMatrix();
        }
    }

    private void drawGrille(GL2 gl) {
        gl.glPushMatrix();
        gl.glColor3f(0.2f, 0.2f, 0.2f);
        gl.glTranslatef(0.0f, 0.25f, 1.61f);
        gl.glScalef(0.6f, 0.1f, 0.02f);
        glut.glutSolidCube(1.0f);
        gl.glPopMatrix();
    }

    private void drawSpoiler(GL2 gl) {
        gl.glPushMatrix();
        gl.glColor3f(0.2f, 0.2f, 0.2f);
        gl.glTranslatef(0.0f, 0.65f, -1.5f); // Position spoiler
        gl.glScalef(0.8f, 0.05f, 0.3f); // Taille spoiler
        glut.glutSolidCube(1.0f);
        gl.glPopMatrix();
    }


    private void drawDoorHandles(GL2 gl) {
        float[][] pos = {{-0.5f, 0.4f, 0.2f}, {0.5f, 0.4f, 0.2f}};
        for (float[] p : pos) {
            gl.glPushMatrix();
            gl.glColor3f(0.1f, 0.1f, 0.1f);
            gl.glTranslatef(p[0], p[1], p[2]);
            gl.glScalef(0.2f, 0.05f, 0.02f);
            glut.glutSolidCube(1.0f);
            gl.glPopMatrix();
        }
    }

    public void handleKeyPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) moveLeft = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) moveRight = true;
    }

    public void handleKeyRelease(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) moveLeft = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) moveRight = false;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
}
