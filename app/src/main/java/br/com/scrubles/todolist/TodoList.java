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

import java.util.Arrays;
import java.util.List;

import br.com.scrubles.todolist.model.Activity;

public class TodoList extends Fragment implements TodoForm.NoticeDialogListener {

    private List<ListFragment> listFragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.todo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.nav_todo_list);

        listFragments = Arrays.asList(new TodoListFragment(), new DoneListFragment());

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());
        TabLayout tabs = view.findViewById(R.id.tabs);
        ViewPager viewPager = view.findViewById(R.id.container);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(ListFragment listFragment: listFragments)
                    listFragment.finishActionMode();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
            return listFragments.get(position);
        }

        @Override
        public int getCount() {
            return listFragments.size();
        }

        @Override
        public CharSequence getPageTitle (int position) {
            return getString(listFragments.get(position).getTitleId());
        }
    }
}
