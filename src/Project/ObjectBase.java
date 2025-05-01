package Project;

import com.jogamp.opengl.GL2;

public abstract class ObjectBase extends GameObject {
    // Base class to define different objects that can be obstacles
    public abstract void draw(GL2 gl);
}
