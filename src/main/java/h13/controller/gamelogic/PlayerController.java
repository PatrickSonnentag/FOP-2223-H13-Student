package h13.controller.gamelogic;

import h13.controller.scene.game.GameController;
import h13.model.gameplay.sprites.Player;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Set;

import static h13.controller.GameConstants.*;

/**
 * A {@link PlayerController} is responsible for instantiating and updating the {@linkplain Player players}.
 */
public class PlayerController {
    // --Variables-- //

    /**
     * The {@link Player} controlled by this {@link PlayerController}.
     */
    private final Player player;

    /**
     * The {@link GameController}.
     */
    private final GameController gameController;

    // --Constructors-- //

    /**
     * Creates a new {@link PlayerController}.
     *
     * @param gameController The game controller.
     */
    public PlayerController(final GameController gameController) {
        this.gameController = gameController;
        player = new Player(
            0,
            ORIGINAL_GAME_BOUNDS.getHeight() - SHIP_SIZE,
            PLAYER_VELOCITY,
            gameController.getGameState());
        getGameController().getGameState().getSprites().add(player);
        handleKeyboardInputs();
    }

    // --Getters and Setters-- //

    /**
     * Gets the value of {@link #player} field.
     *
     * @return The value of {@link #player} field.
     * @see #player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the value of {@link #gameController} field.
     *
     * @return The value of {@link #gameController} field.
     * @see #gameController
     */
    public GameController getGameController() {
        return gameController;
    }

    // --Methods-- //

    /**
     * Handles the given {@link KeyEvent} related to a player action.
     *
     * <ul>
     *     <li>The player can move with the arrow keys or the WASD keys.</li>
     *     <li>The player can shoot with the space bar.</li>
     * </ul>
     *
     * @param e A {@link KeyEvent} to handle which relates to a Player action.
     */
    private void playerKeyAction(final KeyEvent e) {
        player.stop();
        player.setKeepShooting(false);
        Set<KeyCode> presses = gameController.getGameInputHandler().getKeysPressed();

        boolean leftPressed = presses.contains(KeyCode.A) || presses.contains(KeyCode.LEFT);
        boolean rightPressed = presses.contains(KeyCode.D) || presses.contains(KeyCode.RIGHT);

        if(leftPressed && !rightPressed) {
            player.moveLeft();
        } if (rightPressed && !leftPressed) {
            player.moveRight();
        } if (presses.contains(KeyCode.SPACE)) {
            player.setKeepShooting(true);
        }
    }

    /**
     * Registers the keyboard inputs to handle the player actions.
     */
    private void handleKeyboardInputs() {
        final var gameInputHandler = getGameController().getGameInputHandler();
        gameInputHandler.addOnKeyPressed(this::playerKeyAction);
        gameInputHandler.addOnKeyReleased(this::playerKeyAction);
    }
}
