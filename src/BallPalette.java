import java.awt.Color;

/**
 * Single source of truth for all ball colors.
 * Every site that creates Ball objects must use these constants so that
 * balls with the same colorId are always rendered identically.
 *
 *  Index 0 → colorId 1 (RED)
 *  Index 1 → colorId 2 (BLUE)
 *  Index 2 → colorId 3 (GREEN)
 *  Index 3 → colorId 4 (YELLOW)
 *  Index 4 → colorId 5 (PURPLE)
 *  Index 5 → colorId 6 (ORANGE)
 */
public final class BallPalette {

    private BallPalette() {}

    public static final Color[] COLORS = {
        new Color(235, 100, 110), // darker pastel red
        new Color(245, 160,  80), // darker pastel orange
        new Color(230, 200,  50), // darker pastel yellow
        new Color(100, 200, 120), // darker pastel green
        new Color(100, 170, 235), // darker pastel blue
        new Color(160, 130, 220)  // darker pastel purple
    };

    /**
     * Returns the Color for a 1-based colorId (as used in level int[][] layouts).
     * Clamps to valid range so invalid ids never throw.
     */
    public static Color forId(int colorId) {
        int idx = Math.max(0, Math.min(colorId - 1, COLORS.length - 1));
        return COLORS[idx];
    }

    /** Returns a random Color from the palette, bounded by maxColors. */
    public static Color random(int maxColors) {
        int max = Math.min(maxColors, COLORS.length);
        max = Math.max(1, max); // At least 1 color
        return COLORS[(int)(Math.random() * max)];
    }

    /** Returns a random Color from the full palette. */
    public static Color random() {
        return random(COLORS.length);
    }
}
