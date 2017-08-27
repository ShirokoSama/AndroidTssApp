package cn.edu.nju.tss.adapter.Item;


/**
 * Created by Srf on 2017/6/16
 */

public class CourseAssignment {

    private int id;
    private String title;
    private String description;
    private String startAt;
    private String endAt;
    private String status;

    public CourseAssignment(int id , String title, String description, String startAt,
                            String endAt, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getStatus() {
        switch (this.status) {
            case "newly":
                return "新建态";
            case "initing":
                return "正在初始化";
            case "initFail":
                return "初始化失败";
            case "initSuccess":
                return "初始化成功";
            case "ongoing":
                return "考试正在进行";
            case "timeup":
                return "考试已结束";
            case "analyzing":
                return "正在分析结果";
            case "analyzingFinish":
                return "结果分析完毕";
        }
        return "未知状态";
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
