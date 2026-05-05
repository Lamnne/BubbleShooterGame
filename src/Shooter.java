import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Shooter {
    private int x, y;
    private Ball currentBall;
    private Ball nextBall;
    private int maxColors = 4;
    private double angle = 0; // In radians
    private static final int BALL_RADIUS = 15;

    public Shooter(int x, int y, int maxColors) {
        this.x = x;
        this.y = y;
        this.maxColors = maxColors;
        loadNextBall();
    }

    public void setMaxColors(int maxColors) {
        this.maxColors = maxColors;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    private Color getRandomColor() {
        return BallPalette.random(maxColors);
    }

    public void loadNextBall() {
        if (nextBall == null) {
            currentBall = new Ball(x, y, BALL_RADIUS, getRandomColor());
        } else {
            currentBall = nextBall;
            currentBall.setX(x);
            currentBall.setY(y);
        }
        
        // Generate next ball
        double multiChance = 0.05; // 5% — 1 in 20 shots
        if (Math.random() < multiChance) {
            Ball mb = new Ball(x + 50, y, BALL_RADIUS, Color.WHITE);
            mb.setType(Ball.Type.MULTI_COLOR);
            nextBall = mb;
        } else {
            nextBall = new Ball(x + 50, y, BALL_RADIUS, getRandomColor());
        }
    }

    public Ball getCurrentBall() {
        return currentBall;
    }

    public void clearCurrentBall() {
        currentBall = null;
    }

    public void swapBalls() {
        if (currentBall != null && nextBall != null) {
            Ball temp = currentBall;
            currentBall = nextBall;
            nextBall = temp;
            
            currentBall.setX(x);
            currentBall.setY(y);
            
            nextBall.setX(x + 50);
            nextBall.setY(y);
        }
    }

    public void draw(Graphics2D g2) {
        // Draw the shooter base
        g2.setColor(Color.GRAY);
        g2.fillRect(x - 25, y - 10, 50, 20);
        
        // Draw the current ball if it exists
        if (currentBall != null) {
            currentBall.draw(g2);
        }
        
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString("NEXT", x + 35, y - 20);
        
        if (nextBall != null) {
            nextBall.draw(g2);
        }
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
}
