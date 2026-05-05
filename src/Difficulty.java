public enum Difficulty {
    EASY(15, 4, 0.55, 0.8, 8, 0.05),
    MEDIUM(25, 5, 0.35, 0.7, 10, 0.1),
    HARD(35, 6, 0.1, 0.6, 12, 0.15);

    private final int totalLevels;
    private final int maxColors;
    private final double clusterChance;
    private final double dangerYFactor;
    private final int initialRows;
    private final double gridSpeed;

    Difficulty(int totalLevels, int maxColors, double clusterChance, double dangerYFactor, int initialRows, double gridSpeed) {
        this.totalLevels = totalLevels;
        this.maxColors = maxColors;
        this.clusterChance = clusterChance;
        this.dangerYFactor = dangerYFactor;
        this.initialRows = initialRows;
        this.gridSpeed = gridSpeed;
    }

    public int getTotalLevels() {
        return totalLevels;
    }

    public int getMaxColors() {
        return maxColors;
    }

    public double getClusterChance() {
        return clusterChance;
    }

    public double getDangerYFactor() {
        return dangerYFactor;
    }

    public int getInitialRows() {
        return initialRows;
    }

    public double getGridSpeed() {
        return gridSpeed;
    }
}
