package br.com.scrubles.todolist;

import android.os.AsyncTask;

public abstract class DatabaseAsyncTask<T> extends AsyncTask<T, Void, Void> {

    @Override
    protected Void doInBackground(final T... params) {
        handle(params);
        return null;
    }

    abstract void handle(T... params);
}
