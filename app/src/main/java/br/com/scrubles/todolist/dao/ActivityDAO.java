package br.com.scrubles.todolist.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.scrubles.todolist.model.Activity;

@Dao
public interface ActivityDAO {

    @Query("SELECT * FROM activity ORDER BY createdDate ASC")
    LiveData<List<Activity>> getAll();

    @Query("SELECT * FROM activity WHERE done = :done ORDER BY createdDate ASC")
    LiveData<List<Activity>> getAllByDone(Boolean done);

    @Insert
    void insert(Activity... activities);

    @Update
    void update(Activity... activities);

    @Delete
    void delete(Activity... activities);
}
