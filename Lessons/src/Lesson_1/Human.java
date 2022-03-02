/**
 * class Human
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_1;

public class Human extends Animal{
    public Human(String name, Gender gender) {
        super(name, gender);
    }

    @Override
    public void setOwner(Object owner) {
        System.out.println("Human has not owner!");
        super.setOwner(null);
    }
}
