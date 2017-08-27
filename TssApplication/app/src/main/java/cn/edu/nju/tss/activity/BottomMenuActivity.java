package cn.edu.nju.tss.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.tss.R;
import cn.edu.nju.tss.adapter.CourseAdapter;
import cn.edu.nju.tss.adapter.GroupAdapter;
import cn.edu.nju.tss.adapter.Item.Course;
import cn.edu.nju.tss.adapter.Item.Group;
import cn.edu.nju.tss.util.HttpBasicJsonArrayRequest;
import cn.edu.nju.tss.util.SingletonRequestQueue;

public class BottomMenuActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected ListView listView;
    protected BottomNavigationView bottomNavigationView;
    protected LinearLayout linearLayout;
    private int navigationItemId;
    protected String basicUrl = "http://115.29.184.56:8090/api/";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_group:
                    if (navigationItemId != R.id.navigation_group) {
                        BottomMenuActivity.this.createGroup();
                        BottomMenuActivity.this.navigationItemId = R.id.navigation_group;
                    }
                    return true;
                case R.id.navigation_course:
                    if (navigationItemId != R.id.navigation_course) {
                        BottomMenuActivity.this.createCourse();
                        BottomMenuActivity.this.navigationItemId = R.id.navigation_course;
                    }
                    return true;
                case R.id.navigation_account:
                    if (navigationItemId != R.id.navigation_account) {
                        BottomMenuActivity.this.createAccount();
                        BottomMenuActivity.this.navigationItemId = R.id.navigation_account;
                    }
                    return true;
            }
            return false;
        }

    };

    protected void createGroup() {
        if (getSharedPreferences("auth", MODE_PRIVATE).getString("type", "student").equals("student")) {
            toolbar.setTitle("班级列表");
            toolbar.setNavigationIcon(R.drawable.ic_group_white_24dp);
            linearLayout.removeAllViews();
            TextView view = (TextView) View.inflate(this, R.layout.simple_text, null);
            view.setText("暂无权限查看");
            linearLayout.addView(view);
            return;
        }
        linearLayout.removeAllViews();
        SingletonRequestQueue.getInstance(this).getRequestQueue().cancelAll("tag");
        String url = basicUrl + "group";
        HttpBasicJsonArrayRequest request = new HttpBasicJsonArrayRequest(url, getSharedPreferences("auth", MODE_PRIVATE),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Group> groupList = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                groupList.add(new Group(obj.getString("name"), obj.getInt("id")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        GroupAdapter groupAdapter = new GroupAdapter(groupList, BottomMenuActivity.this);
                        listView.setAdapter(groupAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int groupId = (int) parent.getItemIdAtPosition(position);
                                String groupName = ((Group) parent.getItemAtPosition(position)).getGroupName();
                                Intent intent = new Intent(BottomMenuActivity.this, GroupStudentsActivity.class);
                                Bundle bd = new Bundle();
                                bd.putInt("groupId", groupId);
                                bd.putString("groupName", groupName);
                                intent.putExtras(bd);
                                startActivity(intent);
                            }
                        });
                        linearLayout.addView(listView);
                        toolbar.setTitle("班级列表");
                        toolbar.setNavigationIcon(R.drawable.ic_group_white_24dp);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "HTTP验证失败 请重新登陆", Toast.LENGTH_LONG).show();
                startActivity(new Intent(BottomMenuActivity.this, LoginActivity.class));
                finish();
            }
        });
        request.setTag("tag");
        SingletonRequestQueue.getInstance(this).getRequestQueue().add(request);
    }

    protected void createCourse() {
        linearLayout.removeAllViews();
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course(1, "软件工程与计算"));
        courseList.add(new Course(2, "移动互联网开发"));
        courseList.add(new Course(3, "数据结构与算法"));
        CourseAdapter adapter = new CourseAdapter(courseList, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int courseId = (int) parent.getItemIdAtPosition(position);
                String courseName = ((Course) parent.getItemAtPosition(position)).getCourseName();
                Intent intent = new Intent(BottomMenuActivity.this, CourseAssignmentsActivity.class);
                Bundle bd = new Bundle();
                bd.putInt("courseId", courseId);
                bd.putString("courseName", courseName);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
        linearLayout.addView(listView);
        toolbar.setTitle("课程列表");
        toolbar.setNavigationIcon(R.drawable.ic_dashboard_white_24dp);
    }

    protected void createAccount() {
        View account_info = View.inflate(this, R.layout.account_info, null);
        linearLayout.removeAllViews();
        linearLayout.addView(account_info);
        final ImageView avatar = (ImageView) findViewById(R.id.teacher_avatar);
        TextView name = (TextView) findViewById(R.id.teacher_name);
        TextView email = (TextView) findViewById(R.id.teacher_email);
        TextView type = (TextView) findViewById(R.id.teacher_type);
        TextView s_attr = (TextView) findViewById(R.id.student_attr);
        SharedPreferences sp = getSharedPreferences("auth", MODE_PRIVATE);
        String avatarUrl = sp.getString("avatar", "null");
        if (avatarUrl.length() < 8)
            avatarUrl = "http://www.gravatar.com/avatar/93db73548f2321b573ca56d8e885ae74?s=800&d=identicon";
        SingletonRequestQueue.getInstance(this).getRequestQueue().cancelAll("tag");
        ImageRequest imageRequest = new ImageRequest(avatarUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                avatar.setImageBitmap(response);
            }
        }, 400, 400, Bitmap.Config.ALPHA_8, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "图片加载失败", Toast.LENGTH_SHORT).show();
            }
        });
        imageRequest.setTag("tag");
        SingletonRequestQueue.getInstance(this).addToRequestQueue(imageRequest);
        name.setText(sp.getString("name", "null")+" - "+sp.getString("username","null"));
        email.setText(sp.getString("email", "null"));
        String typeStr = "";
        String typeAttr = sp.getString("type", "student");
        typeStr = typeStr + (sp.getString("gender", "male").equals("male")?"male":"female") + " - "
                + (sp.getString("type", "student").equals("teacher")?"teacher":"student");
        if (typeAttr.equals("teacher")) {
            typeStr += (" - " + (sp.getString("authority", "false").equals("true") ? "authority" : "non authority"));
            s_attr.setVisibility(View.INVISIBLE);
        }
        else {
            s_attr.setText(sp.getInt("gitId", 1) + " - " + sp.getString("number", "null"));
        }
        type.setText(typeStr);
        toolbar.setTitle("个人账户信息");
        toolbar.setNavigationIcon(R.drawable.ic_face_white_24dp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationItemId = R.id.navigation_group;
        setContentView(R.layout.activity_bottom_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar_teacher);
        toolbar.inflateMenu(R.menu.top_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sign_out:
                        SharedPreferences sp = getSharedPreferences("auth", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(BottomMenuActivity.this, LoginActivity.class));
                        finish();
                        return true;
                }
                return false;
            }
        });
//        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.group_list);
        linearLayout = (LinearLayout) findViewById(R.id.content);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        defaultInit();
    }

    protected void defaultInit() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_group);
        createGroup();
    }

}


