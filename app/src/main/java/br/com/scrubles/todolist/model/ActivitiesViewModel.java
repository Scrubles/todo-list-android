package br.com.scrubles.todolist.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import br.com.scrubles.todolist.AppDatabase;

public class ActivitiesViewModel extends AndroidViewModel {

    protected LiveData<List<Activity>> activities;
    protected AppDatabase database;

    public ActivitiesViewModel(Application application) {
        super(application);
        database = AppDatabase.getDatabase(application);
        setActivities();
    }

    public LiveData<List<Activity>> getActivities() {
        return activities;
    }

    protected void setActivities() {
        activities = database.activityDAO().getAll();
    }
}
