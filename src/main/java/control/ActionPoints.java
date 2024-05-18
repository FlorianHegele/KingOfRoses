package control;

import boardifier.model.action.ActionList;

import java.util.Objects;

/**
 * This class represents a pair of action list and points.
 * It is used by AI to evaluate and compare different moves based on their calculated points.
 * The points represent values such as the length of a line of pawns.
 * The action list represents the actions to be executed to achieve a goal, like extending a line or breaking an opponent's line.
 */
public class ActionPoints implements Comparable<ActionPoints> {

    private final int point;
    private final ActionList actionList;

    /**
     * Constructs an ActionPoints object with the specified action list and points.
     *
     * @param actionList the list of actions to be performed.
     * @param point the points associated with these actions.
     */
    public ActionPoints(ActionList actionList, int point) {
        this.actionList = actionList;
        this.point = point;
    }

    /**
     * Returns the points associated with the action list.
     *
     * @return the points.
     */
    public int getPoint() {
        return point;
    }

    /**
     * Returns the action list associated with the points.
     *
     * @return the action list.
     */
    public ActionList getActionList() {
        return actionList;
    }

    /**
     * Compares this object with the specified object for order.
     * This helps to sort actions based on their points in descending order,
     * from the action with the most points to the one with the least points.
     *
     * @param actionPoints the action points to compare with.
     * @return a negative integer, zero, or a positive integer as this object has more points,
     *         equal points, or fewer points than the specified object.
     */
    @Override
    public int compareTo(ActionPoints actionPoints) {
        return Integer.compare(actionPoints.point, this.point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionPoints that)) return false;
        return point == that.point && Objects.equals(actionList, that.actionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, actionList);
    }

    /**
     * Returns a string representation of the object.
     * This includes the points and the action list.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "ActionPoints{" +
                "point=" + point +
                ", actionList=" + actionList +
                '}';
    }
}


