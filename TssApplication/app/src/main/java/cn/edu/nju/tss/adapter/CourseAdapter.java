package cn.edu.nju.tss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.nju.tss.R;
import cn.edu.nju.tss.adapter.Item.Course;

/**
 * Created by Srf on 2017/6/8
 */

public class CourseAdapter extends BaseAdapter {

    private List<Course> courseList;
    private Context context;

    public CourseAdapter(List<Course> courses, Context context) {
        this.courseList = courses;
        this.context = context;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return courseList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CourseAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);
            holder = new CourseAdapter.ViewHolder();
            holder.groupName = (TextView) convertView.findViewById(R.id.group_name);
            convertView.setTag(holder);
        }
        else {
            holder = (CourseAdapter.ViewHolder) convertView.getTag();
        }
        holder.groupName.setText(courseList.get(position).getCourseName());
        return convertView;
    }

    private static class ViewHolder {
        TextView groupName;
    }

}
