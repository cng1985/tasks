package com.halfhour.tasks.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.ada.adapter.RenderAdapter;
import com.ada.v4.fragment.BaseFragment;
import com.halfhour.tasks.R;
import com.halfhour.tasks.TaskAddActivity;
import com.halfhour.tasks.model.Task;
import com.halfhour.tasks.render.TaskRender;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by 年高 on 2015/5/16.
 */
public class TaskListFragment extends BaseFragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {
    @Override
    public int getlayout() {
        return R.layout.fragment_task_list;
    }

    ListView listView;
    RenderAdapter<Task> adapter;
    FloatingActionButton floatButton;
    @Override
    public void create(Bundle bundle) {
        listView = (ListView) findViewById(R.id.listView);
        TaskRender render = new TaskRender(getActivity());
        adapter = new RenderAdapter<Task>(getActivity(), render);

        List<Task> tasks = new Select().from(Task.class).execute();
        adapter.addAll(tasks);
        listView.setAdapter(adapter);
        floatButton= (FloatingActionButton) findViewById(R.id.floatButton);
        floatButton.attachToListView(listView);
        floatButton.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    public void onEvent(Task event) {

        if (event != null) {
            adapter.addFirst(event);
            adapter.notifyDataSetChanged();
        }


    }

    private SwipeRefreshLayout swipeRefreshLayout;

    protected final void setRefreshing(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }

    protected final boolean isRefreshing() {
        return swipeRefreshLayout.isRefreshing();
    }

    protected final void initRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    protected final void disableRefreshing() {
        swipeRefreshLayout.setEnabled(false);
    }
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(), TaskAddActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        List<Task> tasks = new Select().from(Task.class).execute();
        adapter.addAll(tasks);
        adapter.notifyDataSetChanged();
        disableRefreshing();
    }
}
