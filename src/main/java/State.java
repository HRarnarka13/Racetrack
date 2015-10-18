import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class State {

    private int velocity_Up;
    private int velocity_Right;
    private TrackCell cell;
    private List<Integer> rewards;

    public State(int velocity_Up, int velocity_Right, TrackCell cell) {
        this.velocity_Up = velocity_Up;
        this.velocity_Right = velocity_Right;
        this.cell = cell;
        this.rewards = new ArrayList<Integer>();
    }

    // region get set
    public int getVelocity_Up() {
        return velocity_Up;
    }

    public void setVelocity_Up(int velocity_Up) {
        this.velocity_Up = velocity_Up;
    }

    public int getVelocity_Right() {
        return velocity_Right;
    }

    public void setVelocity_Right(int velocity_Right) {
        this.velocity_Right = velocity_Right;
    }

    public TrackCell getCell() {
        return cell;
    }

    public void setCell(TrackCell cell) {
        this.cell = cell;
    }

    public ArrayList<Integer> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<Integer> rewards) {
        this.rewards = rewards;
    }
    // endregion

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

}
