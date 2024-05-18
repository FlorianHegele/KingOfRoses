package control;

import boardifier.model.action.ActionList;

public class ActionPoints implements Comparable<ActionPoints> {
    int point;
    ActionList al;

    public ActionPoints(ActionList al, int point) {
        this.al = al;
        this.point = point;
    }


    /**
     * Compares this object with the specified object for order.
     * Effectively helps to sort actions based on the points from the action that has the most points to the one with the least points
     * @param actionPoints the action points to compare with
     * @return a negative integer, zero, or a positive integer as this object is more than, equal to, or less than the specified object.
     */
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


