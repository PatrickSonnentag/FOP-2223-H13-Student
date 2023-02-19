package h13.view.gui;

import h13.controller.ApplicationSettings;
import h13.controller.GameConstants;
import h13.controller.scene.game.GameController;
import h13.model.gameplay.Updatable;
import h13.model.gameplay.sprites.Bullet;
import h13.model.gameplay.sprites.Enemy;
import h13.model.gameplay.sprites.Player;
import h13.model.gameplay.sprites.Sprite;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Scale;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static h13.controller.GameConstants.*;


/**
 * A {@link GameBoard} is a {@link Canvas} on which the
 * {@link Sprite}s as well as the HUD are drawn.
 * It is part of the {@link GameScene} and is controlled by a
 * {@link GameController}.
 */
public class GameBoard extends Canvas implements Updatable {

    // --Variables-- //

    /**
     * The {@link GameScene} that contains this {@link GameBoard}.
     *
     * @see GameScene
     */
    private final GameScene gameScene;

    /**
     * The Background of this {@link GameBoard}.
     *
     * @see Image
     */
    private @Nullable Image backgroundImage;

    // --Constructors-- //

    /**
     * Creates a new GameBoard with the given parameters.
     *
     * @param width     The width of the {@link GameBoard}.
     * @param height    The height of the {@link GameBoard}.
     * @param gameScene The {@link GameScene} that contains this {@link GameBoard}.
     */
    public GameBoard(final double width, final double height, final GameScene gameScene) {
        super(width, height);
        this.gameScene = gameScene;
        if (ApplicationSettings.loadBackgroundProperty().get()) {
            try {
                backgroundImage = new Image("/h13/images/wallpapers/Galaxy3.jpg");
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    // --Utility Methods-- //

    /**
     * Calculates the Scale factor of the {@link GameBoard} based on the
     * {@link GameScene}'s width and height.
     *
     * @return The calculated Scale factor.
     */
    private double getScale() {
        return getWidth() / ORIGINAL_GAME_BOUNDS.getWidth();
    }

    /**
     * Gets the {@link GameController} that controls this {@link GameBoard}.
     *
     * @return The {@link GameController} that controls this {@link GameBoard}.
     * @see GameController
     */
    public GameController getGameController() {
        return gameScene.getController();
    }

    // --Methods-- //

    @Override
    public void update(final double elapsedTime) {
        final double scale = getScale();
        final var gc = getGraphicsContext2D();
        gc.setTransform(new Affine(new Scale(scale, scale)));

        drawBackground(gc);
        drawSprites(gc);
        drawHUD(gc);
        drawBorder(gc);
    }

    /**
     * Draws the background of this {@link GameBoard} to the given
     * {@link GraphicsContext} based on the
     * {@link GameConstants#ORIGINAL_GAME_BOUNDS}.
     * <br>
     * If the background is not set, the background is cleared with the
     * {@link GraphicsContext#clearRect(double, double, double, double)} method.
     *
     * @param gc The {@link GraphicsContext} to draw the background to.
     */
    private void drawBackground(final GraphicsContext gc) {
        gc.setFill(gameScene.getFill());
        if(backgroundImage != null) {
            gc.drawImage(backgroundImage, ORIGINAL_GAME_BOUNDS.getMinX(), ORIGINAL_GAME_BOUNDS.getMinY(),
                ORIGINAL_GAME_BOUNDS.getWidth(), ORIGINAL_GAME_BOUNDS.getHeight());
        } else {
            gc.clearRect(ORIGINAL_GAME_BOUNDS.getMinX(), ORIGINAL_GAME_BOUNDS.getMinY(),
                ORIGINAL_GAME_BOUNDS.getWidth(), ORIGINAL_GAME_BOUNDS.getHeight());
        }
        gc.restore();
    }

    /**
     * Draws the sprites of this {@link GameBoard} to the given
     * {@link GraphicsContext} using the {@link SpriteRenderer} class.
     * <br>
     * The sprites are drawn in the following order (from bottom to top):
     * <ol>
     * <li>Bullets</li>
     * <li>Enemies</li>
     * <li>Player</li>
     * <li>Others (currently none)</li>
     * </ol>
     *
     * @param gc The {@link GraphicsContext} to draw the sprites to.
     */
    private void drawSprites(final GraphicsContext gc) {
        List<Sprite> sprites = getGameController().getGameState().getSprites().stream().toList();

        for (Sprite sprite : sprites) {
            if (sprite instanceof Bullet) {
                SpriteRenderer.renderSprite(gc, sprite);
            }
        }

        for (Sprite sprite : sprites) {
            if (sprite instanceof Enemy) {
                SpriteRenderer.renderSprite(gc, sprite);
            }
        }

        for (Sprite sprite : sprites) {
            if (sprite instanceof Player) {
                SpriteRenderer.renderSprite(gc, sprite);
            }
        }

        for (Sprite sprite : sprites) {
            if (!(sprite instanceof Bullet) && !(sprite instanceof Enemy) && !(sprite instanceof Player)) {
                SpriteRenderer.renderSprite(gc, sprite);
            }
        }
    }

    /**
     * Draws the Heads-Up-Display (HUD) of this {@link GameBoard} to the given
     * {@link GraphicsContext}.
     * <br>
     * The HUD contains the following information:
     * <ul>
     * <li>Player Score (top left)</li>
     * <li>Remaining Lives (top right)</li>
     * </ul>
     * <br>
     *
     * @param gc The {@link GraphicsContext} to draw the HUD to.
     *
     */
    private void drawHUD(final GraphicsContext gc) {
        String scoreString = "Score: "+ gameScene.getController().getPlayer().getScore();
        Text scoreText = new Text(scoreString);
        scoreText.setFont(HUD_FONT);

        String livesString = "Lives: "+ gameScene.getController().getPlayer().getHealth();
        Text livesText = new Text(livesString);
        livesText.setFont(HUD_FONT);

        gc.setFont(HUD_FONT);
        gc.setFill(HUD_TEXT_COLOR);

        gc.fillText(scoreString, ORIGINAL_GAME_BOUNDS.getMinX() + HUD_PADDING,
            ORIGINAL_GAME_BOUNDS.getMinY() + scoreText.getLayoutBounds().getHeight() + HUD_PADDING);
        gc.fillText(livesString, ORIGINAL_GAME_BOUNDS.getMaxX() - livesText.getLayoutBounds().getWidth() - HUD_PADDING,
            ORIGINAL_GAME_BOUNDS.getMinY() + livesText.getLayoutBounds().getHeight() + HUD_PADDING);
    }

    /**
     * Draws the border of this {@link GameBoard} to the given
     * {@link GraphicsContext}.
     * <br>
     * The border is drawn using the
     * {@link GraphicsContext#strokeRect(double, double, double, double)} method.
     * <br>
     * The border has the color {@link GameConstants#BORDER_COLOR} and the width
     * {@link GameConstants#BORDER_WIDTH}.
     *
     * @param gc The {@link GraphicsContext} to draw the border to.
     */
    private static void drawBorder(final GraphicsContext gc) {
        gc.setStroke(BORDER_COLOR);
        gc.setLineWidth(BORDER_WIDTH);
        gc.strokeRect(ORIGINAL_GAME_BOUNDS.getMinX(), ORIGINAL_GAME_BOUNDS.getMinY(),
            ORIGINAL_GAME_BOUNDS.getWidth(),
            ORIGINAL_GAME_BOUNDS.getHeight());
        gc.restore();
    }
}
