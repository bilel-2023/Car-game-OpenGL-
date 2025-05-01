package Project;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class Money extends GameObject {

    private float rotationAngle = 0.0f;
    private float bounceOffset = 0.0f;
    private boolean bounceUp = true;
    private boolean isHovered = false;

    private static final GLUT glut = new GLUT();
    private static final GLU glu = new GLU();

    private float glowPulse = 0.0f;
    private boolean glowIncreasing = true;

    private final float coinRadius = 0.3f;
    private final float coinThickness = 0.05f;

    public Money() {
        this.x = getRandomX();
        this.y = 0.5f;
        this.z = -100.0f;
        this.passed = false;
    }

    @Override
    public void update() {
        z += 0.3f;
        rotationAngle = (rotationAngle + 1.5f) % 360.0f;

        if (bounceUp) {
            bounceOffset += 0.005f;
            if (bounceOffset > 0.06f) bounceUp = false;
        } else {
            bounceOffset -= 0.005f;
            if (bounceOffset < -0.06f) bounceUp = true;
        }

        if (Math.random() < 0.01) {
            isHovered = !isHovered;
        }

        // Smooth glow effect
        if (glowIncreasing) {
            glowPulse += 0.01f;
            if (glowPulse > 0.3f) glowIncreasing = false;
        } else {
            glowPulse -= 0.01f;
            if (glowPulse < 0.0f) glowIncreasing = true;
        }
    }

    @Override
    public void draw(GL2 gl) {
        setupLighting(gl);

        gl.glPushMatrix();
        gl.glTranslatef(x, y + bounceOffset, z);
        gl.glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f);

        drawCoin(gl);
        gl.glPopMatrix();

        gl.glDisable(GL2.GL_LIGHTING);
        drawFloatingText(gl);
    }

    private void setupLighting(GL2 gl) {
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);

        float[] lightPosition = {5.0f, 10.0f, 5.0f, 1.0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);

        float[] ambient, diffuse, specular;
        float shininess = 100.0f;

        if (isHovered) {
            ambient = new float[]{0.5f + glowPulse, 0.5f + glowPulse, 1.0f, 1.0f};
            diffuse = new float[]{0.6f + glowPulse, 0.6f + glowPulse, 1.0f, 1.0f};
            specular = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
        } else {
            ambient = new float[]{0.9f, 0.85f, 0.4f, 1.0f};
            diffuse = new float[]{0.95f, 0.85f, 0.3f, 1.0f};
            specular = new float[]{1.0f, 1.0f, 0.8f, 1.0f};
        }

        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, ambient, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, specular, 0);
        gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, shininess);
    }

    private void drawCoin(GL2 gl) {
        int segments = 80;
        int ridges = 120;

        drawFace(gl, coinRadius, coinThickness, segments, true);
        drawFace(gl, coinRadius, coinThickness, segments, false);
        drawEdgeWithRidges(gl, coinRadius, coinThickness, ridges);
        drawEngraving(gl, coinRadius * 0.65f, coinThickness / 2);
    }

    private void drawFace(GL2 gl, float radius, float thickness, int segments, boolean front) {
        float normalZ = front ? 1.0f : -1.0f;
        float z = front ? thickness / 2 : -thickness / 2;

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glNormal3f(0.0f, 0.0f, normalZ);
        gl.glVertex3f(0.0f, 0.0f, z);

        for (int i = 0; i <= segments; i++) {
            double angle = 2 * Math.PI * i / segments;
            float x = (float) Math.cos(angle) * radius;
            float y = (float) Math.sin(angle) * radius;
            gl.glVertex3f(x, y, z);
        }
        gl.glEnd();
    }

    private void drawEdgeWithRidges(GL2 gl, float radius, float thickness, int ridges) {
        float ridgeDepth = 0.008f;

        gl.glBegin(GL2.GL_QUAD_STRIP);
        for (int i = 0; i <= ridges; i++) {
            double angle = 2 * Math.PI * i / ridges;
            float x = (float) Math.cos(angle);
            float y = (float) Math.sin(angle);

            float r = (i % 2 == 0) ? radius : radius - ridgeDepth;

            float x1 = x * r;
            float y1 = y * r;

            gl.glNormal3f(x, y, 0.0f);
            gl.glVertex3f(x1, y1, -thickness / 2);
            gl.glVertex3f(x1, y1, thickness / 2);
        }
        gl.glEnd();
    }

    private void drawEngraving(GL2 gl, float radius, float z) {
        int petals = 12;
        float innerRadius = radius * 0.4f;

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(0.0f, 0.0f, z + 0.002f);

        for (int i = 0; i <= petals; i++) {
            double angle = 2 * Math.PI * i / petals;
            float r = (i % 2 == 0) ? innerRadius : radius;
            float x = (float) Math.cos(angle) * r;
            float y = (float) Math.sin(angle) * r;
            gl.glVertex3f(x, y, z + 0.002f);
        }
        gl.glEnd();
    }

    private void drawFloatingText(GL2 gl) {
        gl.glPushMatrix();

        gl.glTranslatef(x, y + bounceOffset + 0.6f, z);
        gl.glScalef(0.0025f, 0.0025f, 0.0025f);

        gl.glColor3f(1.0f, 1.0f, 0.9f);

        glut.glutStrokeString(GLUT.STROKE_MONO_ROMAN, "+4");

        gl.glPopMatrix();
    }

    private float getRandomX() {
        return (float) (Math.random() * 4.0 - 2.0);
    }
}
