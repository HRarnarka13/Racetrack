import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarkari on 18/10/15.
 *
 * @author arnarkari
 */
public class State {

    private int velocity_Up;
    private int velocity_Right;
    private Cell cell;

    public State(int velocity_Up, int velocity_Right, Cell cell) {
        this.velocity_Up = velocity_Up;
        this.velocity_Right = velocity_Right;
        this.cell = cell;
    }

    // region get set
    public int getVelocity_Up() {
        return velocity_Up;
    }

    public void setVelocity_Up(int velocity_Up) {
        this.velocity_Up = velocity_Up;
    }

    public int getVelocity_Right() {
        return velocity_Right;
    }

    public void setVelocity_Right(int velocity_Right) {
        this.velocity_Right = velocity_Right;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
    // endregion

    public boolean isStop() {
        return velocity_Up == 0 && velocity_Right == 0;
    }

    public boolean equals(State state) {
        if (velocity_Up == state.getVelocity_Up() && velocity_Right == state.getVelocity_Right()
                && cell.equals(state.getCell())) {
            return true;
        }
        return false;
    }
}
