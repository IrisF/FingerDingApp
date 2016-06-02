package ifi.lmu.com.handmeasurementstudy.system;

/**
 * Created by Jonny on 02.06.2016.
 */
public class User {

    public int id;
    public int age;
    public String gender;
    public int span;
    public int width;
    public int length;

    public User (int id, int age, String gender, int span, int width, int length){
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.span = span;
        this.width = width;
        this.length = length;
    }
}
