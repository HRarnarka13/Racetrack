/**
 * Created by arnarkari on 20/10/15.
 *
 * @author arnarkari
 */
public class RacetrackRecursive {

    Track track;
    HistoryHash history;

    final int MAX_ITERATIONS = 30;
    final int NUMBER_OF_EPISODES = 1000000;
    final int OFF_REWARD = -5;
    final int ON_REWARD = -1;

    public RacetrackRecursive() {
        try {
            this.track = new Track(TrackReader.TrackReader("track1"));
            this.history = new HistoryHash(this.track);

            for (int i = 0; i < NUMBER_OF_EPISODES; i++) {

                Action randomAction = Actions.getRandomStartingAction();
                State beginState = new State(0, 0, track.getRandomStartingPosition());

                int round_reward = simulate(beginState, 0);
                if (i % 10001 == 1) {
                    System.out.println("Round: " + i + " reward : " + round_reward);
                }
            }

            // Get starting state ..
            // Begin simulate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int simulate (State state, int iterations) {

//        System.out.println(track.PrintPos(state.getCell()));
//        System.out.println("Racer: (" + state.getCell().getX() + "," + state.getCell().getY() + ") speed : (" +
//            state.getVelocity_Up() + "," + state.getVelocity_Right() + ")");

        if (state.getCell().getSymbol() != Track.EndPos && iterations <= MAX_ITERATIONS) {

            Action nextAction; // next action
            double randomExplore = Math.random();
            if (randomExplore < 0.1) { // 10 % of the time we explore
                nextAction = Actions.getRandomAction(); // get random action

//                System.out.println("R Action: (" + nextAction.getVelocity_up()+ "," + nextAction.getVelocity_right() + ")");

            } else {
                // Get the best action to take in the current state
                nextAction = history.getBestAction(state);

                //System.out.println("Action: (" + nextAction.getVelocity_up()+ "," + nextAction.getVelocity_right() + ")");
            }

            State nextState = track.move(state, nextAction);




            Cell nextCell = nextState.getCell();

            int r = -1; // Reward begin
            if ( nextCell.equals(state.getCell()) && nextState.isStop() ) {
                r = -5;
            }

            int R = 0;
            R += (simulate(nextState, iterations++) + r);

            Pair updatePair = new Pair(nextState, nextAction);
            history.updateReward(updatePair, R);

            return R;

        } else {
            return 0;
        }
    }
}
