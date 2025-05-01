package Project;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

public class TextRendererHelper {
    private static final TextRenderer renderer = new TextRenderer(new Font("Arial", Font.BOLD, 18));

    public static void drawText(GL2 gl, String text, int x, int y) {
        renderer.beginRendering(800, 600); // adapter si besoin
        renderer.setColor(1.0f, 1.0f, 0.0f, 1.0f); // jaune
        renderer.draw(text, x, y);
        renderer.endRendering();
    }
}
