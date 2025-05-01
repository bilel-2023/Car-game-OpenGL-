package tp5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.GLEventListener;

public class SolarSystemSimulation implements GLEventListener {
    // Liste des planètes dans le système solaire
    private List<Planete> planetes = new ArrayList<>();
    // Utilitaire GLU pour les opérations de haut niveau (comme gluLookAt)
    private GLU glu = new GLU();
    // Angle de vue pour la caméra
    private float angleVue = 0;
    // Temps du dernier rendu pour calculer le delta
    private long lastTime;
    // Niveau de zoom de la caméra
    private float zoom = 10.0f; // Zoom initial
    // État de la simulation (pause ou reprise)
    private boolean isPaused = false;

    // Boutons pour le déplacement gauche et droite
    private JButton leftButton, rightButton;
    // Variable pour stocker la translation sur l'axe X
    private float translationX = 0.0f;

    // Variable pour basculer entre le français et l'anglais
    private boolean isFrench = true;
    // Boutons pour les fonctionnalités de l'interface
    private JButton rotateButton, resetButton, zoomInButton, zoomOutButton, pauseResumeButton, translateButton;

    // Méthode principale pour démarrer la simulation
    public static void main(String[] args) {
        // Obtenir le profil OpenGL pour GL2
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // Créer un canvas OpenGL
        GLCanvas canvas = new GLCanvas(capabilities);
        SolarSystemSimulation simulation = new SolarSystemSimulation();
        canvas.addGLEventListener(simulation);

        // Créer la fenêtre principale
        JFrame frame = new JFrame("Simulation Système Solaire");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout()); // Utilisation de BorderLayout pour placer les boutons

        // Création du panneau des boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Ajouter les boutons au panneau
        simulation.addButtons(buttonPanel);

        // Ajouter le panneau des boutons et le canvas au frame
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(canvas, BorderLayout.CENTER);

        // Configurer la fermeture de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Démarrer l'animateur pour rafraîchir le canvas à 60 FPS
        FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.start();
    }

    // Constructeur de la simulation
    public SolarSystemSimulation() {
        // Créer les planètes (soleil, terre, lune) et les ajouter à la liste
        Planete soleil = new Planete(2.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, null);
        Planete terre = new Planete(0.6f, 5.0f, 30.0f, 0.0f, 0.0f, 1.0f, soleil);
        Planete lune = new Planete(0.2f, 1.0f, 90.0f, 0.5f, 0.5f, 0.5f, terre);
        planetes.add(soleil);
        planetes.add(terre);
        planetes.add(lune);
    }

    // Méthode pour ajouter les boutons à l'interface
    private void addButtons(JPanel buttonPanel) {
        // Bouton pour tourner la vue
        rotateButton = new JButton("Tourner");
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angleVue += 10; // Tourne la vue de 10 degrés à chaque clic
            }
        });

        // Bouton pour réinitialiser la vue
        resetButton = new JButton("Réinitialiser");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angleVue = 0; // Réinitialise l'angle de vue
            }
        });

        // Bouton pour zoomer
        zoomInButton = new JButton("Zoom In");
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoom += 1.0f; // Augmente le zoom
            }
        });

        zoomOutButton = new JButton("Zoom Out");
        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoom -= 1.0f; // Diminue le zoom
            }
        });

        // Bouton pour mettre en pause ou reprendre la simulation
        pauseResumeButton = new JButton("Pause");
        pauseResumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPaused) {
                    isPaused = false;
                    pauseResumeButton.setText(isFrench ? "Pause" : "Pause");
                } else {
                    isPaused = true;
                    pauseResumeButton.setText(isFrench ? "Reprendre" : "Resume");
                }
            }
        });

        // Bouton pour changer la langue
        translateButton = new JButton(isFrench ? "Passer à l'anglais" : "Switch to French");
        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isFrench = !isFrench;
                updateButtonLabels(); // Mettre à jour les labels des boutons
            }
        });

        // Bouton pour déplacer à gauche
        leftButton = new JButton("←");
        leftButton.addActionListener(e -> {
            translationX -= 1.0f; // Déplacer la vue vers la gauche
        });

        // Bouton pour déplacer à droite
        rightButton = new JButton("→");
        rightButton.addActionListener(e -> {
            translationX += 1.0f; // Déplacer la vue vers la droite
        });

        // Ajouter les boutons au panneau
        buttonPanel.add(rotateButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(zoomInButton);
        buttonPanel.add(zoomOutButton);
        buttonPanel.add(pauseResumeButton);
        buttonPanel.add(translateButton);
        buttonPanel.add(leftButton);
        buttonPanel.add(rightButton);
    }

    // Méthode pour mettre à jour les labels des boutons en fonction de la langue
    private void updateButtonLabels() {
        rotateButton.setText(isFrench ? "Tourner" : "Rotate");
        resetButton.setText(isFrench ? "Réinitialiser" : "Reset");
        zoomInButton.setText(isFrench ? "Zoom In" : "Zoom In");
        zoomOutButton.setText(isFrench ? "Zoom Out" : "Zoom Out");
        pauseResumeButton.setText(isFrench ? "Pause" : "Pause");
        translateButton.setText(isFrench ? "Passer à l'anglais" : "Switch to French");
    }

    // Méthode d'initialisation OpenGL
    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0f, 0f, 0f, 1f); // Couleur de fond initiale (noir)
        gl.glEnable(GL2.GL_DEPTH_TEST); // Activer le test de profondeur
        gl.glEnable(GL2.GL_LIGHTING); // Activer l'éclairage
        gl.glEnable(GL2.GL_LIGHT0); // Activer la lumière 0
        gl.glEnable(GL2.GL_COLOR_MATERIAL); // Activer les matériaux de couleur
        lastTime = System.currentTimeMillis(); // Initialiser le temps
    }

    // Méthode de rendu OpenGL
    @Override
    public void display(GLAutoDrawable drawable) {
        if (isPaused) return; // Si la simulation est en pause, ne rien faire

        long currentTime = System.currentTimeMillis();
        float delta = (currentTime - lastTime) / 1000.0f; // Calculer le temps écoulé
        lastTime = currentTime;

        // Mettre à jour les positions des planètes
        for (Planete p : planetes) p.update(delta);

        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // Effacer les buffers
        gl.glLoadIdentity(); // Réinitialiser la matrice de vue

        // Positionner la caméra avec zoom, angle de vue et translation
        glu.gluLookAt(zoom * Math.cos(Math.toRadians(angleVue)), 
        		5, zoom * Math.sin(Math.toRadians(angleVue)), translationX, 0, 0, 0, 1, 0);

        // Dessiner les planètes
        tracer(drawable);
        gl.glFlush(); // Forcer le rendu
    }

    // Méthode pour dessiner les planètes
    private void tracer(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        for (Planete p : planetes) {
            gl.glPushMatrix(); // Sauvegarder la matrice actuelle
            if (p.parent != null) {
                // Appliquer la rotation et la translation pour les planètes en orbite
                gl.glRotatef(p.parent.angle, 0.0f, 1.0f, 0.0f);
                gl.glTranslatef(p.parent.orbite, 0.0f, 0.0f);
            }
            // Appliquer la rotation et la translation de la planète
            gl.glRotatef(p.angle, 0.0f, 1.0f, 0.0f);
            gl.glTranslatef(p.orbite, 0.0f, 0.0f);
            gl.glColor3f(p.r, p.g, p.b); // Définir la couleur de la planète
            drawSphere(gl, p.rayon); // Dessiner la sphère
            gl.glPopMatrix(); // Restaurer la matrice
        }
    }

    // Méthode pour dessiner une sphère
    private void drawSphere(GL2 gl, float radius) {
        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
        glu.gluSphere(quadric, radius, 30, 30); // Dessiner une sphère avec 30 segments
        glu.gluDeleteQuadric(quadric);
    }

    // Méthode pour redimensionner la fenêtre
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        if (height == 0) height = 1; // Éviter la division par zéro
        float aspect = (float) width / height; // Calculer l'aspect ratio
        gl.glViewport(0, 0, width, height); // Définir la vue
        gl.glMatrixMode(GL2.GL_PROJECTION); // Passer en mode projection
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, aspect, 1.0, 1000.0); // Définir la perspective
        gl.glMatrixMode(GL2.GL_MODELVIEW); // Revenir en mode modèle
        gl.glLoadIdentity();
    }

    // Méthode pour libérer les ressources (non utilisée ici)
    @Override
    public void dispose(GLAutoDrawable drawable) {}

    // Méthode pour changer la couleur de fond
    public void setBackgroundColor(float r, float g, float b) {
        GL2 gl = (GL2) GLContext.getCurrentGL();
        gl.glClearColor(r, g, b, 1.0f); // Changer la couleur de fond
    }

    // Méthode pour obtenir les informations sur une planète
    public String getPlanetInfo() {
        // Exemple d'information retournée (peut être modifiée pour inclure plus de détails)
        return "Nom de la planète : Terre\nRayon : 0.6\nPosition : Orbite à 5.0 unités";
    }
}