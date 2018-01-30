package br.com.scrubles.todolist.model;

import android.app.Application;

public class ActivitiesDoneViewModel extends ActivitiesViewModel {

    public ActivitiesDoneViewModel(Application application) {
        super(application);
    }

    protected void setActivities() {
        activities = database.activityDAO().getAllByDone(true);
    }
}
