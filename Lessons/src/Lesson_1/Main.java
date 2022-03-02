/**
 * class Main
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Cat catTom = new Cat("Tom", Gender.Male);
        Cat catJerry = new Cat("Jerry", Gender.Female);

        Dog dogMax = new Dog("Max", Gender.Male);
        Dog dogBella = new Dog("Bella", Gender.Female);
        Dog dogJack = new Dog("Jack", Gender.Male);

        System.out.println("I fill so bad! I want everyone to die!");

        //операции с объектами

        Woman olga = new Woman("Olga");
        Woman natalia = new Woman("Natalia");
        Fish fishPetty = new Fish("Petty", Gender.Female);
        fishPetty.setOwner(olga);
        natalia.setOwner(olga);

        System.out.println("Petty owner is " + fishPetty.getOwner());
        System.out.println("Natalia owner is " + natalia.getOwner());

        //минимум 2, 3, 4 и тд чисел
        System.out.println(min(5, 14, 2, 3, 6));

        //Мама мыла раму
        String[] text = {"Мама", "Мыла", "Раму"};
        int a = 0;
        int b = 1;
        for (int i = 0; i < getFactorial(text.length); i++) {
            for (String string : text) System.out.print(string);
            System.out.println();
            change(text, a++, b++);
            if (a >= text.length) a = 0;
            if (b >= text.length) b = 0;
        }
        System.out.println();

        //таблица умножения
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                System.out.print((i * j) + ((i * j < 10) ? "  " : " "));
            }
            System.out.println();
        }


        System.out.println();

        System.out.println("It's Windows path: \"C:\\Program Files\\Java\\jdk1.7.0\\bin\"");
        System.out.println("It's Java string: \\\"C:\\\\Program Files\\\\Java\\\\jdk1.7.0\\\\bin\\\"");
        System.out.println("日本語");

        //консольный ввод
        Scanner input = new Scanner(System.in);

        System.out.println("Введите Ваше имя: ");
        String name = input.next();
        System.out.println("Введите число лет: ");
        int year = input.nextInt();
        System.out.printf("%s захватит мир через %d лет", name, year);
        System.out.println();

        //алгоритм поиска фибоначи

        System.out.println(fib1(16)); //cyclic. (987)
        System.out.println(fib2(21)); //recursive. (10946)
        // что значит "с использованием памяти" я не понимаю, либо еще не дошел до этого в самообучении, либо туплю...(((
        System.out.println();
        // 4 задание как понял, не уверен что верно.
        Fib cache = new Fib();
        System.out.println(cache.getFib(21)); // first time (10946)
        System.out.println(cache.getFib(16)); // from cache (987)
        System.out.println(cache.getFib(22)); // first time (17711)
        System.out.println(cache.getFib(22)); // cache (17711)


    }

    public static int min(int a, int b) {
//        return Math.min(a, b);
        return (a > b) ? b : a;
    }

    public static int max(int a, int b) {
//        return Math.max(a, b);
        return (a < b) ? b : a;
    }

    public static int min(int... a) {
//        return Arrays.stream(a).min().getAsInt();
        int result = a[0];
        for (int dig : a) {
            if (result > dig) result = dig;
        }
        return result;

    }

    public static int getFactorial(int a) {
        if (a == 0) return 0;
        if (a > 16) {
            throw new ArithmeticException("number too big");
        }
        int result = 1;
        for (int i = 1; i <= a; i++) {
            result *= i;
        }
        return result;
    }

    public static <T> void change(T[] array, int a, int b) {
        try {
            T temp = array[a];
            array[a] = array[b];
            array[b] = temp;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public static int fib1(int i) {
        if (i == 0) return 0;
        if (i == 1 || i == 2) return 1;
        int result = 1;
        int pre = 1;
        for (int j = 3; j <= i; j++) {
            int buf = pre;
            pre = result;
            result += buf;
        }
        return result;
    }

    public static int fib2(int i) {
        if (i == 0) return 0;
        if (i == 1 || i == 2) return 1;
        return fib2(i - 2) + fib2(i - 1);
    }
}
