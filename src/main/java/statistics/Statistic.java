package statistics;

import models.Model;

/**
 * Created by joshheinrichs on 15-05-08.
 */
public abstract class Statistic {

    Model model;

    boolean enabled = true;
    boolean immediateUpdate = true;

    public abstract void calculate();
}
