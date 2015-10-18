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
        this.pairs = new ArrayList<Pair>();
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    public void addPair(Pair pair) {
        pairs.add(pair);
    }
}
