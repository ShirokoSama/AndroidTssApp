package cn.edu.nju.tss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.nju.tss.R;
import cn.edu.nju.tss.adapter.Item.GroupStudent;

/**
 * Created by Srf on 2017/6/16
 */

public class GroupStudentAdapter extends BaseAdapter{

    private List<GroupStudent> list;
    private Context context;

    public GroupStudentAdapter(List<GroupStudent> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public GroupStudent getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.group_student_item, parent, false);
            holder = new ViewHolder();
            holder.groupStudentName = (TextView) convertView.findViewById(R.id.group_student_name);
            holder.groupStudentEmail = (TextView) convertView.findViewById(R.id.group_student_email);
            holder.groupStudentGit = (TextView) convertView.findViewById(R.id.group_student_git);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.groupStudentName.setText(list.get(position).getName() + " - " + list.get(position).getUserName());
        holder.groupStudentEmail.setText(list.get(position).getGender() + " - " + list.get(position).getEmail());
        String gitText = "git : " + list.get(position).getGitUserName();
        holder.groupStudentGit.setText(gitText);
        return convertView;
    }

    private static class ViewHolder {
        TextView groupStudentName;
        TextView groupStudentEmail;
        TextView groupStudentGit;
    }

}
