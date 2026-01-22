package Match;

import javax.swing.*;

public class MatchFrame extends JDialog { // Klasa odpowiedzialna za stworzenie okienka z grą

    public MatchFrame(MatchRenderer renderer) { // Konstruktor
        setTitle("Battleships Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);
        setSize(1024, 768);
        setLayout(null);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        renderer.setBounds(0, 0, 1024, 768);
        add(renderer);
    }
}