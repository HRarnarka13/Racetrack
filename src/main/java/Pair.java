import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 18/10/15.
 *
 *
 *
 * @author arnarkari
 */
public class Pair {
    private State state;
    private Action action;

    public Pair(State state, Action action) {
        this.state = state;
        this.action = action;
    }

    public State getState() {
        return state;
    }

    public Action getAction() {
        return action;
    }

    public boolean equals(Pair pair) {
        if (this.state.equals(pair.getState()) && this.action.equals(pair.getAction())) {
            return true;
        }
        return false;
    }
}
