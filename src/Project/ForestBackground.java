package Project;

import com.jogamp.opengl.GL2;

public class ForestBackground {
    private float positionZ = 0.0f; // Starting Z position of the forest
    private final float speed = 0.1f; // Speed at which the forest moves
    private final float resetPositionZ = 500.0f; // Reset position for a much longer forest

    public void update() {
        // Update the forest's Z position to create movement
        positionZ += speed;

        // Reset the position to create a looping effect
        if (positionZ > resetPositionZ) { // Adjust this value for a longer forest
            positionZ = 0.0f; // Reset to the starting point
        }
    }

    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, positionZ); // Move the forest based on its Z position
        
        // Left forest area: from x = -10 to x = -5.
        gl.glColor3f(0.0f, 0.5f, 0.0f); // dark green
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3f(-10f, -0.1f, 500f);  // Increased Z value for longer stretch
            gl.glVertex3f(-5f, -0.1f, 500f);
            gl.glVertex3f(-5f, -0.1f, -500f);  // Increased Z value for longer stretch
            gl.glVertex3f(-10f, -0.1f, -500f); // Increased Z value for longer stretch
        gl.glEnd();
        
        // Right forest area: from x = 5 to x = 10.
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3f(5f, -0.1f, 500f);    // Increased Z value for longer stretch
            gl.glVertex3f(10f, -0.1f, 500f);   
            gl.glVertex3f(10f, -0.1f, -500f);  // Increased Z value for longer stretch
            gl.glVertex3f(5f, -0.1f, -500f);   // Increased Z value for longer stretch
        gl.glEnd();
        
        // Draw simple trees along the left and right sides.
        gl.glColor3f(0.0f, 0.8f, 0.0f); // lighter green for tree tops
        for (int z = -500; z < 500; z += 20) { // Increased range to cover a larger area
            drawTree(gl, -8f, 0, z); // left side
            drawTree(gl, 8f, 0, z);  // right side
        }
        
        gl.glPopMatrix();
    }
    
    private void drawTree(GL2 gl, float x, float y, float z) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        
        // Draw the tree trunk: a small rectangle (brown)
        gl.glColor3f(0.5f, 0.25f, 0.1f); // Brown color for the trunk
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3f(-0.2f, 0.0f, 0f);  // Bottom left
            gl.glVertex3f(0.2f, 0.0f, 0f);   // Bottom right
            gl.glVertex3f(0.2f, 1.0f, 0f);   // Top right
            gl.glVertex3f(-0.2f, 1.0f, 0f);  // Top left
        gl.glEnd();
        
        // Draw the tree foliage: a triangle for simplicity (green)
        gl.glColor3f(0.0f, 0.8f, 0.0f); // Light green for the foliage
        gl.glBegin(GL2.GL_TRIANGLES);
            gl.glVertex3f(0.0f, 2.0f, 0f);    // Top of the triangle (the tree's peak)
            gl.glVertex3f(-1.0f, 1.0f, 0f);   // Bottom left corner
            gl.glVertex3f(1.0f, 1.0f, 0f);    // Bottom right corner
        gl.glEnd();
        
        gl.glPopMatrix();
    }
}
