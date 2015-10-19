import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 19/10/15.
 *
 * @author arnarkari
 */
public class History {

    List<StateActionHistory> stateActionHistories;

    public History() {
        this.stateActionHistories = new ArrayList<StateActionHistory>();
    }

    public int getIndex (Pair pair) {

        for (int i = 0; i < stateActionHistories.size(); i++) {
            if (stateActionHistories.get(i).getPair().equals(pair)) {
                return i;
            }
        }
        return -1;
    }

    public void update(Pair pair, int reward) {
        int index = getIndex(pair);
        if (index == -1) {
            stateActionHistories.add(new StateActionHistory(pair));
        } else {
            stateActionHistories.get(index).addReward(reward);
        }
    }

    public double getReward(Pair pair) {
        int index = getIndex(pair);
        if (index != -1) {
            return stateActionHistories.get(index).getAvgReward();
        }
        return -1; // TODO : what to return if the stateActionHistory is unseen ?
    }

}
