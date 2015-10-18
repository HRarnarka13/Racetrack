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

    public static String TrackReader(String fileName) throws Exception {

        // Open the file
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File '" + fileName + "' not found");
            e.printStackTrace();
            return null;
        }

        // Check the size of the track
        if (br == null) {
            System.out.println("Error opening file: " + fileName);
            return null;
        }

        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = br.readLine();
            }
            br.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
