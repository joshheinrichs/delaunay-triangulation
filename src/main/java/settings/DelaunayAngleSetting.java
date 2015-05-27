package settings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import modelAdapters.DelaunayTriangulationAdapter;

/**
 * Created by joshheinrichs on 15-05-27.
 */
public class DelaunayAngleSetting extends Setting {

    static final double ANGLE_INPUT_WIDTH = 80.d;

    final DelaunayTriangulationAdapter dt;

    public DelaunayAngleSetting(DelaunayTriangulationAdapter adapter) {
        this.dt = adapter;

        root = new Group();
        CheckBox checkBox = new CheckBox("Angles");
        checkBox.setSelected(dt.isDelaunayAnglesVisible());
        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                dt.setDelaunayAnglesVisible(((CheckBox) t.getSource()).isSelected());
            }
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().add(hBox);

        final TextField minAngle = new TextField(Double.toString(dt.getMinDelaunayAngle()));
        minAngle.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                try {
                    dt.setMinDelaunayAngle(Double.parseDouble(newValue));
                    dt.draw();
                } catch (NumberFormatException e) {
                    dt.setMaxDelaunayAngle(Double.NaN);
                    dt.draw();
                }
            }
        });
        minAngle.setPrefWidth(ANGLE_INPUT_WIDTH);

        final TextField maxAngle = new TextField(Double.toString(dt.getMaxDelaunayAngle()));
        maxAngle.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                try {
                    dt.setMaxDelaunayAngle(Double.parseDouble(newValue));
                    dt.draw();
                } catch (NumberFormatException e) {
                    dt.setMaxDelaunayAngle(Double.NaN);
                    dt.draw();
                }
            }
        });
        maxAngle.setPrefWidth(ANGLE_INPUT_WIDTH);

        hBox.getChildren().add(checkBox);
        hBox.getChildren().add(minAngle);
        hBox.getChildren().add(maxAngle);
    }


}
