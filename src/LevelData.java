public class LevelData {
    private String name;
    private int[][] layout;
    private int timeLimit; // in seconds, 0 for no limit

    public LevelData(String name, int[][] layout) {
        this(name, layout, 0);
    }

    public LevelData(String name, int[][] layout, int timeLimit) {
        this.name = name;
        this.layout = layout;
        this.timeLimit = timeLimit;
    }

    public String getName() {
        return name;
    }

    public int[][] getLayout() {
        return layout;
    }

    public int getTimeLimit() {
        return timeLimit;
    }
}
