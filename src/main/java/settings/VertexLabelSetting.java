package settings;

import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Created by joshheinrichs on 15-05-28.
 */
public class VertexLabelSetting extends DelaunayTriangulationSetting {

    public VertexLabelSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        CheckBox checkBox = new CheckBox("Vertex Labels");
        checkBox.setSelected(dt.isVertexLabelsVisible());
        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                dt.setVertexLabelsVisible(((CheckBox) t.getSource()).isSelected());
            }
        });

        root.getChildren().add(checkBox);
    }


}
