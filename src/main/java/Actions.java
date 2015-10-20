import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Actions {

    /**
     * List of available actions
     */
    private static List<Action> actions = new ArrayList<Action>() {{
        add(new Action(0, 0));
        add(new Action(0, 1));
        add(new Action(0, -1));
        add(new Action(1, 0));
        add(new Action(1, 1));
        add(new Action(1, -1));
        add(new Action(-1, 0));
        add(new Action(-1, 1));
        add(new Action(-1, -1));
    }};


    /**
     * Returns a list of available actions in a random order.
     * @return the list of available actions in a random order.
     */
    public static List<Action> getActions() {
        /**
         *  Here we shuffle the list so that we don't always go through the action list in the same order.
         *  This is particularly good if we are choosing the best action to take in a given state.
         */
        Collections.shuffle(actions, new Random());
        return actions;
    }

    /**
     * Get a random state in the list of states
     * @return a random state
     */
    public static Action getRandomAction() {
        int randomIndex = new Random().nextInt(actions.size());
        return actions.get(randomIndex);
    }

}
