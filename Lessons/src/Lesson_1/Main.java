/**
 * class Main
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_1;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Cat catTom = new Cat("Tom", Gender.Male);
        Cat catJerry = new Cat("Jerry", Gender.Female);

        Dog dogMax = new Dog("Max", Gender.Male);
        Dog dogBella = new Dog("Bella", Gender.Female);
        Dog dogJack = new Dog("Jack", Gender.Male);

        System.out.println("I fill so bad! I want everyone to die!");

        //this is a comment

        Woman olga = new Woman("Olga");
        Woman natalia = new Woman("Natalia");
        Fish fishPetty = new Fish("Petty", Gender.Female);
        fishPetty.setOwner(olga);
        natalia.setOwner(olga);

        System.out.println("Petty owner is " + fishPetty.getOwner());
        System.out.println("Natalia owner is " + natalia.getOwner());

        System.out.println(min(5, 14, 2, 3, 6));

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

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                System.out.print((i * j) + ((i * j < 10) ? "  " : " "));
            }
            System.out.println();
        }

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
}
