/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Racetrack {

    public static void main(String[] args) {
        Track track;
        try {
            track = new Track(TrackReader.TrackReader("track1"));
            System.out.println(track);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
