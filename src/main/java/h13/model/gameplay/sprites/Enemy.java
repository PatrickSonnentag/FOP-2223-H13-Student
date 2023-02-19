package h13.model.gameplay.sprites;

import h13.controller.ApplicationSettings;
import h13.model.gameplay.Direction;
import h13.model.gameplay.GameState;
import javafx.scene.paint.Color;

import java.util.Stack;

/**
 * An {@link Enemy} is a {@link BattleShip} that is moved by the {@link h13.controller.gamelogic.EnemyController} and shoots downwards.
 *
 * @see h13.model.gameplay.EnemyMovement
 */
public class Enemy extends BattleShip {
    // --Variables-- //

    private double totalTime;

    /**
     * The enemy's X-index of the enemy grid.
     */
    private final int xIndex;
    /**
     * The enemy's Y-index of the enemy grid.
     */
    private final int yIndex;
    /**
     * The amount of points the enemy is worth when it is destroyed.
     */
    private final int pointsWorth;

    // --Constructors-- //

    /**
     * Creates a new enemy.
     *
     * @param xIndex      The enemy's X-index of the enemy grid.
     * @param yIndex      The enemy's Y-index of the enemy grid.
     * @param velocity    The enemy's velocity.
     * @param pointsWorth The amount of points the enemy is worth when it is destroyed.
     * @param gameState   The game state.
     */
    public Enemy(final int xIndex, final int yIndex, final double velocity, final int pointsWorth,
                 final GameState gameState) {
        super(0, 0, velocity, Color.YELLOW, 1, gameState);
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.pointsWorth = pointsWorth;
        // get available textures
        final var random = new java.util.Random().nextInt(1, 3);
        // choose one randomly
        loadTexture("/h13/images/sprites/enemy" + random + ".png");
        totalTime = 0.0;
    }

    // --Getters and Setters-- //

    /**
     * Gets the enemy's X-index of the enemy grid.
     *
     * @return The enemy's X-index of the enemy grid.
     */
    public int getXIndex() {
        return xIndex;
    }

    /**
     * Gets the enemy's Y-index of the enemy grid.
     *
     * @return The enemy's Y-index of the enemy grid.
     */
    public int getYIndex() {
        return yIndex;
    }

    /**
     * Gets the amount of points the enemy is worth when it is destroyed.
     *
     * @return The amount of points the enemy is worth when it is destroyed.
     */
    public int getPointsWorth() {
        return pointsWorth;
    }

    // --Utility Methods-- //

    /**
     * Overloaded method from {@link BattleShip#shoot(Direction)} with the {@link Direction#DOWN} parameter.
     *
     * @see BattleShip#shoot(Direction)
     */
    public void shoot() {
        shoot(Direction.DOWN);
    }

    // --update-- //
    @Override
    public void update(final double elapsedTime) {
        super.update(elapsedTime);

        totalTime += elapsedTime;
        if(totalTime * 1000 > ApplicationSettings.enemyShootingDelayProperty().get()) {
            if(Math.random() <= ApplicationSettings.enemyShootingProbabilityProperty().get()) {
                shoot();
                totalTime = 0.0;
            }
        }
    }
}
