import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private List<LevelData> levels;
    private int currentIndex;
    private Difficulty difficulty = Difficulty.EASY;
    private int currentUnlocked = 1;

    public LevelManager() {
        levels = new ArrayList<>();
        currentIndex = 0;
        initLevels();
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        reset();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    private void initLevels() {
        // Level 1: Simple Block (Timed 30s for testing)
        levels.add(new LevelData("Level 1", new int[][] {
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 }
        }));

        // Level 2: Alternating Rows
        levels.add(new LevelData("Level 2", new int[][] {
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 }
        }));

        // Level 3: Columns
        levels.add(new LevelData("Level 3", new int[][] {
                { 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
                { 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1 },
                { 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
                { 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1 }
        }));

        // Level 4: Triangle
        levels.add(new LevelData("Level 4", new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0 }
        }));

        // Level 5: Checkerboard (Timed 30s for testing)
        levels.add(new LevelData("Level 5", new int[][] {
                { 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4 },
                { 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2 },
                { 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2 },
                { 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4 }
        }));

        // Level 6: Gaps
        levels.add(new LevelData("Level 6", new int[][] {
                { 1, 1, 0, 0, 2, 2, 0, 0, 3, 3, 0, 0, 4, 4, 0, 0, 1, 1, 0, 0 },
                { 1, 0, 0, 2, 2, 0, 0, 3, 3, 0, 0, 4, 4, 0, 0, 1, 1, 0, 0 },
                { 0, 0, 2, 2, 0, 0, 3, 3, 0, 0, 4, 4, 0, 0, 1, 1, 0, 0, 2, 2 },
                { 0, 2, 2, 0, 0, 3, 3, 0, 0, 4, 4, 0, 0, 1, 1, 0, 0, 2, 2 }
        }));

        // Level 7: Diagonal
        levels.add(new LevelData("Level 7", new int[][] {
                { 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4 },
                { 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4 },
                { 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2 },
                { 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2 }
        }));

        // Level 8: V Shape
        levels.add(new LevelData("Level 8", new int[][] {
                { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
                { 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0 },
                { 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0 },
                { 0, 0, 0, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 0, 0, 0 }
        }));

        // Level 9: Islands
        levels.add(new LevelData("Level 9", new int[][] {
                { 1, 1, 0, 0, 2, 2, 2, 0, 0, 3, 3, 0, 0, 4, 4, 4, 0, 0, 1, 1 },
                { 1, 0, 0, 2, 2, 2, 2, 0, 0, 3, 0, 0, 4, 4, 4, 4, 0, 0, 1 },
                { 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0 }
        }));

        // Level 10: Stripes and gaps (Timed)
        levels.add(new LevelData("Level 10", new int[][] {
                { 1, 1, 1, 1, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0, 0, 1, 1, 1, 1, 1 },
                { 3, 3, 3, 3, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 3, 3, 3, 3, 3, 3 },
                { 3, 3, 3, 0, 0, 4, 4, 4, 4, 4, 4, 4, 0, 0, 3, 3, 3, 3, 3 }
        }));

        // Level 11: Rainbow
        levels.add(new LevelData("Level 11", new int[][] {
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
        }));

        // Level 12: Blocks
        levels.add(new LevelData("Level 12", new int[][] {
                { 1, 1, 1, 0, 2, 2, 2, 0, 3, 3, 3, 0, 4, 4, 4, 0, 1, 1, 1, 0 },
                { 1, 1, 0, 0, 2, 2, 0, 0, 3, 3, 0, 0, 4, 4, 0, 0, 1, 1, 0 },
                { 1, 1, 1, 0, 2, 2, 2, 0, 3, 3, 3, 0, 4, 4, 4, 0, 1, 1, 1, 0 },
                { 1, 1, 0, 0, 2, 2, 0, 0, 3, 3, 0, 0, 4, 4, 0, 0, 1, 1, 0 }
        }));

        // Level 13: X Marks the Spot
        levels.add(new LevelData("Level 13", new int[][] {
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                { 0, 1, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0 },
                { 0, 0, 1, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0 }
        }));

        // Level 14: Scattered
        levels.add(new LevelData("Level 14", new int[][] {
                { 1, 0, 2, 0, 3, 0, 4, 0, 1, 0, 2, 0, 3, 0, 4, 0, 1, 0, 2, 0 },
                { 0, 3, 0, 4, 0, 1, 0, 2, 0, 3, 0, 4, 0, 1, 0, 2, 0, 3, 0 },
                { 2, 0, 1, 0, 4, 0, 3, 0, 2, 0, 1, 0, 4, 0, 3, 0, 2, 0, 1, 0 },
                { 0, 4, 0, 3, 0, 2, 0, 1, 0, 4, 0, 3, 0, 2, 0, 1, 0, 4, 0 }
        }));

        // Level 15: The Finale (Timed)
        levels.add(new LevelData("Level 15", new int[][] {
                { 1, 1, 2, 2, 3, 3, 4, 4, 1, 1, 2, 2, 3, 3, 4, 4, 1, 1, 2, 2 },
                { 4, 4, 1, 1, 2, 2, 3, 3, 4, 4, 1, 1, 2, 2, 3, 3, 4, 4, 1 },
                { 3, 3, 4, 4, 1, 1, 2, 2, 3, 3, 4, 4, 1, 1, 2, 2, 3, 3, 4, 4 },
                { 2, 2, 3, 3, 4, 4, 1, 1, 2, 2, 3, 3, 4, 4, 1, 1, 2, 2, 3 },
                { 1, 1, 2, 2, 3, 3, 4, 4, 1, 1, 2, 2, 3, 3, 4, 4, 1, 1, 2, 2 }
        }));

        for (int i = 16; i <= 25; i++) {
            levels.add(new LevelData("Level " + i, levels.get((i - 1) % 15).getLayout(), 0));
        }
    }

    public LevelData getCurrent() {
        if (levels.isEmpty())
            return null;
        return levels.get(currentIndex % levels.size());
    }

    public void next() {
        if (hasNext()) {
            currentIndex++;
        }
    }

    public void loadLevel(BallWorld world, int maxColors) {
        LevelData current = getCurrent();
        if (current != null) {
            int[][] layout = current.getLayout();
            int radius = 15;
            int worldWidth = world.getScreenWidth();
            int maxCols = worldWidth / (radius * 2);
            
            world.clearAll();
            for (int r = 0; r < layout.length; r++) {
                // Crop columns to fit worldWidth
                int colsToRender = Math.min(layout[r].length, maxCols);
                for (int c = 0; c < colsToRender; c++) {
                    int colorId = layout[r][c];
                    if (colorId > 0) {
                        double x = (c * radius * 2) + radius + (r % 2 == 1 ? radius : 0);
                        // Ensure X is within bounds even with offset
                        if (x + radius > worldWidth) x = worldWidth - radius;
                        
                        double y = world.CEIL_Y + (r * radius * 1.7) + radius;
                        Ball b = new Ball(x, y, radius, BallPalette.forId(colorId));
                        
                        double rand = Math.random();
                        if (rand < 0.05) b.setType(Ball.Type.BOMB);
                        else if (rand < 0.10) b.setType(Ball.Type.COUNTDOWN);
                        
                        world.getBalls().add(b);
                    }
                }
            }
        }
    }

    public boolean hasNext() {
        return currentIndex < difficulty.getTotalLevels() - 1;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void reset() {
        currentIndex = 0;
    }

    public int getCurrentUnlocked() {
        return currentUnlocked;
    }

    public void setCurrentUnlocked(int unlocked) {
        this.currentUnlocked = Math.max(1, Math.min(unlocked, difficulty.getTotalLevels()));
    }

    public void unlockNextLevel() {
        if (currentIndex + 1 == currentUnlocked && currentUnlocked < difficulty.getTotalLevels()) {
            currentUnlocked++;
        }
    }

    public void setCurrentIndex(int index) {
        this.currentIndex = Math.max(0, Math.min(index, currentUnlocked - 1));
    }
}
