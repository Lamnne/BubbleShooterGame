import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class BallWorld {

    private final List<Ball> balls = new ArrayList<>();
    private final int screenWidth;
    private final int screenHeight;
    public final double CEIL_Y;
    private double currentCeilingY;

    public static final double NEIGHBOR_FACTOR = 1.15;

    public BallWorld(int screenWidth, int screenHeight, double ceilY) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.CEIL_Y = ceilY;
        this.currentCeilingY = ceilY;
    }

    public void advanceGrid(double dy) {
        currentCeilingY += dy;
        for (Ball b : balls) {
            b.setY(b.getY() + dy);
        }
    }

    public void resetCeiling() {
        this.currentCeilingY = CEIL_Y;
    }

    public double getCeilingY() {
        return currentCeilingY;
    }

    public boolean checkCollision(Ball movingBall) {
        double minDist = movingBall.getRadius() * 2;
        for (Ball b : balls) {
            if (dist(movingBall, b) <= minDist)
                return true;
        }
        return false;
    }

    public Ball snapAndSettle(Ball movingBall, double vx, double vy) {
        double diameter = movingBall.getRadius() * 2.0;
        int    r        = movingBall.getRadius();

        Ball   hit  = null;
        double minD = Double.MAX_VALUE;
        for (Ball b : balls) {
            double d = dist(movingBall, b);
            if (d < minD) { minD = d; hit = b; }
        }

        if (hit != null) {
            double dx  = movingBall.getX() - hit.getX();
            double dy  = movingBall.getY() - hit.getY();
            double len = Math.sqrt(dx * dx + dy * dy);
            if (len < 1e-6) { dx = 0; dy = -1; len = 1; }

            double sink = 0.2; 
            movingBall.setX(hit.getX() + (dx / len) * diameter + vx * sink);
            movingBall.setY(hit.getY() + (dy / len) * diameter + vy * sink);
        }

        for (int pass = 0; pass < 12; pass++) {
            boolean anyOverlap = false;
            for (Ball b : balls) {
                double d = dist(movingBall, b);
                if (d < diameter - 0.01) {
                    anyOverlap = true;
                    double ox  = movingBall.getX() - b.getX();
                    double oy  = movingBall.getY() - b.getY();
                    double len = Math.sqrt(ox * ox + oy * oy);
                    if (len < 1e-6) { ox = 0; oy = -1; len = 1; }
                    
                    double push = diameter - d;
                    movingBall.setX(movingBall.getX() + (ox / len) * push);
                    movingBall.setY(movingBall.getY() + (oy / len) * push);
                    
                    double tx = -oy / len;
                    double ty = ox / len;
                    double dot = vx * tx + vy * ty;
                    double slidePower = 0.15 * (1.0 - (double)pass/12.0);
                    movingBall.setX(movingBall.getX() + tx * dot * slidePower);
                    movingBall.setY(movingBall.getY() + ty * dot * slidePower);
                }
            }
            if (!anyOverlap) break;
        }

        movingBall.setX(Math.max(r, Math.min(screenWidth - r, movingBall.getX())));
        balls.add(movingBall);
        return movingBall;
    }

    public void settleAtCeiling(Ball movingBall) {
        int r = movingBall.getRadius();
        double clampedX = Math.max(r, Math.min(screenWidth - r, movingBall.getX()));
        movingBall.setX(clampedX);
        movingBall.setY(currentCeilingY + r);
        balls.add(movingBall);
    }

    public List<Ball> getNeighbors(Ball target) {
        List<Ball> result = new ArrayList<>();
        double threshold = target.getRadius() * 2 * NEIGHBOR_FACTOR;
        for (Ball b : balls) {
            if (b != target && dist(target, b) <= threshold)
                result.add(b);
        }
        return result;
    }

    public void applyImpact(Ball hitBall, double vx, double vy) {
        if (hitBall == null) return;
        
        java.util.Set<Ball> visited = new java.util.HashSet<>();
        java.util.Queue<Ball> queue = new java.util.LinkedList<>();
        java.util.Map<Ball, Integer> distance = new java.util.HashMap<>();
        
        queue.add(hitBall);
        visited.add(hitBall);
        distance.put(hitBall, 0);
        
        double impactForce = Math.sqrt(vx * vx + vy * vy) * 0.5;
        double impactAngle = Math.atan2(vy, vx) + Math.PI / 2;
        
        while (!queue.isEmpty()) {
            Ball cur = queue.poll();
            int d = distance.get(cur);
            if (d > 5) continue;
            
            double force = impactForce / (d + 1);
            cur.addVisualOffset((vx / 15.0) * force, (vy / 15.0) * force);
            
            if (d < 3) {
                cur.applySquash(-0.25 / (d + 1), impactAngle);
            }
            
            for (Ball nb : getNeighbors(cur)) {
                if (!visited.contains(nb)) {
                    visited.add(nb);
                    distance.put(nb, d + 1);
                    queue.add(nb);
                }
            }
        }
    }
    
    public void updateVisuals() {
        for (Ball b : balls) {
            b.updateVisuals();
        }
    }

    public void removeBalls(List<Ball> toRemove) {
        balls.removeAll(toRemove);
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public boolean isEmpty() {
        return balls.isEmpty();
    }

    public boolean hasReachedBottom() {
        for (Ball b : balls) {
            if (b.getY() + b.getRadius() > screenHeight) return true;
        }
        return false;
    }

    public void clearAll() {
        balls.clear();
        resetCeiling();
    }

    public void draw(Graphics2D g2) {
        for (Ball b : balls)
            b.draw(g2);
    }

    private static double dist(Ball a, Ball b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double sweptCollision(double startX, double startY,
                                 double vx, double vy, int radius) {
        double speed   = Math.sqrt(vx * vx + vy * vy);
        if (speed < 1e-9) return -1;

        double stepSize = radius;
        int    steps    = (int) Math.ceil(speed / stepSize) + 1;
        double minDist  = radius * 2.0;

        for (int i = 1; i <= steps; i++) {
            double t  = (double) i / steps;
            double cx = startX + vx * t;
            double cy = startY + vy * t;
            for (Ball b : balls) {
                double dx = cx - b.getX();
                double dy = cy - b.getY();
                if (Math.sqrt(dx * dx + dy * dy) <= minDist) {
                    return speed * t;
                }
            }
        }
        return -1;
    }
}
