/**
 * class Animal
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_1;

public abstract class Animal<T> {
    private String name;
    private T owner;
    private Gender gender;

    public Animal(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getOwner() {
        return owner;
    }

    public void setOwner(T owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", owner=" + owner +
                ", gender=" + gender +
                '}';
    }
}
