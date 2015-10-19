import java.util.*;

/**
 * Created by arnarkari on 19/10/15.
 *
 * @author arnarkari
 */
public class History {

    List<StateActionHistory> stateActionHistories;
    Track track;

    /**
     * Construct a state and action pair for every cell in the track.
     * @param track the track
     * @param actions list of actions
     */
    public History(Track track, List<Action> actions) {
        this.track = track;
        stateActionHistories = new ArrayList<StateActionHistory>();
        int rows = track.getRows();
        int cols = track.getCols();
        // for each cell(x,y)
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                //
                for(int k = -5; k < 6; k++){
                    for(int h = -5; h < 6; h++){
                        for(Action a : actions){
                            State state = new State(k, h, track.getTrack()[i][j]);
                            Pair pair = new Pair(state, a);
                            StateActionHistory stateAction = new StateActionHistory(pair);
                            stateActionHistories.add(stateAction);
                        }
                    }
                }
            }

        }
    }

    public double distanceToFinishLine(State state) {
        Cell finishLineCell = getMiddleFinishLineCell();
        return Math.sqrt(Math.pow(finishLineCell.getX() - state.getCell().getX(), 2)
                + Math.pow(state.getCell().getY() - finishLineCell.getY(),2));
    }

    public Cell getMiddleFinishLineCell() {
        List<Cell> endLine = new ArrayList<Cell>();
        for (int i = 0; i < track.getRows(); i++) {
            if (track.getTrack()[i][track.getCols() - 1].getSymbol() == track.EndPos) {
                endLine.add(track.getTrack()[i][track.getCols() - 3]);
            }
        }
        int middle = endLine.size() / 2;
        return endLine.get(middle);
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
            System.out.println("Error update pair reward");
            System.exit(1);
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

        // Get a list of potential best actions
        List<StateActionHistory> bestStateActions = new ArrayList<StateActionHistory>();
        for ( StateActionHistory sah : stateActionHistories ) {

            if (sah.getPair().getState().equals(state)) {

                if ( sah.getAvgReward() >= bestReward) {

                    bestReward = sah.getAvgReward();
                    bestAction = sah.getPair().getAction();
                    // play and check
                    State maybe = track.move(state, bestAction);
                    if (distanceToFinishLine(maybe) <= distanceToFinishLine(state)) {
                        bestStateActions.add(sah);
                    }
                }
            }
        }
        // System.out.println("Size of best actions list " + bestStateActions.size());
        // Get the best of the best action
        Collections.shuffle(bestStateActions, new Random());
        bestReward = Double.NEGATIVE_INFINITY;
        for ( StateActionHistory bsa : bestStateActions) {
            if ( bsa.getAvgReward() >= bestReward ) {
                bestReward = bsa.getAvgReward();
                bestAction = bsa.getPair().getAction();
            }
        }

        if (bestAction == null) {
            System.out.println("ALERT NO BEST ACTION TO TAKE");
        }
        return bestAction;
    }

}
