import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Track {

    public static final char OffTrack = 'o';
    public static final char OnTrack  = 'x';
    public static final char StartPos = 's';
    public static final char EndPos   = 'e';

    private Cell track[][];
    private int rows;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    private int cols;

    public Track(String trackFile)  {
        parse(trackFile);
    }

    public Cell[][] getTrack() {
        return track;
    }

    /**
     * Get a cell on the track by its position.
     * @param x
     * @param y
     * @return a cell, or null if the cell is not on the track
     */
    public Cell getCell(int x, int y) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell c = track[i][j];
                if (c != null && c.getX() == x && c.getY() == y) {
                    return c;
                }
            }
        }
        return null;
    }



    /**
     * Moves the car to a new state on the track. If the action will crash the car (move it off the track)
     * we will reset the car velocity and return the previous state of the car.
     * @param state     The current state of the car
     * @param action    Action which the car will perform next
     * @return          The next state of the car after the action has been performed. If the action will crash the
     *                  car off the track then the velocity is reset and the car stays at its previous position.
     */
    public State move(State state, Action action) {
        // Get the new velocity up of the car
        int vel_up = state.getVelocity_Up() + action.getVelocity_up();
        if (vel_up >= 5) {
            vel_up = 5;
        } else if (vel_up <= -5) {
            vel_up = -5;
        }

        // Get the new velocity to right of the car
        int vel_right = state.getVelocity_Right() + action.getVelocity_right();
        if (vel_right >= 5) {
            vel_right = 5;
        } else if (vel_right <= -5) {
            vel_right = -5;
        }

        // Get the current position of the car
        Cell currCell = state.getCell();

        int newX = currCell.getX() + vel_right;
        int newY = currCell.getY() - vel_up;

        try {
            // Get the position of the car after the action
            Cell nextCell = track[newY][newX];
            // System.out.println("Next cell coordinates : " + nextCell.getX() + " , " + nextCell.getY());
            // Check if the car is off the track
            if (nextCell.getSymbol() == OffTrack) {
                return new State(0, 0, state.getCell());
            }
            State newState = new State(vel_up, vel_right, nextCell);
            return newState;
        } catch (ArrayIndexOutOfBoundsException e) {
             // The car has crashed
            return new State(0, 0, state.getCell());
        }
    }

    /**
     * Parse the track file string and initialize the track (array)
     * @param trackFile
     */
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
        track = new Cell[rows][cols];

        // Fill the track
        int i = 0, j = 0;
        for(String row : track_rows) {
            j = 0;
            for(char cell : row.toCharArray()) {
                switch (cell) {
                    case OnTrack:
                        track[i][j] = new Cell(j, i, OnTrack, -1);
                        break;
                    case OffTrack:
                        track[i][j] = new Cell(j, i, OffTrack, -5);
                        break;
                    case StartPos:
                        track[i][j] = new Cell(j, i, StartPos, -1);
                        break;
                    case EndPos:
                        track[i][j] = new Cell(j, i, EndPos, 0);
                        break;
                }
                j++;
            }
            i++;
        }
    }

    public Cell getRandomStartingPosition() {
        List<Cell> startingPositions = new ArrayList<Cell>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (track[i][j] != null && track[i][j].getSymbol() == StartPos) {
                    startingPositions.add(track[i][j]);
                }
            }
        }

        int randomIndex = new Random().nextInt(startingPositions.size());
        return startingPositions.get(randomIndex);
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
