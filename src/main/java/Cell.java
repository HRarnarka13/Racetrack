/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class Cell {

    private int x, y;
    private char symbol;
    private int reward;

    public Cell(int x, int y, char symbol, int reward) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.reward = reward;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
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
