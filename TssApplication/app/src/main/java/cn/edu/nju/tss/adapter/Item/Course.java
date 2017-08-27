package cn.edu.nju.tss.adapter.Item;

/**
 * Created by Srf on 2017/6/8
 */

public class Course {

    private int id;
    private String courseName;

    public Course(int id, String courseName) {
        this.id = id;
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
