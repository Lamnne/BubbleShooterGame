import java.util.*;

public class FloatingFinder {
    private final BallWorld world;

    public FloatingFinder(BallWorld world) {
        this.world = world;
    }

    /**
     * BFS from ceiling-touching balls.
     * Any ball not reachable from the ceiling is floating → should be removed.
     */
    public List<Ball> findFloating() {
        List<Ball> all = world.getBalls();

        // Seed: every ball whose top edge touches the ceiling
        Set<Ball> grounded = new HashSet<>();
        Queue<Ball> queue = new LinkedList<>();
        for (Ball b : all) {
            // Use dynamic ceiling from world
            if (b.getY() - b.getRadius() <= world.getCeilingY() + 1.5) {
                grounded.add(b);
                queue.add(b);
            }
        }

        // Spread grounded status through neighbours (any colour)
        while (!queue.isEmpty()) {
            Ball cur = queue.poll();
            for (Ball nb : world.getNeighbors(cur)) {
                if (!grounded.contains(nb)) {
                    grounded.add(nb);
                    queue.add(nb);
                }
            }
        }

        // Anything not grounded is floating
        List<Ball> floating = new ArrayList<>();
        for (Ball b : all) {
            if (!grounded.contains(b))
                floating.add(b);
        }
        return floating;
    }
}
