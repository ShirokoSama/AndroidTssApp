package cn.edu.nju.tss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import cn.edu.nju.tss.adapter.Item.Question;
import cn.edu.nju.tss.adapter.QuestionAdapter;
import cn.edu.nju.tss.util.HttpBasicJsonArrayRequest;
import cn.edu.nju.tss.util.SingletonRequestQueue;

/**
 * Created by Srf on 2017/6/17
 */

public class TeacherAssignmentActivity extends BottomMenuActivity {

    private int assignmentId;
    private String assignmentName;
    private int courseId;
    private String type;

    @Override
    protected void createCourse() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(assignmentName);
        linearLayout.removeAllViews();
        TextView text = (TextView) View.inflate(this, R.layout.simple_text, null);
        text.setText("题目列表");
        linearLayout.addView(text);

        SingletonRequestQueue.getInstance(this).getRequestQueue().cancelAll("tag");
        String url = basicUrl + "course/" + courseId + "/" + type;
        HttpBasicJsonArrayRequest request = new HttpBasicJsonArrayRequest(url, getSharedPreferences("auth", MODE_PRIVATE),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Question> list = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject assignment = response.getJSONObject(i);
                                if (assignment.getInt("id")==assignmentId) {
                                    JSONArray questions = assignment.getJSONArray("questions");
                                    for (int j = 0; j < questions.length(); j++) {
                                        JSONObject obj = questions.getJSONObject(j);
                                        list.add(new Question(obj.getInt("id"), obj.getString("title"),
                                                obj.getString("description"), obj.getString("difficulty"),
                                                obj.getString("gitUrl"), obj.getString("type"),
                                                obj.getJSONObject("creator").getString("name"),
                                                obj.getJSONObject("creator").getString("username"),
                                                obj.getJSONObject("creator").getString("email")));
                                    }
                                    break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        QuestionAdapter adapter = new QuestionAdapter(list, TeacherAssignmentActivity.this);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int assignmentId = TeacherAssignmentActivity.this.assignmentId;
                                int questionId = (int) parent.getItemIdAtPosition(position);
                                String questionName = ((Question) parent.getItemAtPosition(position)).getTitle();
                                Bundle bd = new Bundle();
                                bd.putInt("assignmentId", assignmentId);
                                bd.putInt("questionId", questionId);
                                bd.putString("questionName", questionName);
                                Intent intent = new Intent(TeacherAssignmentActivity.this, TeacherQuestionActivity.class);
                                intent.putExtras(bd);
                                startActivity(intent);
                            }
                        });
                        linearLayout.addView(listView);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "HTTP验证失败 请重新登陆", Toast.LENGTH_LONG).show();
                startActivity(new Intent(TeacherAssignmentActivity.this, LoginActivity.class));
                finish();
            }
        });
        request.setTag("tag");
        SingletonRequestQueue.getInstance(this).addToRequestQueue(request);
    }

    @Override
    protected void defaultInit() {
        Bundle bd = getIntent().getExtras();
        assignmentId = bd.getInt("assignmentId");
        assignmentName = bd.getString("assignmentName");
        courseId = bd.getInt("courseId");
        type = bd.getString("type");
        bottomNavigationView.setSelectedItemId(R.id.navigation_course);
        createCourse();
    }

}
