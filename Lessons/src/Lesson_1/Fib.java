/**
 * class Fib
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_1;

import java.util.HashMap;

public class Fib {
    HashMap<Integer, Long> cache = new HashMap<>();
    int index;

    public Fib() {
        cache.put(0, (long) 0);
        cache.put(1, (long) 1);
        cache.put(2, (long) 1);
        index = 3;
    }

    public long getFib(int number) {
        if (number >= index) {
            for (int i = index; i <= number; i++) {
                cache.put(index++, cache.get(i - 1) + cache.get(i - 2));
            }
//            System.out.print("cycle: ");
        }
        return cache.get(number);
    }
}
