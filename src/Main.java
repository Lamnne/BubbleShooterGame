import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Puzzle Bobble - Version 1");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // Sizes the frame so that all its contents are at or above their preferred sizes
        
        window.setLocationRelativeTo(null); // Center the window
        window.setVisible(true);
        
        gamePanel.startGameThread(); // Start the game loop
    }
}
