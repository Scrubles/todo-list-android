package br.com.scrubles.todolist.model;

import android.app.Application;

public class ActivitiesTodoViewModel extends ActivitiesViewModel {

    public ActivitiesTodoViewModel(Application application) {
        super(application);
    }

    protected void setActivities() {
        activities = database.activityDAO().getAllByDone(false);
    }
}
