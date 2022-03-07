package Lesson_2;

/**
 * class Coordinate
 *
 * @author Melnev Dmitry
 * @version 2022
 */

public class Coordinate {
    int X;
    int Y;
    int steps;
    char who;

    public Coordinate(int X, int Y, int steps, char who) {
        this.X = X;
        this.Y = Y;
        this.steps = steps;
        this.who = who;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "X=" + X +
                ", Y=" + Y +
                ", steps=" + steps +
                ", who=" + who +
                '}';
    }
}
