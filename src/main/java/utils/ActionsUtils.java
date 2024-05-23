package utils;

import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;

import java.util.List;
import java.util.Arrays;
/*
 * Contains methods to help working with actions
 */
public class ActionsUtils {

    /*
     *  Return a number from an actionList to represent an Action
     *  @return 1 : Playing a movement card
     */
    public static int actionListToInt(ActionList actionList) {
        int[] playMovementCard = {1,2,3,1,2};

        int listSize = actionList.getActions().size();
        System.out.println(listSize);
        int[] outActionIdArray = new int[listSize];
        int i = -1;

        for(List<GameAction> gameActions : actionList.getActions()) {
            i++;
            if(gameActions.toString().contains("RemoveFromContainerAction")){
                outActionIdArray[i] = 1;
                continue;
            }

            if(gameActions.toString().contains("PutInContainerAction")){
                outActionIdArray[i] = 2;
                continue;
            }


            if(gameActions.toString().contains("MoveWithinContainerAction")){
                    outActionIdArray[i] = 3;
                    continue;
                }

            if(gameActions.toString().contains("Put In ContainerAction")){
                outActionIdArray[i] = 4;
            }
        }

        System.out.println(Arrays.toString(outActionIdArray));
        System.out.println(Arrays.toString(playMovementCard));

        // TODO :
        // Creating an array to match with the current one
        // will determine which action is played
        if (Arrays.equals(outActionIdArray, playMovementCard)){
            return 1;
        }
        return 0;

    }

}
