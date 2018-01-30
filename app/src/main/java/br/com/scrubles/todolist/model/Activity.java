package br.com.scrubles.todolist.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Calendar;
import java.util.UUID;

@Entity
public class Activity {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private Calendar createdDate;
    private boolean done;

    public Activity() {

    }

    @Ignore
    public Activity(String title) {
        setId(UUID.randomUUID().toString().replaceAll("-", ""));
        setTitle(title);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public boolean getDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        return id.equals(activity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
