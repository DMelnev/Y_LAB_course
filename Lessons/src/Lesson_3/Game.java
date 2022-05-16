
package Lesson_3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * class Game
 *
 * @author Melnev Dmitry
 * @version 2022
 */
public class Game {
    char[][] map;
    protected int size; //размер поля
    protected int set;  //длина победной линии
    final char DOT_EMPTY = '-';
    final char DOT_HUMAN = 'X';
    final char DOT_AI = 'O';

    final File FILE = new File("statistic.txt");

    Scanner input = new Scanner(System.in);
    Random rnd = new Random();

    int aiX, aiY, step, stepGame, Game;
    boolean isAI;
    char[][] temp;
    String name1, name2, result;
    ArrayList<Coordinate> list = new ArrayList<>();
    Data root;

    public Game(Data root) {
        this.root = root;

    }

    public void run() {
        boolean cont = true;
        stepGame = 1;
        Game = 1;
        while (cont) {
            enterMapSize();
            initMap();
            inputName();
            int k = 1;

            root.addChildNode("Player", "", "id", "1", "name", name1, "symbol", "X");
            root.addChildNode("Player", "", "id", "2", "name", name2, "symbol", "O");
            Data game = new Data("Game", "",
                    "map", size + "x" + size, "set", String.valueOf(set));
            root.addChildNode(game);

            while (true) {
                if (k > 2) k = 1;
                if (!isAI || k == 1) printMap();
                int[] tmp = move(k);

                game.addChildNode("Step", (tmp[1] + 1) + "," + (tmp[0] + 1)
                        , "num", String.valueOf(stepGame), "playerId", String.valueOf(k));

                if (checkWin(DOT_HUMAN, map)) {
                    printMap();
                    if (isAI) System.out.println("Вы победили!");
                    else System.out.println(name1 + " победил!");
                    result = " выиграл у ";

                    Data gameResult = new Data("GameResult");
                    game.addChildNode(gameResult);
                    gameResult.addChildNode("Player", "",
                            "id", String.valueOf(k), "name", name1, "symbol", "X");
                    break;
                }
                if (checkWin(DOT_AI, map)) {
                    printMap();
                    if (isAI) System.out.println("Вы проиграли!");
                    else System.out.println(name2 + " победил!");
                    result = " проиграл ";

                    Data gameResult = new Data("GameResult");
                    game.addChildNode(gameResult);
                    gameResult.addChildNode("Player", "",
                            "id", String.valueOf(k), "name", name2, "symbol", "O");
                    break;
                }
                if (checkDrawn()) {
                    printMap();
                    System.out.println("Ничья!");
                    result = " съиграл в ничью с ";
                    game.addChildNode("GameResult", "Draw!");
                    break;
                }
                k++;
                stepGame++;
            }
            writeToFile();
            System.out.println("Желаете еще раз? (Y/N)");
            String tmp = input.next();
            cont = tmp.equals("Y") || tmp.equals("y");
            stepGame = 1;
        }
    }

    private void inputName() {
        selectAI();
        if (isAI) {
            System.out.println("Введите Ваше имя:");
            name1 = input.next();
            name2 = "AI";
        } else {
            System.out.println("Введите имя первого игрока:");
            name1 = input.next();
            System.out.println("Введите имя второго игрока:");
            name2 = input.next();
        }
    }

    protected void enterMapSize() {
        do {
            System.out.println("Введите размер поля (от 1 до 6)");
            size = input.nextInt();
        } while ((size < 1) || (size > 6));
        do {
            System.out.println("Введите количество победных точек (от 1 до " +
                    (Math.min(size, 4))
                    + ")");
            set = input.nextInt();
        } while ((set < 1) || (set > size) || (set > 4));
        map = new char[size][size];
    }

    private void selectAI() {
        int num;
        do {
            System.out.println("Введите количество игроков:");
            num = input.nextInt();
            if (num < 1 || num > 2) System.out.println("Возможен 1 или 2 игрока.");
            isAI = num == 1;
        } while (num < 1 || num > 2);
    }

    private void initMap() {
        map = new char[size][size];
        for (char[] line : map) Arrays.fill(line, DOT_EMPTY);
    }

    private void printMap() {
        System.out.print("  ");
        for (int i = 1; i < size + 1; i++) {
            System.out.printf("%d %s", i, (i < 10) ? " " : "");
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.printf("%d%s", (i + 1), (i < 9) ? " " : "");
            for (int j = 0; j < size; j++)
                System.out.print(map[i][j] + "  ");
            System.out.println();
        }
    }

    private void writeToFile() {
        String text = name1 + result + name2 + "\n";

        if (!FILE.exists()) {
            try {
                FILE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(FILE, true)) {
            outputStream.write(text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] move(int num) {
        int[] tmp = new int[2];
        switch (num) {
            case (1):
                tmp = inputStep(name1);
                map[tmp[0]][tmp[1]] = DOT_HUMAN;
                break;
            case (2):
                if (isAI) {
                    moveAI();
                    tmp[0] = aiX;
                    tmp[1] = aiY;
                } else {
                    tmp = inputStep(name2);
                    map[tmp[0]][tmp[1]] = DOT_AI;
                }
                break;
            default:
                throw new IndexOutOfBoundsException("Argument of method \"move\" is not valid");
        }
        return tmp;
    }

    private int[] inputStep(String name) {
        int x, y;
        do {
            System.out.print("Ход " + name + ". Введите координаты (X, Y):");
            y = input.nextInt() - 1;
            x = input.nextInt() - 1;
            if (checkInput(x, y)) System.out.println("Ход не засчитан.");
        } while (checkInput(x, y));

        return new int[]{x, y};
    }

    protected void moveAI() {
        do {
            aiX = rnd.nextInt(size);
            aiY = rnd.nextInt(size);
        } while (checkInput(aiX, aiY));

        list.clear();
        // перебираем все варианты ходов, первый уровень
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
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
                if (fff.getSteps() < ddd.getSteps()) ddd = fff; // выбираем наибыструю победу
            }
            aiX = ddd.getX();
            aiY = ddd.getY();
        }

        map[aiX][aiY] = DOT_AI;
    }

    private void scanSteps(char who) {

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (temp[x][y] == DOT_EMPTY) {
                    step++;
                    temp[x][y] = who;
                    if (checkWin(who, temp)) {
                        temp[x][y] = DOT_EMPTY;
                        list.add(new Coordinate(x, y, step));
                        step--;
                        return;
                    }
                    if (step >= set) { //ограничиваем рекурсию
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

    protected boolean checkInput(int x, int y) {
        return (x < 0) || (x >= size) || (y < 0) || (y >= size) || (map[x][y] != DOT_EMPTY);
    }

    private boolean checkDrawn() {
        for (char[] line : map)
            for (char point : line)
                if (point == DOT_EMPTY) return false;
        return true; // ничего не нашли, ничья.
    }

    protected boolean checkWin(char point, char[][] test) {
        int border = size - set;  // граница положения фреймов
        // перебираем фреймы на всей карте
        for (int i = 0; i <= border; i++) { //столбец карты
            for (int j = 0; j <= border; j++) { //строка карты
                int countA = 0, countB = 0; //инициализация счетчиков по диагоналям
                // перебираем внутри фрейма
                for (int x = 0; x < set; x++) { //столбец фрейма
                    int countY = 0, countX = 0; //инициализация счетчиков по строкам
                    for (int y = 0; y < set; y++) { //строка фрейма
                        if (test[x + i][y + j] == point) countY++;               //проверяем горизонтали
                        if (test[y + j][x + i] == point) countX++;               //проверяем вертикали
                        if (x == y) {                                           //если диагональ
                            if (test[x + i][y + j] == point) countA++;           //проверяем главную диагональ фрейма
                            if (test[x + i][set - y - 1 + j] == point) countB++; //проверяем доп. диагональ
                        }
                        if (countA >= set || countB >= set || countX >= set || countY >= set)
                            return true;//если нашли
                    }
                }
            }
        }
        return false;
    }

    public void player(String fileName) throws Exception {
        System.out.println("Playing file: " + fileName);

        Data root = ParserXML.dataFromFile(fileName);
        if (!root.getTagName().equals("GamePlay")) {
            System.out.println("Не верный формат файла");
            return;
        }
        for (Data game : root.getChildNodes()) {
            if (game.getTagName().equals("Player")) {//в ТЗ не используется
                System.out.printf("Player %s name is %s as %s\n",
                        game.getAttrByName("id"),
                        game.getAttrByName("name"),
                        game.getAttrByName("symbol"));
                continue;
            }
//            System.out.println(game);

            System.out.printf("Game %s set %s\n",
                    game.getAttrByName("map"),
                    game.getAttrByName("set"));
            set = Integer.parseInt(game.getAttrByName("set"));
            size = Integer.parseInt(game.getAttrByName("map").split("x")[0]);
            initMap();

            for (Data step : game.getChildNodes()) {
                if (step.getTagName().equals("GameResult")) {
                    if (step.getText().equals("Draw!")) {
                        System.out.println("Draw!");
                    } else {
                        Data winner = step.getChildNodeByName("Player");
                        System.out.printf("Player %s -> %s is winner as '%s'!\n",
                                winner.getAttrByName("id"),
                                winner.getAttrByName("name"),
                                winner.getAttrByName("symbol"));
                    }
                    System.out.println();
                    continue;
                }
                if (step.getTagName().equals("Step")) {
                    char mark = (step.getAttrByName("playerId").equals("1")) ? DOT_HUMAN : DOT_AI;
                    String[] coord = step.getText().split(",");
                    if (coord.length != 2) continue;
                    map[(Integer.parseInt(coord[1]) - 1)][(Integer.parseInt(coord[0]) - 1)] = mark;
                    printMap();
                    System.out.println();
                }
            }
        }
    }
}
