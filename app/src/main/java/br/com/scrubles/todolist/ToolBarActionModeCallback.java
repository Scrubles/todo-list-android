package br.com.scrubles.todolist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
    public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
        final List<Activity> activities = fragment.getSelectedActivities();
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
                final AlertDialog dialog = new AlertDialog.Builder(fragment.getContext())
                    .setTitle(R.string.delete_confirmation)
                    .setPositiveButton(R.string.delete_confirmation_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            new DatabaseAsyncTask<Activity>() {
                                @Override
                                public void handle(Activity... activities) {
                                    AppDatabase.getInstance().activityDAO().delete(activities);
                                }
                            }.execute(toArray(activities));
                            mode.finish();
                        }
                    })
                    .setNegativeButton(R.string.delete_confirmation_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).create();
                dialog.show();
                break;
        }
        return false;
    }

    private Activity[] toArray(List<Activity> activities) {
        return activities.toArray(new Activity[activities.size()]);
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        fragment.onEndActionMode();
    }
}
