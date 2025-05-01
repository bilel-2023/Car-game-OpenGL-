package Project;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Game implements GLEventListener, KeyListener {

    private GLJPanel canvas;
    private FPSAnimator animator;
    private Car car;
    private Road road;
    private Camera camera;
    private List<GameObject> gameObjects;
    private ScoreManager scoreManager;
    private ForestBackground forestBackground;
    private FinishLine finishLine;
    private boolean gameOver = false;

    private List<FloatingText> floatingTexts = new ArrayList<>();
    private SoundPlayer soundPlayer; // Add a SoundPlayer instance

    public Game() {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        canvas = new GLJPanel(capabilities);
        canvas.addGLEventListener(this);
        canvas.addKeyListener(this);

        car = new Car();
        road = new Road();
        camera = new Camera(car);
        scoreManager = new ScoreManager();
        forestBackground = new ForestBackground();
        gameObjects = new ArrayList<>();

        // Initialize the SoundPlayer and play background music
        soundPlayer = new SoundPlayer();
        soundPlayer.playSound("C:\\Users\\msi\\Downloads\\race.wav", true); // Loop background music

        // Add enemy cars to the game
        gameObjects.add(new EnemyCar());
        gameObjects.add(new EnemyCar());

        // Add money objects
        for (int i = 0; i < 50; i++) {
            Money money = new Money();
            money.setZ(-100.0f - i * 30.0f); // Space between money objects
            gameObjects.add(money);
        }

        // Add finish line
        finishLine = new FinishLine();
        finishLine.setZ(-600); // Position at the end of the road

        animator = new FPSAnimator(canvas, 60);
    }

    public GLJPanel getCanvas() {
        return canvas;
    }

    public void start() {
        animator.start();
        canvas.requestFocusInWindow();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glClearColor(0.5f, 0.8f, 0.92f, 1.0f); // Light blue sky color
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // No cleanup required for now
    }

    private GameObject getRandomReplacement(float x, float y, float z) {
        Random rand = new Random();
        int choice = rand.nextInt(4); // 0 to 3

        GameObject obj;
        switch (choice) {
            case 0 -> obj = new Star();
            case 1 -> obj = new Person();
            case 2 -> obj = new Money();
            default -> obj = new Star();
        }

        obj.setX(x);
        obj.setY(y);
        obj.setZ(z);
        return obj;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        if (!gameOver) {
            road.update();
            car.update();
            forestBackground.update();
            finishLine.update();

            for (int i = 0; i < gameObjects.size(); i++) {
                GameObject obj = gameObjects.get(i);
                obj.update();

                if (obj instanceof EnemyCar && CollisionDetector.checkCollision(car, obj)) {
                    // Play crash sound and stop background music
                	soundPlayer.stopAllSounds();
                    soundPlayer.playCrashSound("C:\\Users\\msi\\Downloads\\lose.wav");

                    GameObject replacement = getRandomReplacement(obj.getX(), obj.getY(), obj.getZ());
                    gameObjects.set(i, replacement);
                    gameOver = true;
                    animator.stop();
                    break;
                }


                if (obj instanceof Money && CollisionDetector.checkCollision(car, obj)) {
                    soundPlayer.MoneySound("C:\\Users\\msi\\Downloads\\bonus.wav");
                    scoreManager.incrementScore(4);
                    gameObjects.remove(i);
                    i--;

                    floatingTexts.add(new FloatingText("+4", 30, 560, 1000));
                    continue;
                }


                if (!obj.isPassed() && obj.getZ() > car.getZ() + 1.0f) {
                    scoreManager.incrementScore(1);
                    obj.setPassed(true);
                }
            }

            if (Math.abs(car.getZ() - finishLine.getZ()) < 1.0f) {
            	soundPlayer.stopAllSounds();
                soundPlayer.WinSound("C:\\Users\\msi\\Downloads\\win.wav");
                gameOver = true;
                animator.stop();
                scoreManager.setVictory(true);
            }

            scoreManager.update();
        }

        // Camera and rendering
        camera.update();
        camera.applyView(gl);

        forestBackground.draw(gl);
        road.draw(gl);
        car.draw(gl);
        finishLine.draw(gl);

        for (GameObject obj : gameObjects) {
            obj.draw(gl);
        }

        int width = drawable.getSurfaceWidth();
        int height = drawable.getSurfaceHeight();
        scoreManager.render(gl, width, height);

        floatingTexts.removeIf(FloatingText::isExpired);
        for (FloatingText text : floatingTexts) {
            text.render(gl, width, height);
        }

        if (gameOver) {
            scoreManager.renderGameOver(gl, width, height);
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        new GLU().gluPerspective(60.0, (double) width / height, 0.1, 1000);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        car.handleKeyPress(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        car.handleKeyRelease(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}
