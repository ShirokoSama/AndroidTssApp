package cn.edu.nju.tss.adapter.Item;

/**
 * Created by Srf on 2017/6/8
 */

public class Group {

    private int id;
    private String groupName;

    public Group(String groupName, int id) {
        this.id = id;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
