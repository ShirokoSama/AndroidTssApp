package cn.edu.nju.tss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.nju.tss.R;
import cn.edu.nju.tss.adapter.Item.Question;

/**
 * Created by Srf on 2017/6/17
 */

public class QuestionAdapter extends BaseAdapter {

    private List<Question> list;
    private Context context;

    public QuestionAdapter(List<Question> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Question getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.question_item, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.question_title);
            holder.description = (TextView) convertView.findViewById(R.id.question_description);
            holder.difficulty = (TextView) convertView.findViewById(R.id.question_difficulty);
            holder.git = (TextView) convertView.findViewById(R.id.question_git);
            holder.creator = (TextView) convertView.findViewById(R.id.question_creator);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Question item = list.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        String difficultyStr = "难度系数： " + item.getDifficulty();
        holder.difficulty.setText(difficultyStr);
        String gitStr = "git地址： " + item.getGitUrl();
        holder.git.setText(gitStr);
        String creatorUrl = "创建者： " + item.getCreatorName() + " - " +item.getCreatorEmail();
        holder.creator.setText(creatorUrl);
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView description;
        TextView difficulty;
        TextView git;
        TextView creator;
    }

}
