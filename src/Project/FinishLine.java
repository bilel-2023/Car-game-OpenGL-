package Project;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class FinishLine extends GameObject {

    private GLUT glut = new GLUT(); // Create GLUT object for rendering text

    @Override
    public void update() {
        setZ(getZ() + 0.5f); // Simulate world movement
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(getX(), getY(), getZ());

        drawCheckerboard(gl);
        drawFinishBoard(gl);

        gl.glPopMatrix();
    }

    private void drawCheckerboard(GL2 gl) {
        float totalWidth = 15.0f; // Total width of the finish line
        float totalHeight = 0.4f; // Total height (since 2 rows)
        int squaresPerRow = 12;
        int rows = 2;

        float squareWidth = totalWidth / squaresPerRow;
        float squareHeight = totalHeight / rows;

        gl.glBegin(GL2.GL_QUADS);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < squaresPerRow; col++) {
                if ((row + col) % 2 == 0) {
                    gl.glColor3f(0.0f, 0.0f, 0.0f); // Black
                } else {
                    gl.glColor3f(1.0f, 1.0f, 1.0f); // White
                }

                float xStart = -totalWidth / 2 + col * squareWidth;
                float xEnd = xStart + squareWidth;
                float yStart = 0.01f;
                float zStart = 0.0f + row * squareHeight;
                float zEnd = zStart + squareHeight;

                gl.glVertex3f(xStart, yStart, zStart);
                gl.glVertex3f(xEnd, yStart, zStart);
                gl.glVertex3f(xEnd, yStart, zEnd);
                gl.glVertex3f(xStart, yStart, zEnd);
            }
        }

        gl.glEnd();
    }

    private void drawFinishBoard(GL2 gl) {
        // Board and poles positions
        float poleHeight = 3.0f;
        float boardHeight = 0.5f;
        float poleWidth = 0.1f;
        float boardWidth = 16.0f;

        // Draw poles
        gl.glColor3f(0.5f, 0.5f, 0.5f); // Gray color for poles
        gl.glBegin(GL2.GL_QUADS);

        // Left Pole
        float leftPoleX = -7.5f;
        gl.glVertex3f(leftPoleX - poleWidth / 2, 0.0f, 0.0f);
        gl.glVertex3f(leftPoleX + poleWidth / 2, 0.0f, 0.0f);
        gl.glVertex3f(leftPoleX + poleWidth / 2, poleHeight, 0.0f);
        gl.glVertex3f(leftPoleX - poleWidth / 2, poleHeight, 0.0f);

        // Right Pole
        float rightPoleX = 7.5f;
        gl.glVertex3f(rightPoleX - poleWidth / 2, 0.0f, 0.0f);
        gl.glVertex3f(rightPoleX + poleWidth / 2, 0.0f, 0.0f);
        gl.glVertex3f(rightPoleX + poleWidth / 2, poleHeight, 0.0f);
        gl.glVertex3f(rightPoleX - poleWidth / 2, poleHeight, 0.0f);

        gl.glEnd();

        // Draw Checkerboard on the board
        int squaresPerRow = 8;
        int rows = 2;

        float squareWidth = boardWidth / squaresPerRow;
        float squareHeight = boardHeight / rows;

        float startY = poleHeight - boardHeight / 2;

        gl.glBegin(GL2.GL_QUADS);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < squaresPerRow; col++) {
                if ((row + col) % 2 == 0) {
                    gl.glColor3f(0.0f, 0.0f, 0.0f); // Black
                } else {
                    gl.glColor3f(1.0f, 1.0f, 1.0f); // White
                }

                float xStart = -boardWidth / 2 + col * squareWidth;
                float xEnd = xStart + squareWidth;
                float yStart = startY + row * squareHeight;
                float yEnd = yStart + squareHeight;

                gl.glVertex3f(xStart, yStart, 0.0f);
                gl.glVertex3f(xEnd, yStart, 0.0f);
                gl.glVertex3f(xEnd, yEnd, 0.0f);
                gl.glVertex3f(xStart, yEnd, 0.0f);
            }
        }

        gl.glEnd();
    }

}
