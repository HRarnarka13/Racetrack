import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 19/10/15.
 *
 * @author arnarkari
 */
public class StateAction {

    private Pair pair;
    private int number_of_rewards;
    private double weighted_average;

    public StateAction(Pair pair) {
        this.pair = pair;
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
        weighted_average = weighted_average + (0.1  * (reward - weighted_average));
    }

    /**
     * The the avg of the rewards
     *
     * @return the avg of the rewards
     */
    public double getAvgReward() {
        return weighted_average;
    }

    public Pair getPair() {
        return pair;
    }

}
