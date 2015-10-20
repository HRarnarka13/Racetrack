import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 19/10/15.
 *
 * @author arnarkari
 */
public class StateActionHistory {

    private Pair pair;
    private int number_of_rewards;
    private double weighted_average;
    private List<Integer> rewards;

    public StateActionHistory(Pair pair) {
        this.pair = pair;
        rewards = new ArrayList<Integer>();
        number_of_rewards = 0;
    }

    /**
     * Add a reward to the list of rewards
     *
     * @param reward a new reward
     */
    public void addReward(int reward) {
        number_of_rewards++;
        if (number_of_rewards == 1) {
            weighted_average = (double) reward;
        }
        weighted_average = weighted_average + (0.6 * (reward - weighted_average));
    }

    /**
     * The the avg of the rewards
     *
     * @return the avg of the rewards
     */
    public double getAvgReward() {
        return weighted_average;
    }

    public boolean equals(Pair pair) {
        if (this.pair.equals(pair) && this.pair.equals(pair)) {
            return true;
        }
        return false;
    }

    public Pair getPair() {
        return pair;
    }

    public void setPair(Pair pair) {
        this.pair = pair;
    }

    public List<Integer> getRewards() {
        return rewards;
    }

    public void setRewards(List<Integer> rewards) {
        this.rewards = rewards;
    }
}
