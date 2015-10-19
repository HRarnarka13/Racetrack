import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Episode {

    private int reward;
    private List<Pair> pairs;

    public Episode() {
        this.reward = 0;
        this.pairs = new ArrayList<Pair>();
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair> Pairs) {
        this.pairs = pairs;
    }

    public void addPair(Pair Pair) {
        this.pairs.add(Pair);
    }

    public int getReward() {
        return reward;
    }

    public void updateReward(int reward) {
        this.reward += reward;
    }

}
