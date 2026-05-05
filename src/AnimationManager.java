import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnimationManager {
    // All active animations
    private List<FallingBall> fallingBalls = new ArrayList<>();
    private List<BounceRipple> bounceRipples = new ArrayList<>();
    private boolean slidingOut = false;
    private double slideOffsetY = 0;      // current Y offset applied to all settled balls
    private static final double SLIDE_SPEED = 8.0;  // px per frame

    private List<SlideOutBall> slideOutBalls = new ArrayList<>();
    private boolean slideOutDone = false;

    private int screenWidth;
    private int screenHeight;

    public AnimationManager(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void update() {
        // Update falling balls
        Iterator<FallingBall> fbIter = fallingBalls.iterator();
        while (fbIter.hasNext()) {
            FallingBall fb = fbIter.next();
            fb.update();
            if (fb.y > screenHeight + fb.radius * 2) {
                fbIter.remove();
            }
        }

        // Update bounce ripples
        Iterator<BounceRipple> brIter = bounceRipples.iterator();
        while (brIter.hasNext()) {
            BounceRipple br = brIter.next();
            br.update();
            if (br.frame >= br.maxFrames) {
                brIter.remove();
            }
        }

        // Update level transition slide-out
        if (slidingOut) {
            slideOffsetY += SLIDE_SPEED;
            if (slideOffsetY > screenHeight + 50) {
                slidingOut = false;
                slideOutDone = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        // Draw slide-out balls
        if (slidingOut) {
            for (SlideOutBall sb : slideOutBalls) {
                sb.draw(g2, slideOffsetY);
            }
        }

        // Draw falling balls
        for (FallingBall fb : fallingBalls) {
            fb.draw(g2);
        }

        // Draw bounce ripples
        for (BounceRipple br : bounceRipples) {
            br.draw(g2);
        }
    }

    public void spawnFallingBalls(List<Ball> removed) {
        for (Ball b : removed) {
            FallingBall fb = new FallingBall();
            fb.x = b.getX();
            fb.y = b.getY();
            fb.radius = b.getRadius();
            fb.color = b.getColor();
            // Spread out sideways, always fall downward
            fb.vx = (Math.random() * 6.0) - 3.0; // between -3.0 and +3.0
            fb.vy = (Math.random() * 3.0) + 2.0; // between +2.0 and +5.0
            fb.alpha = 1.0;
            fallingBalls.add(fb);
        }
    }

    public void spawnBounceRipple(double x, double y, Color color) {
        bounceRipples.add(new BounceRipple(x, y, color));
    }

    public void startSlideOut(List<Ball> allBalls) {
        slideOutBalls.clear();
        for (Ball b : allBalls) {
            slideOutBalls.add(new SlideOutBall(b.getX(), b.getY(), b.getRadius(), b.getColor()));
        }
        slidingOut = true;
        slideOutDone = false;
        slideOffsetY = 0;
    }

    public boolean isSlideOutDone() {
        return !slidingOut && slideOutDone;
    }

    public boolean isSlidingOut() {
        return slidingOut;
    }

    private class FallingBall {
        double x, y, vx, vy, radius;
        Color color;
        double alpha;

        void update() {
            x += vx;
            vy += 0.5; // Slightly stronger gravity for better feel
            y += vy;
        }

        void draw(Graphics2D g2) {
            g2.setColor(color);
            g2.fillOval((int)(x - radius), (int)(y - radius), (int)(radius * 2), (int)(radius * 2));

            // Draw gloss highlight
            g2.setColor(new Color(255, 255, 255, 120));
            int glossRadius = (int)(radius * 0.4);
            int glossX = (int)(x - radius * 0.3) - glossRadius;
            int glossY = (int)(y - radius * 0.3) - glossRadius;
            g2.fillOval(glossX, glossY, glossRadius * 2, glossRadius * 2);
        }
    }

    private class BounceRipple {
        double x, y;
        Color color;
        int frame = 0;
        int maxFrames = 12;

        BounceRipple(double x, double y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        void update() {
            frame++;
        }

        void draw(Graphics2D g2) {
            double progress = (double) frame / maxFrames; // 0.0 to 1.0
            int radius = (int) (30 * progress);
            float alpha = (float) (1.0 - progress);
            Color c = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (alpha * 120));

            g2.setColor(c);
            Stroke oldStroke = g2.getStroke();
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval((int) (x - radius), (int) (y - radius), radius * 2, radius * 2);
            g2.setStroke(oldStroke);
        }
    }

    private class SlideOutBall {
        double x, y, radius;
        Color color;

        SlideOutBall(double x, double y, double radius, Color color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
        }

        void draw(Graphics2D g2, double offsetY) {
            double drawY = y + offsetY;
            g2.setColor(color);
            g2.fillOval((int)(x - radius), (int)(drawY - radius), (int)(radius * 2), (int)(radius * 2));

            // Draw gloss highlight
            g2.setColor(new Color(255, 255, 255, 120));
            int glossRadius = (int)(radius * 0.4);
            int glossX = (int)(x - radius * 0.3) - glossRadius;
            int glossY = (int)(drawY - radius * 0.3) - glossRadius;
            g2.fillOval(glossX, glossY, glossRadius * 2, glossRadius * 2);
        }
    }
}
