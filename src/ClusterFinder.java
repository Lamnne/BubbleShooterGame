import java.util.*;

public class ClusterFinder {
    private final BallWorld world;

    public ClusterFinder(BallWorld world) {
        this.world = world;
    }

    /** BFS: all settled balls connected to origin that share its colour. */
    public List<Ball> findCluster(Ball origin) {
        List<Ball> cluster = new ArrayList<>();
        if (origin == null)
            return cluster;

        Set<Ball> visited = new HashSet<>();
        Queue<Ball> queue = new LinkedList<>();
        queue.add(origin);
        visited.add(origin);

        while (!queue.isEmpty()) {
            Ball cur = queue.poll();
            cluster.add(cur);
            for (Ball nb : world.getNeighbors(cur)) {
                if (!visited.contains(nb) && nb.getColor().equals(origin.getColor())) {
                    visited.add(nb);
                    queue.add(nb);
                }
            }
        }
        return cluster;
    }
}
