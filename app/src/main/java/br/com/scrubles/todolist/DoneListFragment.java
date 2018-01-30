package br.com.scrubles.todolist;

import android.arch.lifecycle.ViewModelProviders;

import br.com.scrubles.todolist.model.ActivitiesDoneViewModel;

public class DoneListFragment extends ListFragment {

    protected void setActivitiesModelView() {
        activitiesViewModel = ViewModelProviders.of(this).get(ActivitiesDoneViewModel.class);
    }
}
