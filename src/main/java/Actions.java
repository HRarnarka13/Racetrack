import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Actions {

    private List<Action> actions;

    public Actions() {
        // Preset the available actions
        this.actions = new ArrayList<Action>() {{
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
    }

    public List<Action> getActions() {
        return actions;
    }

    /**
     * Get a state by the velocities
     * @param vel_up
     * @param vel_right
     * @return
     */
    public Action getState(int vel_up, int vel_right) {
        for( Action a : actions) {
            if (a.getVelocity_up() == vel_up && a.getVelocity_right() == vel_right) {
                return a;
            }
        }
        return null;
    }

    /**
     * Get a random state
     * @return a random state
     */
    public Action getRandomAction() {
        int randomIndex = new Random().nextInt(actions.size());
        return actions.get(randomIndex);
    }

    public Action getRandomStartingAction() {
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
