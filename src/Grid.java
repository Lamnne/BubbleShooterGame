import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int rows;
    private int cols;
    private int cellSize;
    private Ball[][] cells;

    public Grid(int rows, int cols, int cellSize) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        this.cells = new Ball[rows][cols];
    }

    // Y coordinate of the center of a cell
    public double getCellY(int row) {
        double rowHeight = cellSize * Math.sqrt(3) / 2.0;
        return (cellSize / 2.0) + row * rowHeight;
    }

    // X coordinate of the center of a cell
    public double getCellX(int row, int col) {
        if (row % 2 == 0) {
            return col * cellSize + cellSize / 2.0;
        } else {
            return col * cellSize + cellSize;
        }
    }

    public boolean checkCollision(Ball movingBall) {
        double radius = movingBall.getRadius();
        double collisionDistSq = (2 * radius) * (2 * radius);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Ball b = cells[row][col];
                if (b != null) {
                    double dx = movingBall.getX() - b.getX();
                    double dy = movingBall.getY() - b.getY();
                    if (dx * dx + dy * dy <= collisionDistSq) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** @deprecated Use {@link #snapBallAndGetCell(Ball)} instead. */
    @Deprecated
    public void snapBall(Ball movingBall) {
        snapBallAndGetCell(movingBall);
    }

    public double getTopYOffset() {
        return cellSize / 2.0;
    }

    // -----------------------------------------------------------------------
    // Accessors used by ClusterFinder and FloatingFinder
    // -----------------------------------------------------------------------

    public Ball[][] getCells() {
        return cells;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * Returns the (up to) 6 valid hex neighbors of (row, col).
     * The grid uses a "pointy-top" staggered layout where odd rows are shifted
     * half a cell to the right, so neighbor offsets differ by row parity.
     *
     * Neighbor layout:
     * Even rows: NW=(-1,-1) NE=(-1, 0) W=(0,-1) E=(0,+1) SW=(+1,-1) SE=(+1, 0)
     * Odd rows: NW=(-1, 0) NE=(-1,+1) W=(0,-1) E=(0,+1) SW=(+1, 0) SE=(+1,+1)
     */
    public List<int[]> getNeighbors(int row, int col) {
        List<int[]> neighbors = new ArrayList<>();
        int[][] offsets;
        if (row % 2 == 0) {
            offsets = new int[][] {
                    { -1, -1 }, { -1, 0 },
                    { 0, -1 }, { 0, +1 },
                    { +1, -1 }, { +1, 0 }
            };
        } else {
            offsets = new int[][] {
                    { -1, 0 }, { -1, +1 },
                    { 0, -1 }, { 0, +1 },
                    { +1, 0 }, { +1, +1 }
            };
        }
        for (int[] off : offsets) {
            int nr = row + off[0];
            int nc = col + off[1];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                // Odd rows have one fewer valid column
                if (nr % 2 != 0 && nc == cols - 1)
                    continue;
                neighbors.add(new int[] { nr, nc });
            }
        }
        return neighbors;
    }

    /**
     * Snaps the moving ball to the nearest empty cell and returns the cell
     * coordinates as int[]{row, col}, or null if no valid cell was found.
     */
    public int[] snapBallAndGetCell(Ball movingBall) {
        int bestRow = -1;
        int bestCol = -1;
        double minDistance = Double.MAX_VALUE;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row % 2 != 0 && col == cols - 1)
                    continue;
                if (cells[row][col] == null) {
                    double cellX = getCellX(row, col);
                    double cellY = getCellY(row);
                    double dx = movingBall.getX() - cellX;
                    double dy = movingBall.getY() - cellY;
                    double distSq = dx * dx + dy * dy;
                    if (distSq < minDistance) {
                        minDistance = distSq;
                        bestRow = row;
                        bestCol = col;
                    }
                }
            }
        }

        // Only snap if the ball is actually close enough to a cell.
        // Threshold = 1.5 × cellSize (squared to avoid sqrt).
        double snapThresholdSq = (cellSize * 1.5) * (cellSize * 1.5);

        if (bestRow != -1 && bestCol != -1 && minDistance <= snapThresholdSq) {
            movingBall.setX(getCellX(bestRow, bestCol));
            movingBall.setY(getCellY(bestRow));
            cells[bestRow][bestCol] = movingBall;
            return new int[] { bestRow, bestCol };
        }
        return null;
    }

    /**
     * Removes all balls at the given list of [row, col] positions.
     */
    public void removeBalls(List<int[]> positions) {
        for (int[] pos : positions) {
            cells[pos[0]][pos[1]] = null;
        }
    }

    public void draw(Graphics2D g2) {
        // Draw empty grid slots for visualization
        g2.setColor(new Color(50, 50, 50));
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row % 2 != 0 && col == cols - 1)
                    continue;

                int cx = (int) getCellX(row, col);
                int cy = (int) getCellY(row);
                int r = cellSize / 2;
                g2.drawOval(cx - r, cy - r, cellSize, cellSize);
            }
        }

        // Draw occupied balls
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (cells[row][col] != null) {
                    cells[row][col].draw(g2);
                }
            }
        }
    }
}
