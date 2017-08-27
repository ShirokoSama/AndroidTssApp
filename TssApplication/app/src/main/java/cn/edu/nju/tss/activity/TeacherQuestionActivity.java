package cn.edu.nju.tss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.tss.R;
import cn.edu.nju.tss.adapter.Item.StudentScore;
import cn.edu.nju.tss.adapter.StudentScoreAdapter;
import cn.edu.nju.tss.util.HttpBasicJsonObjectRequest;
import cn.edu.nju.tss.util.MockJson;
import cn.edu.nju.tss.util.SingletonRequestQueue;

/**
 * Created by Srf on 2017/6/17
 */

public class TeacherQuestionActivity extends BottomMenuActivity {

    private int assignmentId;
    private int questionId;
    private String questionName;
    private String questionDescription;
    private int studentNum;
    private List<Integer> count = new ArrayList<>();
    private LinearLayout scoreCountInfo;

    private void createCount() {
        linearLayout.removeView(listView);
        linearLayout.addView(scoreCountInfo);
        TextView description = (TextView) findViewById(R.id.count_question_description);
        TextView num = (TextView) findViewById(R.id.student_num);
        TextView student_count = (TextView) findViewById(R.id.student_score_count);
        description.setText(questionDescription);
        String numStr = "参加学生总数： " + studentNum;
        num.setText(numStr);
        String countStr = "90 - 100： " + count.get(4) + "\n"
                + "80 - 89： " + count.get(3) + "\n"
                + "70 - 79： " + count.get(2) + "\n"
                + "60 - 69： " + count.get(1) + "\n"
                + "0 - 59： " + count.get(0);
        student_count.setText(countStr);
    }

    private void createScores() {
        linearLayout.removeView(scoreCountInfo);
        linearLayout.addView(listView);
    }

    @Override
    protected void createCourse() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(questionName);
        linearLayout.removeAllViews();
        TabLayout tabs = (TabLayout) View.inflate(this, R.layout.simple_tab_2, null);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: createCount(); break;
                    case 1: createScores(); break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        linearLayout.addView(tabs);

        scoreCountInfo = (LinearLayout) View.inflate(this, R.layout.score_count_info, null);

        SingletonRequestQueue.getInstance(this).getRequestQueue().cancelAll("tag");
        String url = basicUrl + "assignment/" + assignmentId + "/score";
        HttpBasicJsonObjectRequest request = new HttpBasicJsonObjectRequest(url, getSharedPreferences("auth", MODE_PRIVATE),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handleResponse(MockJson.getMockAssignmentScore());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "HTTP验证失败 请重新登陆", Toast.LENGTH_LONG).show();
                startActivity(new Intent(TeacherQuestionActivity.this, LoginActivity.class));
                finish();
            }
        });
        request.setTag("tag");
        SingletonRequestQueue.getInstance(this).addToRequestQueue(request);

    }

    private void handleResponse(JSONObject response) {
        System.out.println(response.toString());
        List<StudentScore> list = new ArrayList<>();
        try {
            JSONArray array = response.getJSONArray("questions");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (obj.getJSONObject("questionInfo").getInt("id")==questionId) {
                    JSONArray scores = obj.getJSONArray("students");
                    questionDescription = obj.getJSONObject("questionInfo").getString("description");
                    for (int j = 0; j < scores.length(); j++) {
                        JSONObject score = scores.getJSONObject(i);
                        list.add(new StudentScore(score.getInt("studentId"), score.getString("studentName"),
                                score.getString("studentNumber"), score.getInt("score"), score.getBoolean("scored")));
                    }
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StudentScoreAdapter adapter = new StudentScoreAdapter(list, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int assignmentId = TeacherQuestionActivity.this.assignmentId;
                int questionId = TeacherQuestionActivity.this.questionId;
                String questionName = TeacherQuestionActivity.this.questionName;
                int studentId = (int) parent.getItemIdAtPosition(position);
                String studentName = ((StudentScore) parent.getItemAtPosition(position)).getName();
                Bundle bd = new Bundle();
                bd.putInt("assignmentId", assignmentId);
                bd.putInt("questionId", questionId);
                bd.putString("questionName", questionName);
                bd.putString("studentName", studentName);
                bd.putInt("studentId", studentId);
                Intent intent = new Intent(TeacherQuestionActivity.this, QuestionAnalysisActivity.class);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
        studentNum = list.size();
        for (StudentScore s: list) {
            int score = s.getScore();
            if (score >= 90)
                count.set(4, count.get(4) + 1);
            else if (score >= 80)
                count.set(3, count.get(3) + 1);
            else if (score >= 70)
                count.set(2, count.get(2) + 1);
            else if (score >= 60)
                count.set(1, count.get(1) + 1);
            else
                count.set(0, count.get(0) + 1);
        }
        scoreCountInfo = (LinearLayout) View.inflate(this, R.layout.score_count_info, null);
        createCount();
    }

    @Override
    protected void defaultInit() {
        Bundle bd = getIntent().getExtras();
        assignmentId = bd.getInt("assignmentId");
        questionId = bd.getInt("questionId");
        questionName = bd.getString("questionName");
        bottomNavigationView.setSelectedItemId(R.id.navigation_course);
        for (int i = 0; i < 5; i++)
            count.add(0);
        createCourse();
    }

}
