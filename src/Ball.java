import java.awt.Color;
import java.awt.Graphics2D;

public class Ball {
    public enum Type {
        NORMAL,
        COUNTDOWN,
        MULTI_COLOR,
        BOMB
    }

    private double x, y;
    private double vx, vy;
    private int radius;
    private Color color;
    private Type type = Type.NORMAL;
    private int countdown = 0; // for COUNTDOWN type

    // Visual offsets for juicy "jiggle" effects
    private double visualOffsetX = 0;
    private double visualOffsetY = 0;
    
    // Squash and stretch factors
    private double squash = 0;      // 0 = circle, positive = vertical stretch, negative = vertical squash
    private double squashVel = 0;   // Velocity of squash change
    private double rotation = 0;    // Rotation for squash orientation

    public Ball(double x, double y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.vx = 0;
        this.vy = 0;
    }

    public void setType(Type type) {
        this.type = type;
        if (type == Type.COUNTDOWN) {
            this.countdown = 10;
        } else if (type == Type.BOMB) {
            this.color = Color.BLACK;
        }
    }

    public Type getType() { return type; }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public int getCountdown() { return countdown; }

    public void decrementCountdown() {
        if (type == Type.COUNTDOWN && countdown > 0) {
            countdown--;
        }
    }

    public void setVelocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void addVisualOffset(double ox, double oy) {
        this.visualOffsetX += ox;
        this.visualOffsetY += oy;
    }
    
    public void applySquash(double amount, double angle) {
        this.squashVel += amount;
        this.rotation = angle;
    }

    public void updateVisuals() {
        // Elastic decay for offsets
        visualOffsetX *= 0.85;
        visualOffsetY *= 0.85;
        if (Math.abs(visualOffsetX) < 0.1) visualOffsetX = 0;
        if (Math.abs(visualOffsetY) < 0.1) visualOffsetY = 0;
        
        // Spring-mass dampening for squash (softness)
        double spring = 0.15;
        double damping = 0.82;
        double diff = 0.0 - squash;
        squashVel += diff * spring;
        squashVel *= damping;
        squash += squashVel;
        
        if (Math.abs(squash) < 0.001 && Math.abs(squashVel) < 0.001) {
            squash = 0;
            squashVel = 0;
        }
    }

    public void update(int screenWidth) {
        x += vx;
        y += vy;

        // Bounce off left/right walls
        if (x - radius < 0) {
            x = radius;
            vx = -vx;
            applySquash(0.2, Math.PI / 2); // Side impact
        } else if (x + radius > screenWidth) {
            x = screenWidth - radius;
            vx = -vx;
            applySquash(0.2, Math.PI / 2); // Side impact
        }
    }

    public void draw(Graphics2D g2) {
        double cx = x + visualOffsetX;
        double cy = y + visualOffsetY;

        java.awt.geom.AffineTransform old = g2.getTransform();
        g2.translate(cx, cy);
        g2.rotate(rotation);
        
        // Apply non-uniform scaling for squash/stretch
        double sx = 1.0 - squash; // Width
        double sy = 1.0 + squash; // Height
        g2.scale(sx, sy);

        if (type == Type.MULTI_COLOR) {
            drawRainbow(g2);
        } else if (type == Type.BOMB) {
            drawBomb(g2);
        } else {
            // Base fill
            g2.setColor(color);
            g2.fillOval(-radius, -radius, radius * 2, radius * 2);

            // Glossy highlight
            int hSize = Math.max(1, (int) Math.round(radius * 0.55));
            g2.setColor(new Color(255, 255, 255, 90));
            g2.fillOval(-radius / 2, -radius / 2 - hSize / 2, hSize, hSize);

            if (type == Type.COUNTDOWN) {
                // Pulsing glow ring — color changes based on countdown value
                Color ringColor = countdown <= 3 
                    ? new Color(255, 0, 0)         // RED when urgent
                    : new Color(255, 180, 0);      // ORANGE normally
                
                // Animated pulse using system time
                double pulse = 0.5 + 0.5 * Math.sin(System.currentTimeMillis() * 0.008);
                int glowSize = (int)(4 + pulse * 4);
                
                for (int i = 3; i >= 1; i--) {
                    int alpha = countdown <= 3 ? (int)(80 * pulse * i) : 40 * i;
                    g2.setColor(new Color(ringColor.getRed(), ringColor.getGreen(), 
                                          ringColor.getBlue(), alpha));
                    g2.fillOval(-radius - i*glowSize/3, -radius - i*glowSize/3,
                                (radius + i*glowSize/3)*2, (radius + i*glowSize/3)*2);
                }
                
                // Colored ring border
                g2.setStroke(new java.awt.BasicStroke(3f));
                g2.setColor(ringColor);
                g2.drawOval(-radius, -radius, radius*2, radius*2);
                g2.setStroke(new java.awt.BasicStroke(1f));
                
                // Large countdown number
                String text = String.valueOf(countdown);
                int fontSize = countdown <= 9 ? 24 : 20;
                g2.setFont(new java.awt.Font("Comic Sans MS", java.awt.Font.BOLD, fontSize));
                java.awt.FontMetrics fm = g2.getFontMetrics();
                int tx = -fm.stringWidth(text) / 2;
                int ty = fm.getAscent() / 2 - 1;
                // Black shadow
                g2.setColor(Color.BLACK);
                for (int ox = -2; ox <= 2; ox++)
                    for (int oy = -2; oy <= 2; oy++)
                        if (ox != 0 || oy != 0) g2.drawString(text, tx+ox, ty+oy);
                // White text
                g2.setColor(Color.WHITE);
                g2.drawString(text, tx, ty);
            }
        }
        
        g2.setTransform(old);
    }

    private void drawRainbow(Graphics2D g2) {
        // Rotating rainbow — angle changes over time for animation
        double angle = (System.currentTimeMillis() % 3000) / 3000.0 * Math.PI * 2;
        
        // Outer glow
        for (int i = 3; i >= 1; i--) {
            g2.setColor(new Color(255, 255, 255, 20 * i));
            g2.fillOval(-radius - i*2, -radius - i*2, 
                        (radius + i*2)*2, (radius + i*2)*2);
        }
        
        // Rainbow gradient (rotates)
        float[] fractions = {0f, 0.17f, 0.33f, 0.5f, 0.67f, 0.83f, 1.0f};
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, 
                          Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA};
        double cx = Math.cos(angle) * radius;
        double cy = Math.sin(angle) * radius;
        java.awt.LinearGradientPaint lgp = new java.awt.LinearGradientPaint(
            (float)-cx, (float)-cy, (float)cx, (float)cy, fractions, colors);
        g2.setPaint(lgp);
        g2.fillOval(-radius, -radius, radius*2, radius*2);
        
        // Bright white border
        g2.setStroke(new java.awt.BasicStroke(2.5f));
        g2.setColor(new Color(255, 255, 255, 200));
        g2.drawOval(-radius, -radius, radius*2, radius*2);
        g2.setStroke(new java.awt.BasicStroke(1f));
        
        // Star/sparkle symbol in center
        g2.setColor(new Color(255, 255, 255, 220));
        g2.setFont(new java.awt.Font("Comic Sans MS", java.awt.Font.BOLD, 16));
        java.awt.FontMetrics fm = g2.getFontMetrics();
        String star = "★";
        g2.drawString(star, -fm.stringWidth(star)/2, fm.getAscent()/2 - 2);
    }

    private void drawBomb(Graphics2D g2) {
        // Red glow halo
        for (int i = 3; i >= 1; i--) {
            g2.setColor(new Color(255, 0, 0, 30 * i));
            g2.fillOval(-radius - i*3, -radius - i*3,
                        (radius + i*3)*2, (radius + i*3)*2);
        }
        // Black body
        g2.setColor(Color.BLACK);
        g2.fillOval(-radius, -radius, radius * 2, radius * 2);
        // Grey shine
        g2.setColor(new Color(80, 80, 80));
        g2.fillOval(-radius + 2, -radius + 2, radius, radius);
        // Fuse (brown curved line, thicker)
        g2.setColor(new Color(139, 69, 19));
        g2.setStroke(new java.awt.BasicStroke(3));
        g2.drawArc(-radius/2, -radius - 6, radius, radius, 45, 100);
        g2.setStroke(new java.awt.BasicStroke(1));
        // Big bright spark — alternates every 100ms
        long t = System.currentTimeMillis();
        if (t % 200 < 100) {
            g2.setColor(Color.YELLOW);
            g2.fillOval(radius/4 - 2, -radius - 10, 8, 8);
            g2.setColor(Color.ORANGE);
            g2.fillOval(radius/4 - 1, -radius - 9, 5, 5);
        } else {
            g2.setColor(new Color(255, 100, 0));
            g2.fillOval(radius/4 - 1, -radius - 8, 5, 5);
        }
        // White skull eyes
        g2.setColor(Color.WHITE);
        g2.fillOval(-radius/3 - 3, -3, 7, 7);
        g2.fillOval(radius/3 - 3,  -3, 7, 7);
        // Skull mouth (3 small dots)
        g2.setColor(new Color(200, 200, 200));
        g2.fillOval(-5, radius/3, 4, 4);
        g2.fillOval(0,  radius/3 + 1, 4, 4);
        g2.fillOval(5,  radius/3, 4, 4);
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public int getRadius() { return radius; }
    public Color getColor() { return color; }
}
