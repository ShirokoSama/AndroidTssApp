package cn.edu.nju.tss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.nju.tss.R;
import cn.edu.nju.tss.util.HttpBasicJsonObjectRequest;
import cn.edu.nju.tss.util.MockJson;
import cn.edu.nju.tss.util.SingletonRequestQueue;

/**
 * Created by Srf on 2017/6/18
 */

public class QuestionAnalysisActivity extends BottomMenuActivity {

    private int assignmentId;
    private int questionId;
    private String questionName;
    private int studentId;
    private String studentName;
    private LinearLayout analysis;
    private LinearLayout textarea;

    private void createAnalysis() {
        linearLayout.removeView(textarea);
        linearLayout.addView(analysis);
    }

    private void createReadMe() {
        linearLayout.removeView(analysis);
        linearLayout.addView(textarea);
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
        TabLayout tabs = (TabLayout) View.inflate(this, R.layout.simple_tab_4, null);
        String textStr = studentName + "结果分析";
        tabs.getTabAt(0).setText(textStr);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: createAnalysis(); break;
                    case 1: createReadMe(); break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        linearLayout.removeAllViews();
        linearLayout.addView(tabs);

        SingletonRequestQueue.getInstance(this).getRequestQueue().cancelAll("tag");
        String url = basicUrl + "assignment/" + assignmentId + "/student/" + studentId + "/analysis";
        HttpBasicJsonObjectRequest request = new HttpBasicJsonObjectRequest(url, getSharedPreferences("auth", MODE_PRIVATE),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handleResponse(MockJson.getMockAssignmentResult());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "HTTP验证失败 请重新登陆", Toast.LENGTH_LONG).show();
                startActivity(new Intent(QuestionAnalysisActivity.this, LoginActivity.class));
                finish();
            }
        });
        request.setTag("tag");
        SingletonRequestQueue.getInstance(this).addToRequestQueue(request);

    }

    private void handleResponse(JSONObject response) {
        try {
            JSONArray array = response.getJSONArray("questionResults");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (obj.getInt("questionId")==questionId) {
                    handleResult(obj);
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleResult(JSONObject obj) throws JSONException {
        int score = obj.getJSONObject("scoreResult").getInt("score");
        boolean isCompiled = obj.getJSONObject("testResult").getBoolean("compile_succeeded");
        boolean isTested = obj.getJSONObject("testResult").getBoolean("tested");
        JSONArray array = obj.getJSONObject("testResult").getJSONArray("testcases");
        int total = array.length();
        int pass = 0;
        for (int i = 0; i < array.length(); i++) {
            JSONObject tc = array.getJSONObject(i);
            if (tc.getBoolean("passed"))
                pass++;
        }
        textarea = (LinearLayout) View.inflate(this, R.layout.readme_area, null);
        linearLayout.addView(textarea);
        String readmeStr = "这是README";
        ((TextView) findViewById(R.id.readme)).setText(readmeStr);
        analysis = (LinearLayout) View.inflate(this, R.layout.question_result, null);
        linearLayout.removeView(textarea);
        linearLayout.addView(analysis);
        TextView r_score = (TextView) findViewById(R.id.result_score);
        TextView r_compile = (TextView) findViewById(R.id.result_compile);
        TextView r_tested = (TextView) findViewById(R.id.result_tested);
        TextView r_test_case = (TextView) findViewById(R.id.result_test_case);
        String scoreStr = "得分： " + score;
        String compileStr = "是否成功编译： " + (isCompiled?"是":"否");
        String testedStr = "是否成功测试： " + (isTested?"是":"否");
        String testCaseStr = "测试用例通过情况： " + pass + " / " + total;
        r_score.setText(scoreStr);
        r_compile.setText(compileStr);
        r_tested.setText(testedStr);
        r_test_case.setText(testCaseStr);
    }

    @Override
    protected void defaultInit() {
        Bundle bd = getIntent().getExtras();
        assignmentId = bd.getInt("assignmentId");
        questionId = bd.getInt("questionId");
        questionName = bd.getString("questionName");
        studentId = bd.getInt("studentId");
        studentName = bd.getString("studentName");
        bottomNavigationView.setSelectedItemId(R.id.navigation_course);
        createCourse();
    }

}
