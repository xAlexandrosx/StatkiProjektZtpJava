package Match;

import javax.swing.*;

public class MatchFrame extends JDialog {

    public MatchFrame(MatchRenderer renderer) {
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