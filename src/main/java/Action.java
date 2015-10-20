/**
 * Created by arnarkari on 18/10/15.
 *
 * This class represents a single action
 *
 * @author arnarkari
 */
public class Action {

    private int velocity_up;
    private int velocity_right;

    public Action(int velocity_up, int velocity_right) {
        this.velocity_up = velocity_up;
        this.velocity_right = velocity_right;
    }

    public int getVelocity_up() {
        return velocity_up;
    }

    public int getVelocity_right() {
        return velocity_right;
    }

    public boolean equals(Action action) {
        if (velocity_up == action.getVelocity_up() && velocity_right == action.getVelocity_right()) {
            return true;
        }
        return false;
    }
}
