/**
 * class Game2
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_2;

public class Game2 extends Game {


    @Override
    protected void enterMapSize() {
        do {
            System.out.println("Введите размер поля (от 1 до 99)");
            SIZE = input.nextInt();
        } while ((SIZE < 1) || (SIZE > 99));
        do {
            System.out.println("Введите количество победных точек (от 1 до " + SIZE + ")");
            SET = input.nextInt();
        } while ((SET < 1) || (SET > SIZE));
        map = new char[SIZE][SIZE];
    }

    @Override
    protected boolean checkWin(char point, char[][] temp) {

        return checkWinAndBlock(point, SET, true);
    }

    @Override
    protected void moveAI() {
        do {
            aiX = rnd.nextInt(SIZE);
            aiY = rnd.nextInt(SIZE);
        } while (checkInput(aiX, aiY));
        for (int frame = SET; frame > 1; frame--) { //перебираем размеры фреймов
            if (checkWinAndBlock(DOT_HUMAN, frame, false)) break;
        }
        System.out.println("moveAI " + aiX + " " + aiY);
        map[aiX][aiY] = DOT_AI;
    }


    private boolean checkWinAndBlock(char point, int frame, boolean isWin) {
        int border = SIZE - frame;
        for (int i = 0; i <= border; i++) {
            for (int j = 0; j <= border; j++) {
                int countA = 0, countB = 0;
                for (int x = 0; x < frame; x++) {
                    int countY = 0, countX = 0;
                    for (int y = 0; y < frame; y++) {
                        if (map[x + i][y + j] == point) countY++;
                        if (map[y + j][x + i] == point) countX++;
                        if (x == y) {
                            if (map[x + i][y + j] == point) countA++;
                            if (map[x + i][frame - y - 1 + j] == point) countB++;
                        }
                        if (isWin) {    // если проверяем на победу
                            if (countA >= SET || countB >= SET || countX >= SET || countY >= SET)
                                return true;
                        } else {     // если проверяем на блокировку

                            if (x == y) {
                                if ((countA >= frame) &&
                                        (checkFrame(i + x, j + y, frame, 'A'))) return true;
                                if ((countB >= frame) &&
                                        (checkFrame(i + x, frame - y - 1 + j, frame, 'B'))) return true;
                            }
                            if ((countX >= frame) &&
                                    (checkFrame(i + x, j + y, frame, 'X'))) return true;
                            if ((countY >= frame) &&
                                    (checkFrame(i + x, j + y, frame, 'Y'))) return true;
                            if (x == y) {
                                if ((frame > 2) && (countA == frame - 1) &&
                                        (checkFrame(i + x, j + y, frame, 'E'))) return true;
                                if ((frame > 2) && (countB == frame - 1) &&
                                        (checkFrame(i + x, frame - y - 1 + j, frame, 'F'))) return true;
                            }
                            if ((frame > 2) && (countX == frame - 1) &&
                                    (checkFrame(i + x, j + y, frame, 'C'))) return true;
                            if ((frame > 2) && (countY == frame - 1) &&
                                    (checkFrame(i + x, j + y, frame, 'D'))) return true;

                        }
                    }
                }
            }
        }
        return false;
    }

    protected boolean checkFrame(int xFrame, int yFrame, int frame, char type) {

        switch (type) {
            case ('X'): // вертикальная линия
                if ((yFrame < SIZE - 1) && (map[yFrame + 1][xFrame] == DOT_EMPTY)) { // под линией
                    aiX = yFrame + 1;
                    aiY = xFrame;
                    return true;
                }
                if ((yFrame - frame >= 0) && (map[yFrame - frame][xFrame] == DOT_EMPTY)) { // над линией
                    aiX = yFrame - frame;
                    aiY = xFrame;
                    return true;
                }
                break;
            case ('Y'): // горизонтальная линия
                if ((yFrame < SIZE - 1) && (map[xFrame][yFrame + 1] == DOT_EMPTY)) { // справа от линии
                    aiX = xFrame;
                    aiY = yFrame + 1;
                    return true;
                }
                if ((yFrame - frame >= 0) && (map[xFrame][yFrame - frame] == DOT_EMPTY)) { // слева от линии
                    aiX = xFrame;
                    aiY = yFrame - frame;
                    return true;
                }
                break;
            case ('A')://главная диагональ
                if ((yFrame < SIZE - 1) && (xFrame < SIZE - 1)
                        && (map[xFrame + 1][yFrame + 1] == DOT_EMPTY)) { // справа от диагонали
                    aiX = xFrame + 1;
                    aiY = yFrame + 1;
                    return true;
                }
                if ((yFrame - frame >= 0) && (xFrame - frame >= 0)
                        && (map[xFrame - frame][yFrame - frame] == DOT_EMPTY)) { // слева от диагонали
                    aiX = xFrame - frame;
                    aiY = yFrame - frame;
                    return true;
                }
                break;
            case ('B')://вспомогательная диагональ
                if ((yFrame > 0) && (xFrame + 1 < SIZE)
                        && (map[xFrame + 1][yFrame - 1] == DOT_EMPTY)) { // слева от вспом диагонали
                    aiX = xFrame + 1;
                    aiY = yFrame - 1;
                    return true;
                }
                if ((yFrame + frame <= SIZE) && (xFrame - frame >= 0)
                        && (map[xFrame - frame][yFrame + frame] == DOT_EMPTY)) { // справа от вспом диагонали
                    aiX = xFrame - frame;
                    aiY = yFrame + frame;
                    return true;
                }
                break;
            case ('C')://между точками вертикально
                if (map[yFrame - 1][xFrame] == DOT_EMPTY) {
                    aiX = yFrame - 1;
                    aiY = xFrame;
                    return true;
                }
                break;
            case ('D')://между точками горизонтально
                if (map[xFrame][yFrame - 1] == DOT_EMPTY) { // справа от линии
                    aiX = xFrame;
                    aiY = yFrame - 1;
                    return true;
                }
                break;
            case ('E')://между точек главная диагональ
                if (map[xFrame - 1][yFrame - 1] == DOT_EMPTY) { // центр диагонали
                    aiX = xFrame - 1;
                    aiY = yFrame - 1;
                    return true;
                }
                break;
            case ('F')://между точек вспомогательная диагональ
                if (map[xFrame - 1][yFrame + 1] == DOT_EMPTY) { // центр вспом диагонали
                    aiX = xFrame - 1;
                    aiY = yFrame + 1;
                    return true;
                }
                break;
            default:
                throw new IndexOutOfBoundsException("Bad type of checkFrame.");
        }
        return false;
    }
}
