package br.com.scrubles.todolist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.scrubles.todolist.model.ActivitiesViewModel;
import br.com.scrubles.todolist.model.Activity;

public abstract class ListFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener, TodoForm.NoticeDialogListener {

    protected RecyclerView.Adapter adapter;
    protected ActivitiesViewModel activitiesViewModel;

    protected ActionMode actionMode;
    protected List<Activity> selectedActivities;
    protected List<View> selectedActivityViews;

    public List<Activity> getSelectedActivities() {
        return selectedActivities;
    }

    public abstract int getTitleId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter(new ArrayList<Activity>(), this, this);
        recyclerView.setAdapter(adapter);

        setActivitiesModelView();
        final Observer<List<Activity>> observer = new Observer<List<Activity>>() {
            @Override
            public void onChanged(@Nullable final List<Activity> activities) {
                ((ListAdapter) adapter).setActivities(activities);
                adapter.notifyDataSetChanged();
            }
        };
        activitiesViewModel.getActivities().observe(this, observer);
    }

    protected void setActivitiesModelView() {
        activitiesViewModel = ViewModelProviders.of(this).get(ActivitiesViewModel.class);
    }

    @Override
    public void onClick(View view) {
        Activity activity = (Activity) view.getTag();
        if(actionMode == null) {
            TodoForm todoForm = TodoForm.getInstance(activity);
            todoForm.setListener(this);
            todoForm.show(getChildFragmentManager(), "Activity");
        } else {
            if(selectedActivities == null) {
                selectedActivities = new ArrayList<>();
                selectedActivityViews = new ArrayList<>();
            }

            if(selectedActivities.contains(activity)) {
                selectedActivities.remove(activity);
                selectedActivityViews.remove(view);
                view.setBackground(null);
                if(selectedActivities.size() == 0)
                    actionMode.finish();
            } else {
                selectedActivities.add(activity);
                selectedActivityViews.add(view);
                view.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(actionMode == null)
            actionMode = ((MainActivity) getActivity()).startSupportActionMode(new ToolBarActionModeCallback(this));
        this.onClick(v);
        return true;
    }

    @Override
    public void onDialogPositiveClick(Activity activity) {
        new DatabaseAsyncTask<Activity>() {
            @Override
            public void handle(Activity... activities) {
                AppDatabase.getInstance().activityDAO().update(activities);
            }
        }.execute(activity);
    }

    public void finishActionMode() {
        if(actionMode != null)
            actionMode.finish();
    }

    public boolean onEndActionMode() {
        actionMode = null;
        selectedActivities = null;
        for(View view : selectedActivityViews)
            view.setBackground(null);
        selectedActivityViews = null;
        return true;
    }
}
