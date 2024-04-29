import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 800;
    int boardHeight = 750;

    JFrame frame = new JFrame("TIC-TAC-TOE");
    JLabel textLabel = new JLabel();
    JPanel welcomePanel = new JPanel();
    JButton startButton = new JButton("START GAME");

    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel scorePanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;
    JButton newGameButton;
    boolean gameOver = false;
    int turns = 0;
    int scoreX = 0;
    int scoreO = 0;
    JLabel scoreLabel = new JLabel("Scores: X - 0, O - 0");
    JLabel scoreTitle = new JLabel("SCORE BOARD");

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        welcomePanel.setLayout(new BorderLayout());
        welcomePanel.setBackground(new Color(139, 69, 19));

        textLabel.setBackground(new Color(139, 69, 19));
        textLabel.setForeground(new Color(255, 255, 255));
        textLabel.setFont(new Font("Broadway", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("TIC-TAC-TOE");
        textLabel.setOpaque(true);
        welcomePanel.add(textLabel, BorderLayout.NORTH);

        // Modify the startButton initialization to set preferred size
        startButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        startButton.setFocusable(false);
        startButton.setPreferredSize(new Dimension(150, 50)); // Set preferred size
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        welcomePanel.add(startButton, BorderLayout.CENTER);

        frame.add(welcomePanel, BorderLayout.CENTER);
    }

    void startGame() {
        frame.remove(welcomePanel); // Remove welcome panel
        initializeGame(); // Initialize game components
    }

    void initializeGame() {
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(new Color(139, 69, 19));
        frame.add(boardPanel, BorderLayout.CENTER);

        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(new Color(139, 69, 19));

        newGameButton = new JButton("NEW GAME");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 20));
        newGameButton.setFocusable(false);
        newGameButton.setVisible(false);
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        buttonPanel.add(newGameButton, BorderLayout.CENTER);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        scorePanel.setLayout(new BorderLayout());
        scorePanel.setBackground(new Color(139, 69, 19));

        scoreTitle.setFont(new Font("Broadway", Font.BOLD, 24));
        scoreTitle.setForeground(Color.WHITE);
        scoreTitle.setHorizontalAlignment(JLabel.CENTER);
        scorePanel.add(scoreTitle, BorderLayout.NORTH);

        scoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setForeground(Color.WHITE);
        scorePanel.add(scoreLabel, BorderLayout.CENTER);

        frame.add(scorePanel, BorderLayout.EAST);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(new Color(255, 255, 255));
                tile.setFont(new Font("TimesNewRoman", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText().isEmpty()) {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                if (currentPlayer.equals(playerX)) {
                                    tile.setForeground(new Color(255, 0, 0));
                                } else if (currentPlayer.equals(playerO)) {
                                    tile.setForeground(new Color(0, 0, 255));
                                }
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });
            }
        }
    }

    void checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().equals(board[i][1].getText())
                    && board[i][1].getText().equals(board[i][2].getText())
                    && !board[i][0].getText().isEmpty()) {
                setWinner(board[i][0].getText());
                return;
            }
            if (board[0][i].getText().equals(board[1][i].getText())
                    && board[1][i].getText().equals(board[2][i].getText())
                    && !board[0][i].getText().isEmpty()) {
                setWinner(board[0][i].getText());
                return;
            }
        }
        if (board[0][0].getText().equals(board[1][1].getText())
                && board[1][1].getText().equals(board[2][2].getText())
                && !board[0][0].getText().isEmpty()) {
            setWinner(board[0][0].getText());
            return;
        }
        if (board[0][2].getText().equals(board[1][1].getText())
                && board[1][1].getText().equals(board[2][0].getText())
                && !board[0][2].getText().isEmpty()) {
            setWinner(board[0][2].getText());
            return;
        }

        if (turns == 9) {
            setDraw();
        }
    }

    void setWinner(String player) {
        textLabel.setText(player + " wins!");
        if (player.equals(playerX)) {
            scoreX++;
        } else if (player.equals(playerO)) {
            scoreO++;
        }
        updateScoreboard();
        gameOver = true;
        newGameButton.setVisible(true); // Show NEW GAME button

        // Highlight the winning tiles in green
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().equals(player) && board[i][1].getText().equals(player) && board[i][2].getText().equals(player)) {
                board[i][0].setBackground(Color.GREEN);
                board[i][0].setForeground(Color.BLACK);
                board[i][1].setBackground(Color.GREEN);
                board[i][1].setForeground(Color.BLACK);
                board[i][2].setBackground(Color.GREEN);
                board[i][2].setForeground(Color.BLACK);
            }
            if (board[0][i].getText().equals(player) && board[1][i].getText().equals(player) && board[2][i].getText().equals(player)) {
                board[0][i].setBackground(Color.GREEN);
                board[0][i].setForeground(Color.BLACK);
                board[1][i].setBackground(Color.GREEN);
                board[1][i].setForeground(Color.BLACK);
                board[2][i].setBackground(Color.GREEN);
                board[2][i].setForeground(Color.BLACK);
            }
        }
        if (board[0][0].getText().equals(player) && board[1][1].getText().equals(player) && board[2][2].getText().equals(player)) {
            board[0][0].setBackground(Color.GREEN);
            board[0][0].setForeground(Color.BLACK);
            board[1][1].setBackground(Color.GREEN);
            board[1][1].setForeground(Color.BLACK);
            board[2][2].setBackground(Color.GREEN);
            board[2][2].setForeground(Color.BLACK);
        }
        if (board[0][2].getText().equals(player) && board[1][1].getText().equals(player) && board[2][0].getText().equals(player)) {
            board[0][2].setBackground(Color.GREEN);
            board[0][2].setForeground(Color.BLACK);
            board[1][1].setBackground(Color.GREEN);
            board[1][1].setForeground(Color.BLACK);
            board[2][0].setBackground(Color.GREEN);
            board[2][0].setForeground(Color.BLACK);
        }
    }

    void setDraw() {
        textLabel.setText("It's a draw!");
        updateScoreboard();
        gameOver = true;
        newGameButton.setVisible(true);

        // Set all tiles to grey color
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setBackground(Color.GRAY);
            }
        }

        // Set X and O color to black for all tiles
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (!board[r][c].getText().isEmpty()) {
                    board[r][c].setForeground(Color.BLACK);
                }
            }
        }
    }

    void updateScoreboard() {
        scoreLabel.setText("Scores: X - " + scoreX + ", O - " + scoreO);
    }

    void startNewGame() {
        currentPlayer = playerX;
        turns = 0;
        gameOver = false;
        textLabel.setText("TIC-TAC-TOE");
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.WHITE);
                board[r][c].setForeground(Color.BLACK);
            }
        }
        newGameButton.setVisible(false);
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
