package Lesson_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * class Game
 *
 * @author Melnev Dmitry
 * @version 2022
 */

public class Game {
    char[][] map;
    int SIZE = 5; //размер поля
    int SET = 4;  //длина победной линии
    final char DOT_EMPTY = '-';
    final char DOT_HUMAN = 'X';
    final char DOT_AI = 'O';

    Scanner input = new Scanner(System.in);
    Random rnd = new Random();

    int aiX, aiY, step;
    char[][] temp;
    ArrayList<Coordinate> list = new ArrayList<>();

    int run() {
        initMap();
        while (true) {
            printMap();
            humanMove();
            if (checkWin(DOT_HUMAN, map)) {
                printMap();
                System.out.println("Вы победили!");
                return 1;
            }
            if (checkDrawn()) {
                printMap();
                System.out.println("Ничья!");
                return 0;
            }
            printMap();
            moveAI();
            if (checkWin(DOT_AI, map)) {
                printMap();
                System.out.println("Вы проиграли!");
                return -1;
            }
            if (checkDrawn()) {
                printMap();
                System.out.println("Ничья!");
                return 0;
            }
        }
    }

    private void initMap() {
        map = new char[SIZE][SIZE];
        for (char[] line : map) Arrays.fill(line, DOT_EMPTY);
    }

    private void printMap() {
        System.out.print("  ");
        for (int i = 1; i < SIZE + 1; i++) {
            System.out.printf("%d %s", i, (i < 10) ? " " : "");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%d%s", (i + 1), (i < 9) ? " " : "");
            for (int j = 0; j < SIZE; j++)
                System.out.print(map[i][j] + "  ");
            System.out.println();
        }
    }

    private void humanMove() {
        int x, y;
        do {
            System.out.print("Введите координаты (X, Y):");
            y = input.nextInt() - 1;
            x = input.nextInt() - 1;
            if (checkInput(x, y)) System.out.println("Ход не засчитан.");
        } while (checkInput(x, y));
        map[x][y] = DOT_HUMAN;
    }

    private void moveAI() {
        do {
            aiX = rnd.nextInt(SIZE);
            aiY = rnd.nextInt(SIZE);
        } while (checkInput(aiX, aiY));
        blockHuman();
        map[aiX][aiY] = DOT_AI;
    }

    private void blockHuman() {
        list.clear();
        // перебираем все варианты ходов, первый уровень
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (map[x][y] == DOT_EMPTY) {
                    temp = map.clone();
                    step = 0;
                    temp[x][y] = DOT_AI;
                    if (checkWin(DOT_AI, temp)) {
                        aiX = x;
                        aiY = y;
                        return;
                    }
                    scanSteps(DOT_HUMAN); // запускаем рекурсию
                    temp[x][y] = DOT_EMPTY;
                }
            }
        }
        if (list.size() > 0) { //если есть победа
            Coordinate ddd = list.get(0);
            for (Coordinate fff : list) {
                if (fff.steps < ddd.steps) ddd = fff; // выбираем наибыструю победу
            }
            aiX = ddd.X;
            aiY = ddd.Y;
        }
    }

    private void scanSteps(char who) {

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (temp[x][y] == DOT_EMPTY) {
                    step++;
                    temp[x][y] = who;
                    if (checkWin(who, temp)) {
                        temp[x][y] = DOT_EMPTY;
                        list.add(new Coordinate(x, y, step, who));
                        step--;
                        return;
                    }
                    if (step >= SET) { //ограничиваем рекурсию
                        step--;
                        temp[x][y] = DOT_EMPTY;
                        return;
                    }
                    who = (who == DOT_AI) ? DOT_HUMAN : DOT_AI;
                    scanSteps(who);
                    step--;
                    temp[x][y] = DOT_EMPTY;
                }
            }
        }
    }

    private boolean checkInput(int x, int y) {
        return (x < 0) || (x >= SIZE) || (y < 0) || (y >= SIZE) || (map[x][y] != DOT_EMPTY);
    }

    boolean checkDrawn() {
        for (char[] line : map)
            for (char point : line)
                if (point == DOT_EMPTY) return false;
        return true; // ничего не нашли, ничья.
    }

    private boolean checkWin(char point, char[][] test) {
        int border = SIZE - SET;  // граница положения фреймов
        // перебираем фреймы на всей карте
        for (int i = 0; i <= border; i++) { //столбец карты
            for (int j = 0; j <= border; j++) { //строка карты
                int countA = 0, countB = 0; //инициализация счетчиков по диагоналям
                // перебираем внутри фрейма
                for (int x = 0; x < SET; x++) { //столбец фрейма
                    int countY = 0, countX = 0; //инициализация счетчиков по строкам
                    for (int y = 0; y < SET; y++) { //строка фрейма
                        if (test[x + i][y + j] == point) countY++;               //проверяем горизонтали
                        if (test[y + j][x + i] == point) countX++;               //проверяем вертикали
                        if (x == y) {                                           //если диагональ
                            if (test[x + i][y + j] == point) countA++;           //проверяем главную диагональ фрейма
                            if (test[x + i][SET - y - 1 + j] == point) countB++; //проверяем доп. диагональ
                        }
                        if (countA >= SET || countB >= SET || countX >= SET || countY >= SET)
                            return true;//если нашли
                    }
                }
            }
        }
        return false;
    }

}
