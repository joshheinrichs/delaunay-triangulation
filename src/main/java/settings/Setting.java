package settings;

import javafx.scene.Group;

/**
 * Settings generally effect what is shown in a Model. This is distinct from Tools, which generally modify the model's
 * state.
 */
public abstract class Setting {

    Group root = new Group();

    /**
     * Returns the Setting's UI elements to be displayed in a toolbar.
     */
    public Group getRoot() {
        return root;
    }
}
