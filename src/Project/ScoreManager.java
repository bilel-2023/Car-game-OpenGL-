package Project;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Font;

public class ScoreManager {
    private int score;
    private float elapsedTime;
    private TextRenderer textRenderer;
    private long lastTime;
    private boolean victory = false; // Flag for win condition

    public ScoreManager() {
        score = 0;
        elapsedTime = 0.0f;
        lastTime = System.currentTimeMillis();
        textRenderer = null;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        float deltaSeconds = (currentTime - lastTime) / 1000.0f;
        lastTime = currentTime;
        elapsedTime += deltaSeconds;
    }

    public void incrementScore() {
        score += 1;
    }

    public void incrementScore(int amount) {
        score += amount;
    }

    public void render(GL2 gl, int width, int height) {
        if (textRenderer == null) {
            textRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 18));
        }

        textRenderer.beginRendering(width, height);
        textRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        textRenderer.draw("Score: " + score, 10, height - 30);
        textRenderer.draw("Time: " + String.format("%.2f", elapsedTime), 10, height - 60);
        textRenderer.endRendering();
    }

    public void renderGameOver(GL2 gl, int width, int height) {
        if (textRenderer == null) {
            textRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
        }

        textRenderer.beginRendering(width, height);

        if (victory) {
            textRenderer.setColor(0.0f, 1.0f, 0.0f, 1.0f); // Green for victory
            String msg = "VICTORY!";
            int textWidth = msg.length() * 20;
            int xPos = (width - textWidth) / 2;
            int yPos = height / 2;
            textRenderer.draw(msg, xPos, yPos);
        } else {
            textRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f); // Red for game over
            String msg = "GAME OVER";
            int textWidth = msg.length() * 20;
            int xPos = (width - textWidth) / 2;
            int yPos = height / 2;
            textRenderer.draw(msg, xPos, yPos);
        }

        textRenderer.endRendering();
    }

    // === NEW method ===
    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public int getScore() {
        return score;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }
}
