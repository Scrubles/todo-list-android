package br.com.scrubles.todolist;

import android.arch.lifecycle.ViewModelProviders;

import br.com.scrubles.todolist.model.ActivitiesTodoViewModel;

public class TodoListFragment extends ListFragment {

    protected void setActivitiesModelView() {
        activitiesViewModel = ViewModelProviders.of(this).get(ActivitiesTodoViewModel.class);
    }
}
