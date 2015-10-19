import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Racetrack {

    public static void main(String[] args) {

        final int MAX_ITERATIONS = 30;

        Actions actions = new Actions();
        Track track;
        try {
            track = new Track(TrackReader.TrackReader("track1"));
            System.out.println(track);


            HistoryHash history = new HistoryHash(track, actions.getActions());
            long sum = 0;
            int wins = 0;
            for (int i = 0; i < 1000000; i++) {

                // <Episode>
                Episode episode = new Episode();

                State beginState;
                State currentState;
                do {
                    // Get a random starting position and action
                    Action randomAction = actions.getRandomStartingAction();

                    beginState = new State(0, 0, track.getRandomStartingPosition());

                    // Adding first pair to the episode
                    episode.addPair(new Pair(beginState, randomAction));
                    currentState = track.move(beginState, randomAction);

                } while (currentState.getCell().getY() == beginState.getCell().getY());

                int numberOfIteration = 0;
                while (currentState.getCell().getSymbol() != track.EndPos && numberOfIteration <= MAX_ITERATIONS) {
                    Action action; // our next action
                    boolean crashed = false;

                    do {
                        crashed = false;
                        double randomExplore = Math.random();
                        if (randomExplore < 0.1) { // 10 % of the time we explore
                            action = actions.getRandomAction(); // get random action
                        } else {
                            // Get the best action to take in the current state
                            action = history.getBestAction(currentState, actions);
                        }

                        State lastState = currentState;
                        currentState = track.move(currentState, action);

                        if (lastState.getCell().getSymbol() == Track.OnTrack
                                && action.getVelocity_right() != 0 && action.getVelocity_up() != 0) {
                            if (currentState.getCell().getX() == lastState.getCell().getX() &&
                                    currentState.getCell().getY() == lastState.getCell().getY()) {
                                episode.updateReward(-5);
                                crashed = true;
                                numberOfIteration++;
                                episode.addPair(new Pair(currentState, action));
                            }
                        }
                    } while (crashed);

                    // Only slide if we are moving.
                    if (currentState.getVelocity_Right() != 0 && currentState.getVelocity_Up() != 0) {
                        boolean didSlide = false;
                        double randomSlide = Math.random();
                        Cell slideCell = null;
                        if (randomSlide < 0.25) { // Slide up 25 % of the time
                            didSlide = true;
                            // this makes us slide DOWN not UP LOL..
//                            System.out.println("Slide up");
                            slideCell = track.getCell(currentState.getCell().getX(),
                                    currentState.getCell().getY() - 1);

                        } else if (randomSlide > 0.75) { // Slide right 25 % of the time
                            didSlide = true;
                            // System.out.println("Slide right");
                            slideCell = track.getCell(currentState.getCell().getX() + 1,
                                    currentState.getCell().getY());
                        }

                        // check if we slide and we slide out of the track
                        if (didSlide && (slideCell == null || slideCell.getSymbol() == track.OffTrack)) {
                            // TODO : check if we slide over the finish line

                            currentState.setVelocity_Right(0);
                            currentState.setVelocity_Up(0);
                            slideCell = currentState.getCell();
                            episode.updateReward(-5);
                            currentState.setCell(slideCell);
                        }
                    }

                    Cell currentCell = currentState.getCell();
                    if (currentCell != null) {
                        episode.updateReward(currentCell.getReward());
                    } else {
                        System.out.println("ERROR: READING SYMBOL");
                    }

                    // Add action and current state to the list
                    episode.addPair(new Pair(currentState, action));
                    numberOfIteration++;

                    if (currentCell.getSymbol() == Track.EndPos) {
                        wins++;
                    }

                    if (currentCell.getSymbol() == Track.EndPos && i % 10000 == 0) {
                        System.out.print("won in : " + numberOfIteration + " iterations, ");
                    }
                }
                // </Episode>

                // Add the reward we got for the episode to each of the states
                for (Pair pair: episode.getPairs()) {
                    history.updateReward(pair, episode.getReward());
                }
                sum += episode.getReward();
                if (i % 10000 == 1) {
                    System.out.println(" Round:" + i + " avg : " + sum / (double) i + " wins : " + wins);
                    history.Print();
                }

            }
            // Add state to list
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
