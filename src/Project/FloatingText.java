package Project;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class FloatingText {
    private String text;
    private float x, y;
    private long startTime;
    private int duration;

    public FloatingText(String text, float x, float y, int duration) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - startTime > duration;
    }

    public void render(GL2 gl, int width, int height) {
        if (isExpired()) return;

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        new GLU().gluOrtho2D(0, width, 0, height);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();

        gl.glColor3f(1.0f, 1.0f, 0.0f); // jaune
        TextRendererHelper.drawText(gl, text, (int)x, (int)y);

        gl.glPopMatrix();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
}
