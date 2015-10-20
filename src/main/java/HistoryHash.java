import java.util.*;

/**
 * Created by arnarkari on 19/10/15.
 *
 * @author arnarkari
 */
public class HistoryHash {

    HashMap<String, StateActionHistory> hashMap;
    Track track;
    Cell finishLine;

    /**
     * Construct a state and action pair for every cell in the track.
     * @param track the track
     */
    public HistoryHash(Track track) {
        this.track = track;
        this.hashMap = new HashMap<String, StateActionHistory>();
        this.finishLine = getMiddleFinishLineCell();

        int rows = track.getRows();
        int cols = track.getCols();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                for(int k = -5; k < 6; k++){
                    for(int h = -5; h < 6; h++){
                        for(Action a : Actions.getActions()){
                            State state = new State(k, h, track.getTrack()[i][j]);
                            Pair pair = new Pair(state, a);
                            StateActionHistory stateAction = new StateActionHistory(pair);
                            this.add(stateAction);
                        }
                    }
                }
            }

        }
    }

    public double distanceToFinishLine(State state) {
        return Math.sqrt(Math.pow(finishLine.getX() - state.getCell().getX(), 2)
                + Math.pow(state.getCell().getY() - finishLine.getY(), 2));
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

    /**
     * Returns the best action to take in a given state based on the best reward
     * @param state
     * @return
     */
    public Action getBestAction(State state) {

        double bestReward = Double.NEGATIVE_INFINITY;
        Action bestAction = null;

        // Create a list of potential best actions
        List<StateActionHistory> bestStateActions = new ArrayList<StateActionHistory>();

        for (Action action : Actions.getActions()) {

            StateActionHistory sah = hashMap.get(getStringKey(new Pair(state, action)));
            bestAction = sah.getPair().getAction();
            State maybe = track.move(state, bestAction); // move the card
            // Check if we move further away from the finish line
//            if (distanceToFinishLine(maybe) < distanceToFinishLine(state)) {
                bestStateActions.add(sah);
//            }
        }
//        Collections.shuffle(bestStateActions, new Random());
        bestReward = Double.NEGATIVE_INFINITY;
        int counter = 0;
        for ( StateActionHistory bsa : bestStateActions) {
            if ( bsa.getAvgReward() >= bestReward ) {
                bestReward = bsa.getAvgReward();
                bestAction = bsa.getPair().getAction();

            }
        }

        if (bestAction == null) {
            System.out.println("ALERT NO BEST ACTION TO TAKE");
        }

        // System.out.println(bestReward +  " (" + bestAction.getVelocity_up() + "," + bestAction.getVelocity_right() + ")");
        return bestAction;
    }

    public void add(StateActionHistory stateActionHistory) {
        hashMap.put(getStringKey(stateActionHistory.getPair()), stateActionHistory);
    }

    public void updateReward(Pair pair, int reward) {
        hashMap.get(getStringKey(pair)).addReward(reward);
    }

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

    public void Print() {
        System.out.println(hashMap.size());
        int numberOfStates = 0;
        int numberOfActive = 0;
        for ( StateActionHistory s : hashMap.values()) {
            if (s.getPair().getState().getCell().getSymbol() == Track.OnTrack) {
                numberOfStates++;
                if ( s.getRewards().size() > 0) {
                    numberOfActive++;
                }
                // System.out.println("size of : " + getStringKey(s.getPair()) + " " + s.getRewards().size());
            }
        }
        System.out.println("numberOfStates = " + numberOfStates);
        System.out.println("numberOfActive = " + numberOfActive);
        System.out.println(numberOfActive / (double) numberOfStates );
    }
}
