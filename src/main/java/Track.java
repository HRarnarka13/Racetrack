import java.io.*;
import java.util.Arrays;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Track {

    public final char OffTrack = 'o';
    public final char OnTrack  = 'x';
    public final char StartPos = 's';
    public final char EndPos   = 'e';

    private TrackCell track[][];
    private int rows;
    private int cols;

    public Track(String trackFile)  {
        parse(trackFile);
    }

    public TrackCell[][] getTrack() {
        return track;
    }

    private void parse(String trackFile) {

        // Check how large the array has to be for the track
        String [] track_rows  = trackFile.split("\n");
        rows = track_rows.length;
        cols = 0;
        for (String s : track_rows) {
            if (cols < s.length()) {
                cols = s.length();
            }
        }
        track = new TrackCell[rows][cols];

        // Fill the track
        int i = 0, j = 0;
        for(String row : track_rows) {
            j = 0;
            for(char cell : row.toCharArray()) {
                switch (cell) {
                    case OnTrack:
                        track[i][j] = new TrackCell(OnTrack, -1);
                        break;
                    case OffTrack:
                        track[i][j] = new TrackCell(OffTrack, -5);
                        break;
                    case StartPos:
                        track[i][j] = new TrackCell(StartPos, 0);
                        break;
                    case EndPos:
                        track[i][j] = new TrackCell(EndPos, 5);
                        break;
                }
                j++;
            }
            i++;
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (track[i][j] != null) {
                    result += track[i][j].toString();
                }
            }
            result += "\n";
        }
        return result;
    }
}
