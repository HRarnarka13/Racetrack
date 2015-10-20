/**
 * Created by arnarkari on 20/10/15.
 *
 * @author arnarkari
 */
public class Racetrack {

    Track track;
    HistoryHash history;

    final int MAX_ITERATIONS = 30;
    final int NUMBER_OF_EPISODES = 2000000;
    final int OFF_REWARD = -5;
    final int ON_REWARD = -1;

    public Racetrack() {
        try {
            this.track = new Track(TrackReader.TrackReader("track1"));
            this.history = new HistoryHash(this.track);

            int sum = 0;
            for (int i = 0; i < NUMBER_OF_EPISODES; i++) {

                State beginState = new State(0, 0, track.getRandomStartingPosition());

                int reward = simulate(beginState, 0, false);
                sum += reward;
                if (i % 10000 == 0) {
                    System.out.println("Round : " + i + " SUM : " + sum + " avg : " + sum / (double)(10000) + " reward : " + reward);
                    sum = 0;
                }
            }

            State beginState = new State(0, 0, track.getRandomStartingPosition());
            System.out.println("Reward! : " + simulate(beginState, 0, true));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Recursive function for simulating a episode on the track.
     * @param state current state
     * @param iteration iteration number
     * @param print boolean value to say if we want to print the episode
     * @return the reward of the begin state after the episode
     */
    public int simulate (State state, int iteration, boolean print) {

        if (print) {
            System.out.println(track.PrintPos(state.getCell(), 'R'));
            System.out.println("Racer: (" + state.getCell().getX() + "," + state.getCell().getY() + ") speed : (" +
                    state.getVelocity_Up() + "," + state.getVelocity_Right() + ")");

        }
        // Check if the current state is a terminal state
        if (state.getCell().getSymbol() != Track.EndPos && iteration <= MAX_ITERATIONS) {

            Action nextAction; // next action
            double randomExplore = Math.random();
            if (randomExplore < 0.1 && iteration != 0) { // 10 % of the time we explore
                nextAction = Actions.getRandomAction(); // get random action
                if (print) {
                    System.out.println("Action: (" + nextAction.getVelocity_up()+ "," + nextAction.getVelocity_right() + ")");
                }
            } else {
                // Get the best action to take in the current state
                nextAction = history.getGoodAction(state);
                if (print) {
                    System.out.println("Action: (" + nextAction.getVelocity_up()+ "," + nextAction.getVelocity_right() + ")");
                }
            }

            State nextState = track.move(state, nextAction);
            Cell nextCell = nextState.getCell();

            int r = ON_REWARD; // Reward for on track
            if ( nextCell.equals(state.getCell()) && nextState.isStop() ) {
                r = OFF_REWARD; // Reward for off track
            }

            // Slide 50 % of the time
            double randomSlide = Math.random();
            Cell slideToCell;
            if (randomSlide < 0.25) { // Slide up 25 % of the time
                slideToCell = track.getCell(nextCell.getX(), nextCell.getY() - 1);
                if (slideToCell != null) {
                    if (print)
                        System.out.println(track.PrintPos(nextState.getCell(), 'P'));

                    nextState.setCell(slideToCell);
                }
            } else if (randomSlide > 0.75) { // Slide right 25 % of the time
                slideToCell = track.getCell(nextCell.getX() + 1, nextCell.getY());
                if (slideToCell != null) {
                    if (print)
                        System.out.println(track.PrintPos(nextState.getCell(), 'L'));

                    nextState.setCell(slideToCell);
                }
            }

            // If we slide of the track we crash and reset
            if (nextState.getCell().getSymbol() == Track.OffTrack) {
                nextState = new State(0,0, state.getCell());
            }

            int R = 0;
            R += (simulate(nextState, ++iteration, print) + r);

            Pair updatePair = new Pair(state, nextAction);
            history.updateReward(updatePair, R);

            return R;

        } else {
            return 0;
        }
    }
}

