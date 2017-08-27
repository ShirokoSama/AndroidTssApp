package cn.edu.nju.tss.activity;

import android.view.View;
import android.widget.TextView;

import cn.edu.nju.tss.R;

/**
 * Created by Srf on 2017/6/18
 */

public class StudentBottomMenuActivity extends BottomMenuActivity{

    @Override
    protected void createGroup() {
        toolbar.setTitle("班级列表");
        toolbar.setNavigationIcon(R.drawable.ic_group_white_24dp);
        linearLayout.removeAllViews();
        TextView view = (TextView) View.inflate(this, R.layout.simple_text, null);
        view.setText("暂无权限查看");
        linearLayout.addView(view);
    }

}
