package h13.view.gui;

import h13.controller.ApplicationSettings;
import h13.controller.scene.menu.SettingsController;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import static h13.controller.GameConstants.ENEMY_SHOOTING_PROBABILITY;

/**
 * The {@link SettingsScene} is a {@link SubMenuScene} that displays the "Settings" menu.
 */
public class SettingsScene extends SubMenuScene<SettingsController, TabPane> {

    /**
     * Creates a new {@link SettingsScene}.
     */
    public SettingsScene() {
        super(new TabPane(), new SettingsController(), "Settings");
        init();
    }

    /**
     * Initialize the content of the scene.
     */
    private void init() {

        // Checkbox for instantShooting
        this.getController().instantShootingCheckBox = new CheckBox("Instant shooting");

        // Slider for enemyShootingDelay
        Label enemyShootingDelayLabel = new Label();
        enemyShootingDelayLabel.setText("Cooldown for enemies shooting");
        this.getController().enemyShootingDelaySlider = new Slider(0.0, 10000.0,  ApplicationSettings.enemyShootingDelayProperty().get());
        HBox enemyShootingDelayBox = new HBox(this.getController().enemyShootingDelaySlider, enemyShootingDelayLabel);

        // Slider for enemyShootingProbability
        getController().enemyShootingProbabilitySlider = new Slider(0, 1, ENEMY_SHOOTING_PROBABILITY);
        Label enemyShootingProbabilityLabel = new Label();
        enemyShootingProbabilityLabel.setText("Probability for the enemy shooting after cooldown");
        getController().enemyShootingProbabilitySlider.minProperty().set(0);
        getController().enemyShootingProbabilitySlider.maxProperty().set(1);

        HBox enemyShootingProbabilityBox = new HBox(getController().enemyShootingProbabilitySlider, enemyShootingProbabilityLabel);

        // Checkbox for fullscreen
        this.getController().fullscreenCheckBox = new CheckBox("Start game in fullscreen mode");

        // Checkbox for loadTextures
        this.getController().loadTexturesCheckBox = new CheckBox("Load textures of sprites");

        // Checkbox for loadBackground
        this.getController().loadBackgroundCheckBox = new CheckBox("Load background");


        // Adding all elements to vBox
        final var settingsTab1 = new Tab();
        settingsTab1.setContent(this.getController().instantShootingCheckBox);
        final var settingsTab2 = new Tab();
        settingsTab2.setContent(enemyShootingDelayBox);
        final var settingsTab3 = new Tab();
        settingsTab3.setContent(enemyShootingProbabilityBox);
        final var settingsTab4 = new Tab();
        settingsTab4.setContent(this.getController().fullscreenCheckBox);
        final var settingsTab5 = new Tab();
        settingsTab5.setContent(this.getController().loadTexturesCheckBox);
        final var settingsTab6 = new Tab();
        settingsTab6.setContent(this.getController().loadBackgroundCheckBox);

        getContentRoot().getTabs().add(settingsTab1);
        getContentRoot().getTabs().add(settingsTab2);
        getContentRoot().getTabs().add(settingsTab3);
        getContentRoot().getTabs().add(settingsTab4);
        getContentRoot().getTabs().add(settingsTab5);
        getContentRoot().getTabs().add(settingsTab6);
    }
}
