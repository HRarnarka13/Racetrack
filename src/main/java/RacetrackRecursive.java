import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by arnarkari on 20/10/15.
 *
 * @author arnarkari
 */
public class RacetrackRecursive {

    Track track;
    HistoryHash history;

    final int MAX_ITERATIONS = 30;
    final int NUMBER_OF_EPISODES = 2000000;
    final int OFF_REWARD = -5;
    final int ON_REWARD = -1;
    private double EPSILON = 0.1;
    ArrayList<Integer> means = new ArrayList<Integer>();
    public RacetrackRecursive() {
        try {
            this.track = new Track(TrackReader.TrackReader("track1"));
            this.history = new HistoryHash(this.track);
            int sum = 0;
            ArrayList<Cell>startingPositions = track.getAllStartingStates();
            for (int i = 0; i < NUMBER_OF_EPISODES; i++) {
//                State beginState = new State(0, 0, track.getRandomStartingPosition());
                State beginState = new State(0, 0, startingPositions.get(i % startingPositions.size()));

                int reward = simulate(beginState, 0, false);
                means.add(reward);

//                sum += reward;
                if (i % 1000 == 0) {
                    Collections.sort(means);
                    int nines = Collections.frequency(means, -9);
                    int tens = Collections.frequency(means, -10);
                    int eights = Collections.frequency(means, -8);
                    int elevens = Collections.frequency(means, -11);
                    int twelves = Collections.frequency(means, -12);
                    int sumOfStuff = nines + tens + eights + elevens + twelves;
                    System.out.println( "Round : " + i
                                        + " Mean of list " + means.get(means.size() / 2)
                                        + " Lowest in list : " + means.get(means.size() - 1)
                                        + " Lower than 11's " + sumOfStuff );
//                    System.out.println("Round : " + i + " SUM : " + sum + " avg : " + sum / (double)(1000) + " reward : " + reward);
                    sum = 0;
                    means.clear();
                }
            }

            State beginState = new State(0, 0, track.getRandomStartingPosition());
            simulate(beginState, 0, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int simulate (State state, int iterations, boolean print) {
        if(iterations < 200000){
            EPSILON = 0.1;
        } else if (iterations > 300000 && iterations < 500000){
            EPSILON = 0.04;
        } else{
            EPSILON = 0.01;
        }

        if (state.getCell().getSymbol() != Track.EndPos && iterations <= MAX_ITERATIONS) {

            Action nextAction; // next action
            double randomExplore = Math.random();
            if (randomExplore < EPSILON && iterations != 0) { // 10 % of the time we explore
                nextAction = Actions.getRandomAction(); // get random action

                if (print) {
//                    System.out.println("R Action: (" + nextAction.getVelocity_up()+ "," + nextAction.getVelocity_right() + ")");
                }
            } else {
                // Get the best action to take in the current state
                nextAction = history.getBestAction(state);

                if (print) {
//                    System.out.println("Action: (" + nextAction.getVelocity_up()+ "," + nextAction.getVelocity_right() + ")");
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
            Cell slideToCell = null;
            if (randomSlide < 0.25) { // Slide up 25 % of the time
                slideToCell = track.getCell(nextCell.getX(), nextCell.getY() - 1);
                if (slideToCell != null) {
                    nextState.setCell(slideToCell);
                }
            } else if (randomSlide > 0.75) { // Slide right 25 % of the time
                slideToCell = track.getCell(nextCell.getX() + 1, nextCell.getY());
                if (slideToCell != null) {
                    nextState.setCell(slideToCell);

                }
            }

            // If we slide of the track we crash and reset
            if (nextState.getCell().getSymbol() == Track.OffTrack) {
                nextState = new State(0,0, state.getCell());
            }

            int R = 0;
            R += (simulate(nextState, ++iterations, print) + r);

            Pair updatePair = new Pair(state, nextAction);
            history.updateReward(updatePair, R);
            if (print) {
                System.out.println("R : " + R);
            }

            if (print) {
                System.out.println("R : " + R);
                // System.out.println(track.PrintPos(state.getCell()));
                System.out.println("Racer: (" + state.getCell().getX() + "," + state.getCell().getY() + ") speed : (" +
                        state.getVelocity_Up() + "," + state.getVelocity_Right() + ")");
                System.out.println("Action: (" + nextAction.getVelocity_up()+ "," + nextAction.getVelocity_right() + ")");
            }

            return R;

        } else {
            return 0;
        }
    }
}
