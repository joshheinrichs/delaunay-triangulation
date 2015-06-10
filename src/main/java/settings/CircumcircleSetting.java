package settings;

import javafx.scene.control.CheckBox;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Created by joshheinrichs on 15-05-27.
 */
public class CircumcircleSetting extends DelaunayTriangulationSetting {

    public CircumcircleSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        CheckBox checkBox = new CheckBox("Circumcircles");
        checkBox.setSelected(dt.isCircumcirclesVisible());
        checkBox.setOnMouseClicked(t -> dt.setCircumcirclesVisible(((CheckBox) t.getSource()).isSelected()));

        root.getChildren().add(checkBox);
    }
}
