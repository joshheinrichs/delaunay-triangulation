package settings;

import javafx.scene.control.CheckBox;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Setting to toggle the display of Gabriel Circles
 */
public class GabrielCircleSetting extends DelaunayTriangulationSetting {

    /**
     * Constructs a new {@link GabrielCircleSetting}, which acts upon the given adapter.
     * @param adapter Adapter which is modifiable by this setting.
     */
    public GabrielCircleSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        CheckBox checkBox = new CheckBox("Gabriel Circles");
        checkBox.setSelected(dt.isGabrielCirclesVisible());
        checkBox.setOnMouseClicked(t -> dt.setGabrielCirclesVisible(((CheckBox) t.getSource()).isSelected()));

        root.getChildren().add(checkBox);
    }
}
