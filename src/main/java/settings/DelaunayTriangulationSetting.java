package settings;

import modelAdapters.DelaunayTriangulationAdapter;

/**
 * Created by joshheinrichs on 15-05-27.
 */
public abstract class DelaunayTriangulationSetting extends Setting {

    DelaunayTriangulationAdapter dt;

    public DelaunayTriangulationSetting(DelaunayTriangulationAdapter dt) {
        this.dt = dt;
    }

}
