/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class TrackCell {

    private int reward;
    private boolean onTrack;
    private boolean onFinishLine;
    private boolean onStartingLine;

    public TrackCell(int reward, boolean onTrack, boolean onFinishLine, boolean onStartingLine) {
        this.reward = reward;
        this.onTrack = onTrack;
        this.onFinishLine = onFinishLine;
        this.onStartingLine = onStartingLine;
    }

    public int getReward() {
        return reward;
    }

    public boolean isOnTrack() {
        return onTrack;
    }

    public boolean isOnFinishLine() {
        return onFinishLine;
    }

    public boolean isOnStartingLine() {
        return onStartingLine;
    }
}
