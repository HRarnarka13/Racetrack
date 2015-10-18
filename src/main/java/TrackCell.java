/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class TrackCell {

    private char symbol;
    private int reward;

    public TrackCell(char simpol, int reward) {
        this.symbol = simpol;
        this.reward = reward;
    }

    public char getSimpol() {
        return symbol;
    }

    public void setSimpol(char simpol) {
        this.symbol = simpol;
    }

    public int getReward() {
        return reward;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
