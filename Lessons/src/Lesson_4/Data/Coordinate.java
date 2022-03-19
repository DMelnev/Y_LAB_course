/**
 * class Coordinate
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_4.Data;

public class Coordinate {
    private int X;
    private int Y;
    private int steps;

    public Coordinate(int X, int Y, int steps) {
        this.X = X;
        this.Y = Y;
        this.steps = steps;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "X=" + X +
                ", Y=" + Y +
                ", steps=" + steps +
                '}';
    }
}
