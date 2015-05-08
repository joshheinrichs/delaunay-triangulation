package ui;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

/**
 * Created by joshheinrichs on 15-05-08.
 */
public class IndexedCircle extends Circle {
    int index;
    public void setIndex(int index) {this.index = index;}
    public int getIndex() {return this.index;}
}
