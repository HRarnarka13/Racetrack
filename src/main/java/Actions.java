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
    public Action getRandomState() {
        int randomIndex = new Random().nextInt(actions.size());
        return actions.get(randomIndex);
    }

}
