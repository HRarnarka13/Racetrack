import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class TrackReader {

    public static TrackCell[][] TrackReader(String fileName) throws Exception {
        TrackCell track[][];

        // Open the file
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File '" + fileName + "' not found");
            e.printStackTrace();
        }

        // Check the size of the track
        StringBuilder sb = new StringBuilder();
        if (br == null) {
            throw new Exception("'Error opening file'");
        }
        String l  = br.readLine();
        if (l == null) {

        }

        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            int i = 0;
            while (line != null) {
                for (int j = 0; j < line.length(); j++) {
                    switch (line.charAt(j)) {
                        case 'o' :
                            track[i][j] = new TrackCell(-5, false, false, false);
                            break;
                        case 'x' :
                            track[i][j] = new TrackCell(-1, true, false, false);
                            break;
                        case 's' :
                            track[i][j] = new TrackCell(0, true, false, true);
                            break;
                        case 'e' :
                            track[i][j] = new TrackCell(1, false, true, false);
                            break;
                        default:
                            throw new Exception("error parsing file");
                    }
                }
                System.out.println(line.length());
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                i++;
            }
            String everything = sb.toString();
            System.out.println(everything);
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return track;
    }
}
