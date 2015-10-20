import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public final class Actions {

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

    public Actions() {
        // Preset the available actions
    }

    public static List<Action> getActions() {
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

    public static Action getRandomStartingAction() {
        List<Action> startingActions = new ArrayList<Action>();
        for ( Action action : actions ) {
            if (action.getVelocity_up() != -1) {
                // Don't move back in the starting state
                startingActions.add(action);
            }
        }
        int randomIndex = new Random().nextInt(startingActions.size());
        return startingActions.get(randomIndex);
    }
}
