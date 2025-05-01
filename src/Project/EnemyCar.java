package Project;

import com.jogamp.opengl.GL2;
import java.util.Random;

public class EnemyCar extends GameObject {
    private float speed;
    private Random random;
    private GameObject visualObject;  // Star, Dog, or Person

    public EnemyCar() {
        random = new Random();
        x = (random.nextFloat() - 0.5f) * 8;
        y = 0.0f;
        z = -50.0f;
        speed = 0.2f;
        passed = false;

        spawnRandomObject();
    }

    private void spawnRandomObject() {
        int type = random.nextInt(2); 
        switch (type) {
            case 0:
                visualObject = new Star();
                visualObject.setY(y +0.5f); // Placer la Star plus haut
                break;
            case 1:
                visualObject = new Person();
                visualObject.setY(y); // Même hauteur que la route
                break;
        }

        visualObject.setX(x);
        visualObject.setZ(z);
    }



    @Override
    public void update() {
        z += speed;
        visualObject.setZ(z); // keep sync
        if (z > 5) {
            reset();
        }
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        visualObject.draw(gl);
        gl.glPopMatrix();
    }

    private void reset() {
        x = (random.nextFloat() - 0.5f) * 8;
        z = -50.0f;
        passed = false;
        spawnRandomObject();
        visualObject.setX(x);
        visualObject.setZ(z);
    }
    
    @Override
    public float getX() {
        return visualObject.getX();
    }

    @Override
    public float getZ() {
        return visualObject.getZ();
    }

}
