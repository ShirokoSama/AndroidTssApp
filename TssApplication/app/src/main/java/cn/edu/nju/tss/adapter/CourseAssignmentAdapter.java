package cn.edu.nju.tss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.nju.tss.R;
import cn.edu.nju.tss.adapter.Item.CourseAssignment;

/**
 * Created by Srf on 2017/6/16
 */

public class CourseAssignmentAdapter extends BaseAdapter {

    private List<CourseAssignment> list;
    private Context context;

    public CourseAssignmentAdapter(List<CourseAssignment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CourseAssignment getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CourseAssignmentAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.course_assignment_item, parent, false);
            holder = new CourseAssignmentAdapter.ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.assignment_title);
            holder.description = (TextView) convertView.findViewById(R.id.assignment_description);
            holder.startAt = (TextView) convertView.findViewById(R.id.assignment_start);
            holder.endAt = (TextView) convertView.findViewById(R.id.assignment_end);
            holder.status = (TextView) convertView.findViewById(R.id.assignment_status);
            convertView.setTag(holder);
        }
        else {
            holder = (CourseAssignmentAdapter.ViewHolder) convertView.getTag();
        }
        holder.title.setText(list.get(position).getTitle());
        holder.description.setText(list.get(position).getDescription());
        String start = "开始时间： " + list.get(position).getStartAt();
        holder.startAt.setText(start);
        String end = "结束时间： " + list.get(position).getEndAt();
        holder.endAt.setText(end);
        String status = "考试状态： " + list.get(position).getStatus();
        holder.status.setText(status);
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView description;
        TextView startAt;
        TextView endAt;
        TextView status;
    }

}
