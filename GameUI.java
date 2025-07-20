package Dice_Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameUI extends JFrame {
    private MainEngine engine;
    private JTextArea gameLog;
    private JButton rollButton;
    private JPanel playerPanel;
    private JLabel resultLabel;

    private HashMap<Players, JLabel> playerLabels = new HashMap<>();

    public GameUI() {
        setTitle("Roll Your Dice!");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ArrayList<Players> players = getPlayerInput();
        engine = new MainEngine(players);

        
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        JScrollPane scroll = new JScrollPane(gameLog);

        resultLabel = new JLabel("Click 'Roll Dice' to start the game.", SwingConstants.CENTER);

        rollButton = new JButton("Roll");
        rollButton.addActionListener(e -> playRound());

        playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(players.size(), 1));
        for (Players p : players) {
            JLabel label = new JLabel(p.showNames() + " - Wins: 0");
            playerLabels.put(p, label);
            playerPanel.add(label);
        }

        
        gameLog.setBackground(new Color(30, 30, 30));
        gameLog.setForeground(new Color(144, 238, 144));
        gameLog.setFont(new Font("Consolas", Font.PLAIN, 14));
        gameLog.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultLabel.setForeground(Color.ORANGE);
        resultLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        rollButton.setFont(new Font("Arial", Font.BOLD, 16));
        rollButton.setBackground(new Color(72, 61, 139));
        rollButton.setForeground(Color.WHITE);
        rollButton.setFocusPainted(false);
        rollButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        rollButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        playerPanel.setBackground(new Color(45, 45, 45));
        playerPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Scoreboard",
                0, 0, new Font("Arial", Font.BOLD, 14),
                Color.LIGHT_GRAY
        ));

        for (JLabel label : playerLabels.values()) {
            label.setForeground(new Color(173, 216, 230));
            label.setFont(new Font("Monospaced", Font.BOLD, 14));
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }

        
        add(resultLabel, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, playerPanel);
        split.setDividerLocation(500);
        split.setResizeWeight(1.0);
        split.setDividerSize(9);
        add(split, BorderLayout.CENTER);

        add(rollButton, BorderLayout.SOUTH);
    }

    private ArrayList<Players> getPlayerInput() {
        ArrayList<Players> list = new ArrayList<>();
        int n = 0;
        while (n <= 0) {
            String input = JOptionPane.showInputDialog(this, "Enter number of players:");
            try {
                n = Integer.parseInt(input);
                if (n <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number > 0.");
            }
        }

        for (int i = 0; i < n; i++) {
            String name;
            do {
                name = JOptionPane.showInputDialog(this, "Enter name for Player " + (i + 1) + ":");
            } while (name == null || name.trim().isEmpty());
            list.add(new Players(name.trim()));
        }
        return list;
    }

    private void playRound() {
        gameLog.append("Rolling dice...\n");
        ArrayList<Players> players = engine.arr;
        Dice dice = new Dice();
        ArrayList<Players> winners = new ArrayList<>();
        int highest = 0;

        for (Players p : players) {
            int roll = dice.roll();
            gameLog.append(p.showNames() + " rolled: " + roll + "\n");

            if (roll > highest) {
                highest = roll;
                winners.clear();
                winners.add(p);
            } else if (roll == highest) {
                winners.add(p);
            }
        }

        if (winners.size() == 1) {
            Players winner = winners.get(0);
            winner.incrementWin();
            resultLabel.setText("Winner: " + winner.showNames());
        } else {
            StringBuilder tieMsg = new StringBuilder("It's a tie between: ");
            for (Players p : winners) tieMsg.append(p.showNames()).append(" ");
            resultLabel.setText(tieMsg.toString());
        }

        gameLog.append("\n Round Wins:\n");
        for (Players p : players) {
            gameLog.append(p.showNames() + " - Wins: " + p.showWins() + "\n");
            playerLabels.get(p).setText(p.showNames() + " - Wins: " + p.showWins());
        }
        gameLog.append("------------\n");
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            GameUI game = new GameUI();
            game.setVisible(true);
        });
    }
}