package h13.model.gameplay;

import h13.model.gameplay.sprites.Enemy;
import h13.shared.Utils;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import static h13.controller.GameConstants.*;

/**
 * The {@link EnemyMovement} class is responsible for moving the {@linkplain Enemy enemies} in a grid.
 */
public class EnemyMovement implements Updatable {

    // --Variables-- //

    /**
     * The current movement direction
     */
    private Direction direction;

    /**
     * The current movement speed
     */
    private double velocity = INITIAL_ENEMY_MOVEMENT_VELOCITY;

    /**
     * The Next y-coordinate to reach
     */
    private double yTarget = 0;

    /**
     * The current {@link GameState}
     */
    private final GameState gameState;

    // --Constructors-- //

    /**
     * Creates a new EnemyMovement.
     *
     * @param gameState The enemy controller.
     */
    public EnemyMovement(final GameState gameState) {
        this.gameState = gameState;
        nextRound();
    }

    // --Getters and Setters-- //

    /**
     * Gets the current {@link #velocity}.
     *
     * @return The current {@link #velocity}.
     * @see #velocity
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * Sets the current {@link #velocity} to the given value.
     *
     * @param velocity The new {@link #velocity}.
     * @see #velocity
     */
    public void setVelocity(final double velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the current {@link #direction}.
     *
     * @return The current {@link #direction}.
     * @see #direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the current {@link #direction} to the given value.
     *
     * @param direction The new {@link #direction}.
     * @see #direction
     */
    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    /**
     * Checks whether the bottom was reached.
     *
     * @return {@code true} if the bottom was reached, {@code false} otherwise.
     */
    public boolean bottomWasReached() {
        return getEnemyBounds().getMaxY() >= ORIGINAL_GAME_BOUNDS.getMaxY();
    }

    /**
     * Gets the enemy controller.
     *
     * @return The enemy controller.
     */
    public GameState getGameState() {
        return gameState;
    }

    // --Utility Methods-- //

    /**
     * Creates a BoundingBox around all alive enemies.
     *
     * @return The BoundingBox.
     */
    public Bounds getEnemyBounds() {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        for (Enemy enemy : getGameState().getEnemies()) {
            if (enemy.getBounds().getMinX() < minX) {
                minX = enemy.getBounds().getMinX();
            }
            if (enemy.getBounds().getMinY() < minY) {
                minY = enemy.getBounds().getMinY();
            }
            if (enemy.getBounds().getMaxX() > maxX) {
                maxX = enemy.getBounds().getMaxX();
            }
            if (enemy.getBounds().getMaxY() > maxY) {
                maxY = enemy.getBounds().getMaxY();
            }
        }
        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Checks whether the target Position of the current movement iteration is reached.
     *
     * @param enemyBounds The BoundingBox of all alive enemies.
     * @return {@code true} if the target Position of the current movement iteration is reached, {@code false} otherwise.
     */
    public boolean targetReached(final Bounds enemyBounds) {
        return (getDirection() == Direction.DOWN && enemyBounds.getMinY() >= yTarget) ||
            (enemyBounds.getMinX() <= ORIGINAL_GAME_BOUNDS.getMinX() && getDirection() == Direction.LEFT) ||
            (enemyBounds.getMaxX() >= ORIGINAL_GAME_BOUNDS.getWidth() && getDirection() == Direction.RIGHT);
    }

    // --Movement-- //

    @Override
    public void update(final double elapsedTime) {
        Bounds bounds = Utils.getNextPosition(getEnemyBounds(), velocity, getDirection(), elapsedTime);

        if(!bottomWasReached()) {
            if(targetReached(bounds)) {
                nextMovement(getEnemyBounds());
                updatePositions(bounds.getMinX() - getEnemyBounds().getMinX(),
                    bounds.getMinY() - getEnemyBounds().getMinY());
            } else {
                updatePositions(bounds.getMinX() - getEnemyBounds().getMinX(),
                    bounds.getMinY() - getEnemyBounds().getMinY());
            }
        }
    }

    /**
     * Updates the positions of all alive enemies.
     *
     * @param deltaX The deltaX.
     * @param deltaY The deltaY.
     */
    public void updatePositions(final double deltaX, final double deltaY) {
        for (Enemy enemy : gameState.getEnemies()) {
            Bounds futureEnemyBound = new BoundingBox(enemy.getX() + deltaX, enemy.getY() + deltaY,
                enemy.getWidth(), enemy.getHeight());
            futureEnemyBound = Utils.clamp(futureEnemyBound);

            enemy.setX(futureEnemyBound.getMinX());
            enemy.setY(futureEnemyBound.getMinY());
        }
    }

    /**
     * Starts the next movement iteration.
     *
     * @param enemyBounds The BoundingBox of all alive enemies.
     */
    public void nextMovement(final Bounds enemyBounds) {
        if(direction.isHorizontal()) {
            setDirection(Direction.DOWN);
            yTarget += VERTICAL_ENEMY_MOVE_DISTANCE;
        } else if (direction.isVertical()) {
            if (enemyBounds.getMaxX() >= ORIGINAL_GAME_BOUNDS.getWidth()) {
                setDirection(Direction.LEFT);
            } else {
                setDirection(Direction.RIGHT);
            }
        }
        setVelocity(velocity + ENEMY_MOVEMENT_SPEED_INCREASE);
    }

    /**
     * Prepares the next round of enemies.
     * Uses {@link h13.controller.GameConstants#INITIAL_ENEMY_MOVEMENT_DIRECTION} and {@link h13.controller.GameConstants#INITIAL_ENEMY_MOVEMENT_VELOCITY} to set the initial values.
     */
    public void nextRound() {
        direction = INITIAL_ENEMY_MOVEMENT_DIRECTION;
        yTarget = HUD_HEIGHT;
    }
}
