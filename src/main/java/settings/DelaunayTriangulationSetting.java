package settings;

import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Created by joshheinrichs on 15-05-27.
 */
public abstract class DelaunayTriangulationSetting extends Setting {

    DelaunayTriangulationUiAdapter dt;

    public DelaunayTriangulationSetting(DelaunayTriangulationUiAdapter dt) {
        this.dt = dt;
    }

}
