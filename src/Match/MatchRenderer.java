package Match;

import battleship.Battleship;
import board.Board;
import players.HumanPlayer;
import players.IPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MatchRenderer extends JPanel implements IMatch { // Klasa odpowiedzialna za obsługę okienka z grą
    private final MatchFrame frame;
    public static final int viewportW = 512,
            viewportH = 768;
    public static final int boardPixels = 400;
    private final Color bgColor;
    private final Match match;
    private IPlayer perspective;
    private Graphics2D g2d;
    private int
            cellSize,
            boardOffsetX,
            boardOffsetY;

    private volatile boolean drawBoard;
    private volatile String title;
    private boolean swImmediately;

    public MatchRenderer(int variant) { // Konstruktor
        match = new Match(variant);
        frame = new MatchFrame(MatchRenderer.this);
        swImmediately = variant == Match.PLAYER_VS_COMPUTER;
        this.setPreferredSize(new Dimension(viewportW, viewportH));
        this.setBackground(bgColor = Color.DARK_GRAY);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!(perspective instanceof HumanPlayer human)) {
                    switchPerspective();
                    return;
                }
                Board board = perspective.getEnemyBoard();
                int x = (e.getX() - boardOffsetX) / cellSize;
                int y = (e.getY() - boardOffsetY) / cellSize;
                if (x >= 0 && x < board.size && y >= 0 && y < board.size) {
                    human.hitTarget(x, y);
                    switchPerspective();
                }
            }
        });

    }

    public MatchRenderer setSwitchImmediately(){ // Metoda zmieniająca tryb gry
        swImmediately = true;
        return this;
    }

    private void setPerspective(IPlayer perp){ // Metoda ustawiająca perspektywę
        if(perp.getOwnBoard().allSunk()) {
            frame.dispose();
            match.finishMatch(perspective, perp);
            return;
        }

        this.perspective = perp;
        cellSize = boardPixels / perspective.getOwnBoard().size;
        boardOffsetX = (viewportW - boardPixels) / 2;
        boardOffsetY = (viewportH - boardPixels) / 2;

        if(!(perspective instanceof HumanPlayer)) {
            perspective.takeTurn();
        }
    }

    private void switchPerspective(){ // Metoda zmieniająca perspektywę gry
        IPlayer opposite = match.p1;
        if (perspective != null && perspective.equals(match.p1)){
            opposite = match.p2;
        }
        drawBoard = false;
        IPlayer finalOpposite = opposite;
        if(swImmediately) {
            setPerspective(finalOpposite);
            updateTitle();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 3; i > 0; i--) {
                        title = "Switching boards in " + i + "..";
                        updateRenderer();
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    setPerspective(finalOpposite);
                    updateTitle();
                }
            }, "Countdown Thread").start();
        }
    }

    private void updateTitle() { // Metoda aktualizująca stan kafelka planszy
        drawBoard = true;
        title = perspective.getName() + "'s Shots:";
        updateRenderer();
    }

    protected void paintComponent(Graphics g) { // Metoda rysująca okienko
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setBackground(bgColor);
        g2d.clearRect(0, 0, viewportW, viewportH);
        if(drawBoard)
            render();
        g2d.setColor(Color.WHITE);
        g2d.drawString(title, boardOffsetX, boardOffsetY - 10);
    }

    private void drawShip(Battleship obj) { // Metoda rysująca statki
        int x = boardOffsetX + (obj.posX * cellSize);
        int y = boardOffsetY + (obj.posY * cellSize);

        g2d.setColor(Color.GRAY);
        if (obj.vertical) {
            g2d.fillRect(x + 5, y + 5, cellSize - 10, (cellSize * obj.length) - 10);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x + 5, y + 5, cellSize - 10, (cellSize * obj.length) - 10);
        } else {
            g2d.fillRect(x + 5, y + 5, (cellSize * obj.length) - 10, cellSize - 10);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x + 5, y + 5, (cellSize * obj.length) - 10, cellSize - 10);
        }
    }

    public void render() { // Metoda do renderowania
        if (perspective == null) return;

        Board bb = perspective.getOwnBoard();

        for (int x = 0; x < bb.size; x++) {
            int sX = boardOffsetX + (x * cellSize);
            for (int y = 0; y < bb.size; y++) {
                int sY = boardOffsetY + (y * cellSize);
                int hitState = bb.getTile(x, y);
                if (hitState == Board.HIT) {
                    g2d.setColor(new Color(255, 100, 100));
                } else {

                }
                g2d.setColor(new Color(30, 144, 255));
                g2d.fillRect(sX, sY, cellSize, cellSize);

                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRect(sX, sY, cellSize, cellSize);
            }
        }
        bb = perspective.getEnemyBoard();
        for (int x = 0; x < bb.size; x++) {
            int sX = boardOffsetX + (x * cellSize);
            for (int y = 0; y < bb.size; y++) {
                int sY = boardOffsetY + (y * cellSize);
                int hitState = bb.getTile(x, y);
                if (hitState == Board.MISS) { // MISS
                    g2d.setColor(Color.WHITE);
                    int size = cellSize / 4;
                    g2d.fillOval(sX + (cellSize / 2) - (size / 2), sY + (cellSize / 2) - (size / 2), size, size);
                } else if (hitState == Board.HIT) { // HIT
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawLine(sX + 5, sY + 5, sX + cellSize - 5, sY + cellSize - 5);
                    g2d.drawLine(sX + cellSize - 5, sY + 5, sX + 5, sY + cellSize - 5);
                }
            }
        }
    }

    public void updateRenderer() { // Metoda aktualizacja renderera
        if (isVisible() && perspective != null) {
            repaint();
        }
    }

    @Override
    public void playMatch() { // Metoda uruchamiająca rozgrywkę
        setPerspective(match.p1);
        updateTitle();
        frame.setVisible(true);
    }
}
