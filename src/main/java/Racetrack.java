import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Racetrack {

    public static void main(String[] args) {

        final int MAX_ITERATIONS = 70;

        Actions actions = new Actions();
        Track track;
        try {
            track = new Track(TrackReader.TrackReader("track1"));
            System.out.println(track);


            History history = new History(track, actions.getActions());

            for (int i = 0; i < 1000; i++) {
                // <Episode>
                Episode episode = new Episode();

                // Get a random starting position and action
                Action randomAction = actions.getRandomStartingAction();

                State currentState = new State(randomAction.getVelocity_up(), randomAction.getVelocity_right(), track.getRandomStartingPosition());

                System.out.println("Starting pos : " + currentState.getCell().getX() + ", "
                        + currentState.getCell().getY());

                System.out.println("ACTION : UP :" + randomAction.getVelocity_up() + " RIGHT : "
                        + randomAction.getVelocity_right() );

                // Adding first pair to the episode
                episode.addPair(new Pair(currentState, randomAction));
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
                    System.out.println("Curr itterations" + numberOfIteration);
                    System.out.println("Curr SYMBOL: " + currentState.getCell().getX() + ", "
                            + currentState.getCell().getY() + " : " + currentState.getCell().getSymbol());

                    Action action; // our next action

                    double randomExplore = Math.random();
                    if (randomExplore < 0.1) { // 10 % of the time we explore
                        action = actions.getRandomAction(); // get random action
                        System.out.println("explore");
                    } else {
                        // choose state with good reward
                        // Bet the best action to take in the current state
                        action = history.getBestAction(currentState);
                        System.out.println("pick best");

                    }

                    currentState = track.move(currentState, action);

                    // Only slide if we are moving.
                    if (currentState.getVelocity_Right() != 0 && currentState.getVelocity_Up() != 0) {
                        double randomSlide = Math.random();
                        if (randomSlide < 0.25) { // Slide up 25 % of the time
//                            this makes us slide DOWN not UP LOL..
                            System.out.println("Slide up");
                            Cell slideCell = track.getCell(currentState.getCell().getX(),
                                    currentState.getCell().getY() - 1);
                            if (slideCell == null || slideCell.getSymbol() == track.OffTrack) {
                                // TODO : check if we slide over the finish line
                                // TODO : check if we slide out of the track
                            }
                            currentState.setCell(slideCell);

                        } else if (randomSlide > 0.75) { // Slide right 25 % of the time
                            System.out.println("Slide right");
                            Cell slideCell = track.getCell(currentState.getCell().getX() + 1,
                                    currentState.getCell().getY());
                            if (slideCell == null || slideCell.getSymbol() == track.OffTrack) {
                                // TODO : check if we slide over the finish line
                                // TODO : check if we slide out of the track
                            }

                            currentState.setCell(slideCell);
                        }
                    }

                    // Add action and current state to the list
                    episode.addPair(new Pair(currentState, action));
                    numberOfIteration++;
                }
                // </Episode>

                for (Pair pair : episode.getPairs()) {
                    // TODO : calculate reward for the episode. i.e the time of the race

                    // TODO: add the reward to each pair if the pair is new create it...
                }
            }
            // Add state to list
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
