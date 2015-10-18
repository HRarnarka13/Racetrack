import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Pair {
    private State state;
    private Action action;
    private List<Integer> rewards;

    public Pair(State state, Action action) {
        this.state = state;
        this.action = action;
        this.rewards = new ArrayList<Integer>();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * Add a reward to the list of rewards
     * @param reward a new reward
     */
    public void addReward(int reward) {
        rewards.add(reward);
    }


    /**
     * The the avg of the rewards
     * @return the avg of the rewards
     */
    public double getAvgReward() {
        int sum = 0;
        for (Integer reward : rewards) {
            sum += reward;
        }
        return sum / (double) rewards.size();
    }

    public boolean equals(Pair pair) {
        if (this.state.equals(pair.getState()) && this.action.equals(pair.getAction())) {
            return true;
        }
        return false;
    }
}
