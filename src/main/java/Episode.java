import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Episode {

    private int reward;
    private List<StateActionHistory> stateActionHistories;

    public Episode() {
        this.reward = 0;
        this.stateActionHistories = new ArrayList<StateActionHistory>();
    }

    public List<StateActionHistory> getStateActionHistories() {
        return stateActionHistories;
    }

    public void setStateActionHistories(List<StateActionHistory> stateActionHistory) {
        this.stateActionHistories = stateActionHistories;
    }

    public void addPair(StateActionHistory stateActionHistory) {
        this.stateActionHistories.add(stateActionHistory);
    }

    public int getReward() {
        return reward;
    }

    public void updateReward(int reward) {
        this.reward += reward;
    }

}
