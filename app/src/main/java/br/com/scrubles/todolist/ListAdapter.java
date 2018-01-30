package br.com.scrubles.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.scrubles.todolist.model.Activity;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Activity> activities;
    private View.OnClickListener clickListener;
    private View.OnLongClickListener longClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(TextView view) {
            super(view);
            textView = view;
        }
    }

    public ListAdapter(List<Activity> activities, View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
        this.activities = activities;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder((TextView) view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Activity activity = activities.get(position);
        holder.textView.setText(activity.getTitle());
        holder.textView.setOnClickListener(clickListener);
        holder.textView.setOnLongClickListener(longClickListener);
        holder.textView.setTag(activity);
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
