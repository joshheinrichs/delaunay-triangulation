package settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Enables or disables the display of alpha stable edges.
 */
public class DelaunayAlphaStableSetting extends DelaunayTriangulationSetting {

    static final double ALPHA_INPUT_WIDTH = 80.d;

    /**
     * Constructs a new {@link DelaunayAlphaStableSetting}, which enables or disables the display of alpha stable edges.
     * @param adapter The adapter which this setting modifies.
     */
    public DelaunayAlphaStableSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        HBox hBox = new HBox(5);
        hBox.setPadding(new Insets(5));
        hBox.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().add(hBox);

        final TextField alpha = new TextField(Double.toString(dt.getAlphaStability()));
        alpha.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                dt.setAlphaStability(Double.parseDouble(newValue));
                dt.draw();
            } catch (NumberFormatException e) {
                dt.setAlphaStability(Double.NaN);
                dt.draw();
            }
        });
        alpha.setPrefWidth(ALPHA_INPUT_WIDTH);

        final CheckBox checkBox = new CheckBox("Alpha Stable");
        checkBox.setSelected(dt.isAlphaVisible());
        checkBox.setOnMouseClicked(t -> {
            dt.setAlphaVisible(checkBox.isSelected());
            alpha.setDisable(!checkBox.isSelected());
            dt.draw();
        });

        hBox.getChildren().add(checkBox);
        hBox.getChildren().add(alpha);
    }
}
