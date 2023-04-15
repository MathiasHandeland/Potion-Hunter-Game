package no.uib.inf101.main;

import javax.swing.JFrame;

public class Main {

  public static void main(String[] args) {
    
    
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setTitle("Adventure Game");

    GamePanel gamePanel = new GamePanel();
    frame.add(gamePanel);

    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

  }
}
