package cn.edu.nju.tss.adapter.Item;

/**
 * Created by Srf on 2017/6/16
 */

public class GroupStudent {

    private int id;
    private String name;
    private String userName;
    private String avatar;
    private String gender;
    private String email;
    private String gitUserName;

    public GroupStudent(int id, String name, String userName, String avatar, String gender, String email, String gitUserName) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.avatar = avatar;
        this.gender = gender;
        this.email = email;
        this.gitUserName = gitUserName;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGitUserName() {
        return gitUserName;
    }

    public void setGitUserName(String gitUserName) {
        this.gitUserName = gitUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
