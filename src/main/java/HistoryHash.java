import java.util.*;

/**
 * Created by arnarkari on 19/10/15.
 *
 * @author arnarkari
 */
public class HistoryHash {

    HashMap<String, StateAction> hashMap;
    Track track;

    /**
     * Construct a state and action pair for every cell in the track.
     * @param track the track
     */
    public HistoryHash(Track track) {
        this.track = track;
        this.hashMap = new HashMap<String, StateAction>();

        int rows = track.getRows();
        int cols = track.getCols();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                for(int k = -5; k < 6; k++){
                    for(int h = -5; h < 6; h++){
                        for(Action a : Actions.getActions()){
                            State state = new State(k, h, track.getTrack()[i][j]);
                            Pair pair = new Pair(state, a);
                            StateAction stateAction = new StateAction(pair);
                            this.add(stateAction);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param state
     * @return
     */
    public Action getGoodAction(State state) {
        double bestReward = Double.NEGATIVE_INFINITY;
        Action bestAction = null;

        for (Action action : Actions.getActions()) {
            StateAction sah = hashMap.get(getStringKey(new Pair(state, action)));
            if ( sah.getAvgReward() >= bestReward ) {
                bestReward = sah.getAvgReward();
                bestAction = sah.getPair().getAction();
            }
        }

        return bestAction;
    }


    /**
     * Add a new state action
     * @param stateAction
     */
    public void add(StateAction stateAction) {
        hashMap.put(getStringKey(stateAction.getPair()), stateAction);
    }

    /**
     * Update the reward of a given pair
     * @param pair the pair to update the reward
     * @param reward the actual reward
     */
    public void updateReward(Pair pair, int reward) {
        hashMap.get(getStringKey(pair)).addReward(reward);
    }


    /**
     * Gets a formatted string for a given pair which acts as the key to the hash-map.
     * @param pair
     * @return formatted string
     */
    public String getStringKey(Pair pair) {
        String stringKey = "";
        stringKey += (pair.getState().getCell().getX() + "*");
        stringKey += (pair.getState().getCell().getY() + "*");
        stringKey += (pair.getState().getVelocity_Up() + "*");
        stringKey += (pair.getState().getVelocity_Right() + "*");
        stringKey += (pair.getAction().getVelocity_up() + "*");
        stringKey += (pair.getAction().getVelocity_right());
        return stringKey;
    }
}
