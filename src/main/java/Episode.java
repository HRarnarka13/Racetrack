import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Episode {

    private int reward;
    private List<StateActionHistory> stateActionHistory;

    public Episode() {
        this.reward = 0;
        this.stateActionHistory = new ArrayList<StateActionHistory>();
    }

    public List<StateActionHistory> getStateActionHistorys() {
        return stateActionHistory;
    }

    public void setStateActionHistorys(List<StateActionHistory> stateActionHistory) {
        this.stateActionHistory = stateActionHistory;
    }

    public void addPair(StateActionHistory stateActionHistory) {
        this.stateActionHistory.add(stateActionHistory);
    }

    public int getReward() {
        return reward;
    }

    public void updateReward(int reward) {
        this.reward += reward;
    }

}
