package cn.edu.nju.tss.adapter.Item;

/**
 * Created by Srf on 2017/6/18
 */

public class StudentScore {

    private int id;
    private String name;
    private String number;
    private int score;
    private boolean scored;

    public StudentScore(int id, String name, String number, int score, boolean scored) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.score = score;
        this.scored = scored;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }

}
