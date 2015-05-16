package com.halfhour.tasks;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.halfhour.tasks.model.Task;

import de.greenrobot.event.EventBus;


public class TaskAddActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = (EditText) findViewById(R.id.name);
        contents = (EditText) findViewById(R.id.contents);
    }

    EditText name;
    EditText contents;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = super.onOptionsItemSelected(item);
        if (handled) {
            return true;
        }
        int itemId_ = item.getItemId();
        if (itemId_ == android.R.id.home) {
            back();
            return true;
        }

        return false;
    }
    public void onBackPressed() {
        Task task = new Task();
        task.name = name.getText().toString();
        task.contents = contents.getText().toString();
        task.save();
        EventBus.getDefault().post(task);
        super.onBackPressed();
    }
    void back() {
        onBackPressed();


    }

}
