package control;

import boardifier.model.action.ActionList;

public class ActionPoints implements Comparable<ActionPoints> {
    int point;
    ActionList al;

    public ActionPoints(ActionList al, int point) {
        this.al = al;
        this.point = point;
    }


    @Override
    public int compareTo(ActionPoints actionPoints) {
        return Integer.compare(actionPoints.point, this.point);
    }

    @Override
    public String toString() {
        return "ActionPoints{" +
                "point=" + point +
                ", al=" + al +
                '}';
    }
}


