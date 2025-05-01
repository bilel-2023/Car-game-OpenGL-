package Project;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera {
    private Car car;
    private float eyeX, eyeY, eyeZ;
    private float centerX, centerY, centerZ;

    public Camera(Car car) {
        this.car = car;
        // Initial camera setup
        update();
    }

    public void update() {
        // Smooth follow, interpolate the camera position to create a smoother movement
        eyeX += (car.getX() - eyeX) * 0.1f;
        eyeY += (car.getY() + 2.0f - eyeY) * 0.1f;
        eyeZ += (car.getZ() + 5.0f - eyeZ) * 0.1f;

        // Look ahead of the car
        centerX = car.getX();
        centerY = car.getY();
        centerZ = car.getZ() - 10.0f;
    }


    public void applyView(GL2 gl) {
        GLU glu = new GLU();
        gl.glLoadIdentity();
        glu.gluLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, 0, 1, 0);
    }
}
