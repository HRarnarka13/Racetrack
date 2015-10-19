import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Created by arnarkari on 19/10/15.
 *
 * @author arnarkari
 */
public class History {

    List<StateActionHistory> stateActionHistories;

    /**
     * Construct a state and action pair for every cell in the track.
     * @param track the track
     * @param actions list of actions
     */
    public History(Track track, List<Action> actions){
        int rows = track.getRows();
        int cols = track.getCols();
        // for each cell(x,y)
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                //
                if(track.getCell(i, j ).getSymbol() == track.OnTrack || track.getCell(i, j).getSymbol() == track.StartPos){
                    for(int k = -5; k < 6; k++){
                        for(int h = -5; h < 6; h++){
                            for(Action a : actions){
                                State state = new State(k, h, track.getCell(i, j));
                                Pair pair = new Pair(state, a);
                                StateActionHistory stateAction = new StateActionHistory(pair);
                                stateActionHistories.add(stateAction);
                            }
                        }
                    }

                }
            }

        }
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


    /**
     * Returns the best action to take in a given state based on the best reward
     * @param state
     * @return
     */
    public Action getBestAction(State state) {

        double bestReward = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        for ( StateActionHistory sah : stateActionHistories ) {
            if (sah.getPair().getState().equals(state)) {
                if ( sah.getAvgReward() > bestReward ) {
                    bestReward = sah.getAvgReward();
                    bestAction = sah.getPair().getAction();
                }
            }
        }
        if (bestAction == null) {
            System.out.println(state);
        }
        return bestAction;
    }

}
