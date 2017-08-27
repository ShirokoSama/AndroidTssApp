package cn.edu.nju.tss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.nju.tss.R;
import cn.edu.nju.tss.adapter.Item.StudentScore;

/**
 * Created by Srf on 2017/6/18
 */

public class StudentScoreAdapter extends BaseAdapter {

    private List<StudentScore> list;
    private Context context;

    public StudentScoreAdapter(List<StudentScore> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public StudentScore getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.student_score_item, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.student_score_name);
            holder.score = (TextView) convertView.findViewById(R.id.student_score_score);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        StudentScore item = list.get(position);
        holder.name.setText(item.getName() + " " + item.getNumber());
        String scoreStr = "分数： " + item.getScore();
        holder.score.setText(scoreStr);
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView score;
    }

}
