package h13.controller.scene.menu;

import h13.controller.ApplicationSettings;
import h13.controller.scene.SceneController;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * A {@link SceneController} that manages the "Settings" scene.
 */
public class SettingsController extends SceneController {

    // --Variables-- //

    /**
     * The checkbox for the "load textures" setting.
     */
    public CheckBox loadTexturesCheckBox;

    /**
     * The checkbox for the "load background" setting.
     */
    public CheckBox loadBackgroundCheckBox;

    /**
     * The checkbox for the "instant shooting" setting.
     */
    public CheckBox instantShootingCheckBox;

    /**
     * The checkbox for the "full screen" setting.
     */
    public CheckBox fullscreenCheckBox;

    /**
     * The slider for the "music volume" setting.
     */
    public Slider enemyShootingDelaySlider;

    public Slider enemyShootingProbabilitySlider;

    @Override
    public String getTitle() {
        return "Space Invaders - Settings";
    }

    @Override
    public void initStage(final Stage stage) {
        super.initStage(stage);
        instantShootingCheckBox.selectedProperty().bindBidirectional(ApplicationSettings.instantShootingProperty());
        enemyShootingDelaySlider.valueProperty().bindBidirectional(ApplicationSettings.enemyShootingDelayProperty());
        enemyShootingProbabilitySlider.valueProperty().bindBidirectional(ApplicationSettings.enemyShootingProbabilityProperty());
        fullscreenCheckBox.selectedProperty().bindBidirectional(ApplicationSettings.fullscreenProperty());
        loadTexturesCheckBox.selectedProperty().bindBidirectional(ApplicationSettings.loadTexturesProperty());
        loadBackgroundCheckBox.selectedProperty().bindBidirectional(ApplicationSettings.loadBackgroundProperty());
    }
}
