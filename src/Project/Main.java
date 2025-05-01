package Project;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        System.setProperty("jogl.disable.openglarbcontext", "true");

        JFrame frame = new JFrame("Jeu 3D - Voiture");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game game = new Game();

        frame.add(game.getCanvas());
        frame.setSize(800, 600);
        frame.setVisible(true);

        game.start();
    }
    
}
