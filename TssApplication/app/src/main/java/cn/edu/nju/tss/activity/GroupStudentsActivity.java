package cn.edu.nju.tss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewStub;
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
import cn.edu.nju.tss.adapter.GroupStudentAdapter;
import cn.edu.nju.tss.adapter.Item.GroupStudent;
import cn.edu.nju.tss.util.HttpBasicJsonArrayRequest;
import cn.edu.nju.tss.util.SingletonRequestQueue;

/**
 * Created by Srf on 2017/6/16
 */

public class GroupStudentsActivity extends BottomMenuActivity {

    private int groupId;
    private String groupName;

    @Override
    protected void createGroup() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(groupName);
        linearLayout.removeAllViews();
        TextView text = (TextView) View.inflate(this, R.layout.simple_text, null);
        text.setText("学生列表");
        linearLayout.addView(text);
//        TabLayout tabs = (TabLayout) View.inflate(this, R.layout.simple_tab, null);
//        linearLayout.addView(tabs);
        String url = basicUrl + "group/" + groupId + "/students";
        SingletonRequestQueue.getInstance(this).getRequestQueue().cancelAll("tag");
        HttpBasicJsonArrayRequest request = new HttpBasicJsonArrayRequest(url, getSharedPreferences("auth", MODE_PRIVATE),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<GroupStudent> list = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                list.add(new GroupStudent(obj.getInt("id"), obj.getString("name"),
                                        obj.getString("username"), obj.getString("avatar"), obj.getString("gender"),
                                        obj.getString("email"), obj.getString("gitUsername")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        GroupStudentAdapter adapter = new GroupStudentAdapter(list, GroupStudentsActivity.this);
                        listView.setAdapter(adapter);
                        linearLayout.addView(listView);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "HTTP验证失败 请重新登陆", Toast.LENGTH_LONG).show();
                startActivity(new Intent(GroupStudentsActivity.this, LoginActivity.class));
                finish();
            }
        });
        request.setTag("tag");
        SingletonRequestQueue.getInstance(this).addToRequestQueue(request);
    }

    @Override
    protected void defaultInit() {
        Bundle bd = getIntent().getExtras();
        groupId = bd.getInt("groupId");
        groupName = bd.getString("groupName");
        bottomNavigationView.setSelectedItemId(R.id.navigation_group);
        createGroup();
    }

}
