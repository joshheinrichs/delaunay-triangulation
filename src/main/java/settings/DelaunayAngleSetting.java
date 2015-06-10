package settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Created by joshheinrichs on 15-05-27.
 */
public class DelaunayAngleSetting extends DelaunayTriangulationSetting {

    static final double ANGLE_INPUT_WIDTH = 80.d;

    public DelaunayAngleSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        HBox hBox = new HBox(5);
        hBox.setPadding(new Insets(5));
        hBox.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().add(hBox);

        final TextField minAngle = new TextField(Double.toString(dt.getMinDelaunayAngle()));
        minAngle.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                dt.setMinDelaunayAngle(Double.parseDouble(newValue));
                dt.draw();
            } catch (NumberFormatException e) {
                dt.setMinDelaunayAngle(Double.NaN);
                dt.draw();
            }
        });
        minAngle.setPrefWidth(ANGLE_INPUT_WIDTH);

        final TextField maxAngle = new TextField(Double.toString(dt.getMaxDelaunayAngle()));
        maxAngle.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                dt.setMaxDelaunayAngle(Double.parseDouble(newValue));
                dt.draw();
            } catch (NumberFormatException e) {
                dt.setMaxDelaunayAngle(Double.NaN);
                dt.draw();
            }
        });
        maxAngle.setPrefWidth(ANGLE_INPUT_WIDTH);

        final CheckBox checkBox = new CheckBox("Angles");
        checkBox.setSelected(dt.isDelaunayAnglesVisible());
        checkBox.setOnMouseClicked(t -> {
            dt.setDelaunayAnglesVisible(((CheckBox) t.getSource()).isSelected());
            minAngle.setDisable(!checkBox.isSelected());
            maxAngle.setDisable(!checkBox.isSelected());
        });

        hBox.getChildren().add(checkBox);
        hBox.getChildren().add(minAngle);
        hBox.getChildren().add(maxAngle);
    }


}
