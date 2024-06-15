package utils;

import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;

import java.util.Arrays;
import java.util.List;

/**
 * Contains methods to help working with actions
 */
public class ActionsUtils {

    /**
     * Return a number from an actionList to represent an Action
     *
     * @return 1 : Playing a movement card
     */
    public static int actionListToInt(ActionList actionList) {
        int[] playMovementCard = {1, 2, 3, 1, 2};
        int[] playTakeCard = {1, 2};
        int[] playHeroCard = {0, 3, 1, 2, 1, 0};

        int listSize = actionList.getActions().size();
        int[] outActionIdArray = new int[listSize];
        int i = -1;

        for (List<GameAction> gameActions : actionList.getActions()) {
            i++;
            if (gameActions.toString().contains("RemoveFromContainerAction")) {
                outActionIdArray[i] = 1;
                continue;
            }

            if (gameActions.toString().contains("PutInContainerAction")) {
                outActionIdArray[i] = 2;
                continue;
            }

            if (gameActions.toString().contains("MoveWithinContainerAction")) {
                outActionIdArray[i] = 3;
                continue;
            }

            if (gameActions.toString().contains("Put In ContainerAction")) {
                outActionIdArray[i] = 4;
            }
        }

        /*
         * Creating an array to match with the current one
         * will determine which action is played
         */
        if (Arrays.equals(outActionIdArray, playMovementCard)) {
            return 1;
        }
        if (Arrays.equals(outActionIdArray, playTakeCard)) {
            return 2;
        }
        if (Arrays.equals(outActionIdArray, playHeroCard)){
            return 3;
        }
        return 0;

    }

}
