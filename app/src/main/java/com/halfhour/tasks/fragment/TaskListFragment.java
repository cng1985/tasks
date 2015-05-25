package com.halfhour.tasks.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.ada.adapter.RenderAdapter;
import com.ada.v4.fragment.BaseFragment;
import com.halfhour.tasks.R;
import com.halfhour.tasks.TaskAddActivity;
import com.halfhour.tasks.TaskAddActivity_;
import com.halfhour.tasks.event.TaskEvent;
import com.halfhour.tasks.model.Task;
import com.halfhour.tasks.render.TaskRender;
import com.melnykov.fab.FloatingActionButton;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by 年高 on 2015/5/16.
 */
public class TaskListFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
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
        floatButton = (FloatingActionButton) findViewById(R.id.floatButton);
        floatButton.attachToListView(listView);
        floatButton.setOnClickListener(this);
        EventBus.getDefault().register(this);
        initRefreshLayout();
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(TaskEvent event) {

        if (event != null) {
            if (event.getType() == 1) {
                adapter.addFirst(event.getTask());
            } else if (event.getType() == 2) {
                adapter.clear();
                List<Task> tasks = new Select().from(Task.class).execute();
                adapter.addAll(tasks);
            }
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

        TaskAddActivity_.intent(this).start();
    }

    @Override
    public void onRefresh() {

        swipeRefreshLayout.setEnabled(false);
        disableRefreshing();
        adapter.clear();
        List<Task> tasks = new Select().from(Task.class).execute();
        adapter.addAll(tasks);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setEnabled(false);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Task task = adapter.getData(position);
        TaskAddActivity_.intent(this).id(task.getId()).start();


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Task task = adapter.getData(position);
        Dialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialogLight) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {

                super.onPositiveActionClicked(fragment);
                task.delete();
                adapter.remove(task);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.title("你确定要删除该项吗？")
                .positiveAction("确定")
                .negativeAction("取消");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);
        return true;
    }
}
