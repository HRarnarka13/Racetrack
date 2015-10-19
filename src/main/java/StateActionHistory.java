import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 19/10/15.
 *
 * @author arnarkari
 */
public class StateActionHistory {

    private Pair pair;
    private List<Integer> rewards;

    public StateActionHistory(Pair pair) {
        this.pair = pair;
        rewards = new ArrayList<Integer>();
    }

    /**
     * Add a reward to the list of rewards
     *
     * @param reward a new reward
     */
    public void addReward(int reward) {
        rewards.add(reward);
    }

    /**
     * The the avg of the rewards
     *
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
