package settings;

import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * A setting specifically for Delaunay triangulations.
 */
public abstract class DelaunayTriangulationSetting extends Setting {

    DelaunayTriangulationUiAdapter dt;

    public DelaunayTriangulationSetting(DelaunayTriangulationUiAdapter dt) {
        this.dt = dt;
    }

}
