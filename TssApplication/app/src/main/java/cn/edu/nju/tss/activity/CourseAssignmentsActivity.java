package cn.edu.nju.tss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.tss.R;
import cn.edu.nju.tss.adapter.CourseAssignmentAdapter;
import cn.edu.nju.tss.adapter.Item.CourseAssignment;
import cn.edu.nju.tss.util.HttpBasicJsonArrayRequest;
import cn.edu.nju.tss.util.SingletonRequestQueue;

/**
 * Created by Srf on 2017/6/16
 */

public class CourseAssignmentsActivity extends BottomMenuActivity {

    private int courseId;
    private String courseName;
    private String type;
    private CourseAssignmentAdapter examAdapter;
    private CourseAssignmentAdapter homeworkAdapter;
    private CourseAssignmentAdapter exerciseAdapter;

    private void createExams() {
        listView.setAdapter(examAdapter);
        this.type = "exam";
    }

    private void createExercises() {
        listView.setAdapter(exerciseAdapter);
        this.type = "exercise";
    }

    private void createHomeworks() {
        listView.setAdapter(homeworkAdapter);
        this.type = "homework";
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
        toolbar.setTitle(courseName);
        linearLayout.removeAllViews();
        TabLayout tabs = (TabLayout) View.inflate(this, R.layout.simple_tab, null);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: createExams(); break;
                    case 1: createExercises(); break;
                    case 2: createHomeworks(); break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        linearLayout.addView(tabs);
        handleRequest();
        linearLayout.addView(listView);
        listView.setAdapter(examAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bd = new Bundle();
                int assignmentId = (int) parent.getItemIdAtPosition(position);
                String assignmentName = ((CourseAssignment) parent.getItemAtPosition(position)).getTitle();
                bd.putInt("assignmentId", assignmentId);
                bd.putString("assignmentName", assignmentName);
                bd.putInt("courseId", CourseAssignmentsActivity.this.courseId);
                bd.putString("type", CourseAssignmentsActivity.this.type);
                Intent intent;
                if (getSharedPreferences("auth", MODE_PRIVATE).getString("type", "student").equals("teacher"))
                    intent = new Intent(CourseAssignmentsActivity.this, TeacherAssignmentActivity.class);
                else
                    intent = new Intent(CourseAssignmentsActivity.this, StudentAssignmentActivity.class);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
    }

    private void handleRequest() {
        SingletonRequestQueue.getInstance(this).getRequestQueue().cancelAll("tag");

        String examUrl = basicUrl + "course/" + courseId + "/exam";
        HttpBasicJsonArrayRequest examRequest = new HttpBasicJsonArrayRequest(examUrl, getSharedPreferences("auth", MODE_PRIVATE),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        examAdapter = new CourseAssignmentAdapter(initAssignmentList(response), CourseAssignmentsActivity.this);
                        createExams();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        examRequest.setTag("tag");
        SingletonRequestQueue.getInstance(this).addToRequestQueue(examRequest);

        String exerciseUrl = basicUrl + "course/" + courseId + "/exercise";
        HttpBasicJsonArrayRequest exerciseRequest = new HttpBasicJsonArrayRequest(exerciseUrl, getSharedPreferences("auth", MODE_PRIVATE),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        exerciseAdapter = new CourseAssignmentAdapter(initAssignmentList(response), CourseAssignmentsActivity.this);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        exerciseRequest.setTag("tag");
        SingletonRequestQueue.getInstance(this).addToRequestQueue(exerciseRequest);

        String homeworkUrl = basicUrl + "course/" + courseId + "/homework";
        HttpBasicJsonArrayRequest homeworkRequest = new HttpBasicJsonArrayRequest(homeworkUrl, getSharedPreferences("auth", MODE_PRIVATE),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        homeworkAdapter = new CourseAssignmentAdapter(initAssignmentList(response), CourseAssignmentsActivity.this);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        homeworkRequest.setTag("tag");
        SingletonRequestQueue.getInstance(this).addToRequestQueue(homeworkRequest);
    }

    private List<CourseAssignment> initAssignmentList(JSONArray response) {
        List<CourseAssignment> list = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject obj = response.getJSONObject(i);
                list.add(new CourseAssignment(obj.getInt("id"), obj.getString("title"),
                        obj.getString("description"), obj.getString("startAt"),
                        obj.getString("endAt"), obj.getString("status")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    protected void defaultInit() {
        Bundle bd = getIntent().getExtras();
        courseId = bd.getInt("courseId");
        courseName = bd.getString("courseName");
        bottomNavigationView.setSelectedItemId(R.id.navigation_course);
        createCourse();
    }

}
