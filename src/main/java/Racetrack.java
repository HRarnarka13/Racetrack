import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Racetrack {


    private Actions actions = new Actions();


    public static void main(String[] args) {
        Track track;
        try {
            track = new Track(TrackReader.TrackReader("track1"));
            System.out.println(track);

            for (int i = 0; i < 1000; i++) {

                Episode episode = new Episode();
                State currentState = new State(0,0, new TrackCell())
                while (current)

                double randomExplore = Math.random();
                if (randomExplore < 0.1) { // 10 % of the time we explore
                    // explore
                    // choose random state
                    State state = actions.getRandomState();

                } else {
                    // choose state with good reward

                }

                double randomSlide = Math.random();
                if (randomSlide < 0.25) { // Slide up 25 % of the time

                } else if (randomSlide > 0.75) { // Slide right 25 % of the time

                } else { // No slide

                }
            }
            // Add state to list


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
