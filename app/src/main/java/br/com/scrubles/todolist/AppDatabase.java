package br.com.scrubles.todolist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import br.com.scrubles.todolist.dao.ActivityDAO;
import br.com.scrubles.todolist.model.Activity;

@Database(entities = {Activity.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract ActivityDAO activityDAO();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "todo-list_db").build();
        return INSTANCE;
    }

    public static AppDatabase getInstance() {
        return INSTANCE;
    }
}
