import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Racetrack {

    public static void main(String[] args) {
        Actions actions = new Actions();
        Track track;
        try {
            track = new Track(TrackReader.TrackReader("track1"));
            System.out.println(track);


            History history = new History();

            for (int i = 0; i < 1000; i++) {
                // <Episode>
                Episode episode = new Episode();

                // Get a random starting position AND ACTION
                State currentState = new State(0,0, track.getRandomStartingPosition());
                while (currentState.getCell().getSymbol() != track.EndPos) {

                    // TODO: remove random here!
                    Action action = actions.getRandomAction(); // our next action

                    double randomExplore = Math.random();
                    if (randomExplore < 0.1) { // 10 % of the time we explore
                        action = actions.getRandomAction(); // get random action
                    } else {
                        // choose state with good reward

                        for (Action a : actions.getActions()) {
                            // TODO: calculate best action
                        }
                    }

                    double randomSlide = Math.random();
                    if (randomSlide < 0.25) { // Slide up 25 % of the time

                        Cell slideCell = track.getCell(currentState.getCell().getX(),
                                                        currentState.getCell().getY() + 1);
                        if (slideCell == null || slideCell.getSymbol() == track.OffTrack) {
                            // TODO : check if we slide over the finish line
                            // TODO : check if we slide out of the track
                        }
                        currentState.setCell(slideCell);

                    } else if (randomSlide > 0.75) { // Slide right 25 % of the time

                        Cell slideCell = track.getCell(currentState.getCell().getX() + 1,
                                                        currentState.getCell().getY());
                        if (slideCell == null || slideCell.getSymbol() == track.OffTrack) {
                            // TODO : check if we slide over the finish line
                            // TODO : check if we slide out of the track
                        }

                        currentState.setCell(slideCell);
                    }

                    // Add action and current state to the list
                    episode.addPair(new Pair(currentState, action));

                    // TODO: update currentState i.e. move the car
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
