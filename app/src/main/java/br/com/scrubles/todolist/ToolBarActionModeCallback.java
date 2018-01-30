package br.com.scrubles.todolist;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import br.com.scrubles.todolist.model.Activity;

public class ToolBarActionModeCallback implements ActionMode.Callback {

    private ListFragment fragment;


    public ToolBarActionModeCallback(ListFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        menu.findItem(R.id.action_checkUncheck).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.findItem(R.id.action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        List<Activity> activities = fragment.getSelectedActivities();
        switch (item.getItemId()) {
            case R.id.action_checkUncheck:
                for(Activity activity : activities)
                    activity.setDone(!activity.getDone());
                new DatabaseAsyncTask<Activity>() {
                    @Override
                    public void handle(Activity... activities) {
                        AppDatabase.getInstance().activityDAO().update(activities);
                    }
                }.execute(toArray(activities));
                mode.finish();
                break;
            case R.id.action_delete:
                new DatabaseAsyncTask<Activity>() {
                    @Override
                    public void handle(Activity... activities) {
                        AppDatabase.getInstance().activityDAO().delete(activities);
                    }
                }.execute(toArray(activities));
                mode.finish();
                break;
        }
        return false;
    }

    private Activity[] toArray(List<Activity> activities) {
        return activities.toArray(new Activity[activities.size()]);
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        fragment.endActionMode();
    }
}
