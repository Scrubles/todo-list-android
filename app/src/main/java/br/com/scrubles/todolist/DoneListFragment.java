package br.com.scrubles.todolist;

import android.arch.lifecycle.ViewModelProviders;

import br.com.scrubles.todolist.model.ActivitiesDoneViewModel;

public class DoneListFragment extends ListFragment {

    @Override
    public int getTitleId() {
        return R.string.todo_list_done;
    }

    protected void setActivitiesModelView() {
        activitiesViewModel = ViewModelProviders.of(this).get(ActivitiesDoneViewModel.class);
    }
}
