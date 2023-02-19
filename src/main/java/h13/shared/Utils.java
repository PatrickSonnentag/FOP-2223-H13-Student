package h13.shared;

import h13.model.gameplay.Direction;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import static h13.controller.GameConstants.ORIGINAL_GAME_BOUNDS;

/**
 * A {@link Utils} class containing utility methods.
 */
public class Utils {

    /**
     * Returns the closest position for the given {@link Bounds} that is within the game bounds.
     *
     * @param bounds The bounds to be clamped.
     * @return the clamped coordinate.
     * @see <a href="https://en.wikipedia.org/wiki/Clamping_(graphics)">Clamping_(graphics)</a>
     * @see h13.controller.GameConstants
     */
    public static Bounds clamp(final Bounds bounds) {
        double minX = Math.max(ORIGINAL_GAME_BOUNDS.getMinX(),
            Math.min(ORIGINAL_GAME_BOUNDS.getMaxX() - bounds.getWidth(), bounds.getMinX()));
        double minY = Math.max(ORIGINAL_GAME_BOUNDS.getMinY(),
            Math.min(ORIGINAL_GAME_BOUNDS.getMaxY() - bounds.getHeight(), bounds.getMinY()));

        return new BoundingBox(minX, minY,
            bounds.getWidth(), bounds.getHeight());
    }

    /**
     * Returns the Moved Bounding Box for the given {@link Bounds}, {@link Direction}, velocity and time.
     *
     * @param bounds      The bounds to be moved.
     * @param velocity    The velocity of the movement.
     * @param direction   The direction of the movement.
     * @param elapsedTime The time elapsed since the last movement.
     * @return the moved bounds
     */
    public static Bounds getNextPosition(final Bounds bounds, final double velocity, final Direction direction, final double elapsedTime) {
        double constant = velocity * elapsedTime;
        double newMinX = bounds.getMinX() + direction.getX() * constant;
        double newMinY = bounds.getMinY() + direction.getY() * constant;
        return new BoundingBox(newMinX, newMinY, bounds.getWidth(), bounds.getHeight());
    }
}
