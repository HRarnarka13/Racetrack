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


            History history = new History(track, actions.getActions());

            for (int i = 0; i < 1000; i++) {
                // <Episode>
                Episode episode = new Episode();

                State beginState;
                State currentState;
                do {
                    // Get a random starting position and action
                    Action randomAction = actions.getRandomStartingAction();

                    beginState = new State(0, 0, track.getRandomStartingPosition());

//                    System.out.println("State : " + beginState.getCell().getSymbol() + " : (" +
//                            beginState.getCell().getX() + "," + beginState.getCell().getY() + ") velocity : (" +
//                            beginState.getVelocity_Up() + "," + beginState.getVelocity_Right() + ")");
//
//                    System.out.println("Action : (" + randomAction.getVelocity_up() +
//                            "," + randomAction.getVelocity_right() + ")");

                    // Adding first pair to the episode
                    episode.addPair(new Pair(beginState, randomAction));
                    if (beginState.getCell().getSymbol() == Track.OffTrack) {
//                        System.out.println("We are off the track before move!");
                    }
                    currentState = track.move(beginState, randomAction);

                } while (currentState.getCell().getY() == beginState.getCell().getY());
//                System.out.println("LETS GO THIS ");
//                System.out.println("State : " + currentState.getCell().getSymbol() + " : (" +
//                        currentState.getCell().getX() + "," + currentState.getCell().getY() + ") velocity : (" +
//                        currentState.getVelocity_Up() + ","  + currentState.getVelocity_Right() + ")");

//                We are not supposed to actually move the car in the start. Just give it a state.
//
//                // Get the next position of the car
//                int nextX = currentState.getCell().getX() + randomAction.getVelocity_right();
//                int nextY = currentState.getCell().getY() - randomAction.getVelocity_up();
//
//                System.out.println("nextX = " + nextX);
//                System.out.println("nextY = " + nextY);
//
//                Cell nextCell = track.getCell(nextX, nextY);
//                if (nextCell == null) {
//                    System.out.println("NEXT CELL IS NULL");
//                }
//                currentState = new State(randomAction.getVelocity_up(), randomAction.getVelocity_right(), nextCell);
//
//                System.out.println("FIRST MOVE SYMBOL: " + currentState.getCell().getX() + ", "
//                        + currentState.getCell().getY() + " : " + currentState.getCell().getSymbol());

                int numberOfIteration = 0;
                while (currentState.getCell().getSymbol() != track.EndPos && numberOfIteration <= MAX_ITERATIONS) {
                    // System.out.println("Current Iterations" + numberOfIteration);

                    // System.out.println("Curr SYMBOL: " + currentState.getCell().getX() + ", "
                    //        + currentState.getCell().getY() + " : " + currentState.getCell().getSymbol());

                    Action action; // our next action

                    double randomExplore = Math.random();
                    if (randomExplore < 0.1) { // 10 % of the time we explore
                        action = actions.getRandomAction(); // get random action
                        // System.out.println("explore");
                    } else {
                        // choose state with good reward
                        // Bet the best action to take in the current state
                        action = history.getBestAction(currentState);

                    }
//                    System.out.println("State : " + currentState.getCell().getSymbol() + " : (" +
//                    currentState.getCell().getX() + "," + currentState.getCell().getY() + ") velocity : (" +
//                    currentState.getVelocity_Up() + ","  + currentState.getVelocity_Right() + ")");
//                    System.out.println("Action : (" + action.getVelocity_up()
//                            + "," + action.getVelocity_right() + ")");

                    if (currentState.getCell().getSymbol() == Track.OffTrack) {
                        //System.out.println("We are off the track before move2!");
                    }
                    State lastState = currentState;
                    currentState = track.move(currentState, action);
                    if (currentState.getCell().getSymbol() == Track.OffTrack) {
                        //System.out.println("We are off the track");
                    }

                    if (lastState.getCell().getSymbol() == Track.OnTrack
                            && action.getVelocity_right() != 0 && action.getVelocity_up() != 0) {
                        if (currentState.getCell().getX() == lastState.getCell().getX() &&
                                currentState.getCell().getY() == lastState.getCell().getY()) {
                            episode.updateReward(-5);
                        }
                    }


                    // Only slide if we are moving.
                    if (currentState.getVelocity_Right() != 0 && currentState.getVelocity_Up() != 0) {
                        double randomSlide = Math.random();
                        if (randomSlide < 0.25) { // Slide up 25 % of the time
                            // this makes us slide DOWN not UP LOL..
//                            System.out.println("Slide up");
                            Cell slideCell = track.getCell(currentState.getCell().getX(),
                                    currentState.getCell().getY() - 1);
                            if (slideCell == null || slideCell.getSymbol() == track.OffTrack) {
                                // TODO : check if we slide over the finish line
                                // TODO : check if we slide out of the track
                                currentState.setVelocity_Right(0);
                                currentState.setVelocity_Up(0);
                                slideCell = currentState.getCell();
                                episode.updateReward(-5);
                                // System.out.println("wee slided off the track!");
//                                System.out.println("sliding Curr SYMBOL: " + currentState.getCell().getX() + ", "
//                                        + currentState.getCell().getY() + " : " + currentState.getCell().getSymbol());
                            }
                            currentState.setCell(slideCell);

                        } else if (randomSlide > 0.75) { // Slide right 25 % of the time
                            // System.out.println("Slide right");
                            Cell slideCell = track.getCell(currentState.getCell().getX() + 1,
                                    currentState.getCell().getY());

                            if (slideCell == null || slideCell.getSymbol() == track.OffTrack) {
                                // TODO : check if we slide over the finish line
                                // TODO : check if we slide out of the track

                                currentState.setVelocity_Right(0);
                                currentState.setVelocity_Up(0);
                                slideCell = currentState.getCell();
                                episode.updateReward(-5);
                            }

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
                        System.out.print("won in : " + numberOfIteration + " iterations, ");
                    }
                }
                // </Episode>

                // Add the reward we got for the episode to each of the states
                for (Pair pair: episode.getPairs()) {
                    history.update(pair, episode.getReward());
                }
                System.out.println(" Round:" + i + " reward : " + episode.getReward());

            }
            // Add state to list
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
