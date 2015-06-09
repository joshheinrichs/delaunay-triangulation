package settings;

import javafx.scene.Group;

/**
 * Created by joshheinrichs on 15-05-27.
 */
public abstract class Setting {
    Group root = new Group();

    public Group getRoot() {
        return root;
    }
}
