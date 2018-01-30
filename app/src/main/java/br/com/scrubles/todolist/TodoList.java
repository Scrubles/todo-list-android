package br.com.scrubles.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.scrubles.todolist.model.Activity;

public class TodoList extends Fragment implements TodoForm.NoticeDialogListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.todo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.nav_todo_list);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());
        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.container);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoForm todoForm = TodoForm.getInstance(new Activity(""));
                todoForm.setListener(TodoList.this);
                todoForm.show(getChildFragmentManager(), "Activity");
            }
        });
    }

    @Override
    public void onDialogPositiveClick(Activity activity) {
        new DatabaseAsyncTask<Activity>() {
            @Override
            public void handle(Activity... activities) {
                AppDatabase.getInstance().activityDAO().insert(activities);
            }
        }.execute(activity);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new TodoListFragment();
                case 1:
                    return new DoneListFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle (int position) {
            switch(position) {
                case 0:
                    return getString(R.string.todo_list_todo);
                case 1:
                    return getString(R.string.todo_list_done);
            }

            return null;
        }
    }
}
