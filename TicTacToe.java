import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

import javax.swing.*;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TicTacToe extends JPanel {
    private static final long serialVersionUID = 1L;
    static JButton buttons[] = new JButton[9];

    static String[] board = { "0", "1", "2", "3", "4", "5", "6", "7", "8" };

    public static void main(String[] args) {
        JFrame window = new JFrame("Tic-Tac-Toe");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new TicTacToe());
        window.setBounds(400, 200, 400, 400);
        window.setVisible(true);

        Play();

    }

    public static void Play() {
        resetbaord();
        String First = JOptionPane.showInputDialog("Would you like to go First? (Y/N)");
        if (First == null) {
            System.exit(0);
        }
        if (!First.equals("Y")) {
            int move = minimax(board, "X", 0);
            buttons[move].setText("X");
            updateboard(board);
        }
    }

    public static void buffer(int time) {
        System.out.print("Scanning");
        try {
            for (int i = 0; i < time; i++) {
                Thread.sleep(1000);
                System.out.print(".");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static void resetbaord() {
        for (int i = 0; i <= 8; i++) {
            board[i] = Integer.toString(i);
        }
    }

    public static void updateboard(String[] baord) {
        for (int i = 0; i <= 8; i++) {
            if (!buttons[i].getText().equals("")) {
                baord[i] = buttons[i].getText();
            }
        }
    }

    public static int minimax(String[] board, String Player, int depth) {
        int bestX = Integer.MIN_VALUE;
        int bestO = Integer.MAX_VALUE;
        int bestmove = -3;

        if (haswon(board, "X")) {
            return 10 - depth;
        } else if (haswon(board, "O")) {
            return -10 + depth;
        } else if (isdraw(board)) {
            return 0;
        }

        for (int i = 0; i < board.length; i++) {
            if (!board[i].equals("X") & !board[i].equals("O")) {
                board[i] = Player;
                String next = change(Player);
                int score = minimax(board, next, depth + 1);
                board[i] = Integer.toString(i);

                if (Player.equals("X")) {
                    if (max(bestX, score)) {
                        bestX = score;
                        bestmove = i;
                    }
                } else {
                    if (min(bestO, score)) {
                        bestO = score;
                        bestmove = i;
                    }
                }
            }
        }

        if (depth > 0) {
            if (Player.equals("X")) {
                return bestX;
            } else {
                return bestO;
            }
        }

        return bestmove;
    }

    public static boolean max(int bestval, int val) {
        if (val >= bestval) {
            return true;
        }

        else
            return false;
    }

    public static boolean min(int bestval, int val) {
        if (val <= bestval) {
            return true;
        }

        else
            return false;
    }

    public static String change(String p) {
        if (p.equals("X")) {
            return "O";
        } else {
            return "X";
        }
    }

    public static boolean isdraw(String[] baord) {
        int counter = 0;
        boolean isdraw = false;
        for (int i = 0; i < baord.length; i++) {
            if (baord[i].equals("X") | baord[i].equals("O")) {
                counter++;
            }
        }
        if (counter == 9) {
            isdraw = true;
        }
        return isdraw;
    }

    public static boolean haswon(String[] board, String p) {
        boolean won = false;
        if (board[0].equals(p) & board[1].equals(p) & board[2].equals(p)) {
            won = true;
        }
        if (board[3].equals(p) & board[4].equals(p) & board[5].equals(p)) {
            won = true;
        }
        if (board[6].equals(p) & board[7].equals(p) & board[8].equals(p)) {
            won = true;
        }
        if (board[0].equals(p) & board[3].equals(p) & board[6].equals(p)) {
            won = true;
        }
        if (board[1].equals(p) & board[4].equals(p) & board[7].equals(p)) {
            won = true;
        }
        if (board[2].equals(p) & board[5].equals(p) & board[8].equals(p)) {
            won = true;
        }
        if (board[6].equals(p) & board[4].equals(p) & board[2].equals(p)) {
            won = true;
        }
        if (board[0].equals(p) & board[4].equals(p) & board[8].equals(p)) {
            won = true;
        }

        return won;
    }

    public TicTacToe() {
        setLayout(new GridLayout(3, 3));
        initializebuttons();
    }

    public void initializebuttons() {
        for (int i = 0; i <= 8; i++) {
            buttons[i] = new JButton();
            buttons[i].setText("");
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            buttons[i].addActionListener(new buttonListener());

            add(buttons[i]); // adds this button to JPanel (note: no need for JPanel.add(...)
                             // because this whole class is a JPanel already
        }
    }

    public void resetButtons() {
        for (int i = 0; i <= 8; i++) {
            buttons[i].setText("");
        }
    }

    private class buttonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JButton buttonClicked = (JButton) e.getSource();
            buttonClicked.setText("O");
            updateboard(board);
            if (isdraw(board)) {
                JOptionPane.showMessageDialog(null, "The game is a Tie");
                resetButtons();
                Play();
            } else {
            int move = minimax(board, "X", 0); 
            buttons[move].setText("X");
            updateboard(board);

            if (haswon(board, "X"))
            {
                JOptionPane.showMessageDialog(null, "The Artificial Intelligence has Defeated you ");
                resetButtons();
                Play();
            }
            else if (isdraw(board))
            {
                JOptionPane.showMessageDialog(null, "The game is a Tie");
                resetButtons();
                Play();
            }
        }
        }
        
        
    }
    
}