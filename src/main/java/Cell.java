/**
 * Created by arnarkari on 18/10/15.
 *
 * This class represents sa single cell on the track
 *
 * @author arnarkari
 */
public class Cell {

    private int x, y; // position of the cell
    private char symbol; // the symbol of the cell
    private int reward; // the reward of ending in this cell

    public Cell(int x, int y, char symbol, int reward) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.reward = reward;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getReward() {
        return reward;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }

    public boolean equals(Cell cell) {
        if (x == cell.getX() && y == cell.getY()) {
            return true;
        }
        return false;
    }
}
