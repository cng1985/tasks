package com.halfhour.tasks;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.halfhour.tasks.event.TaskEvent;
import com.halfhour.tasks.model.Task;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_task_add)
public class TaskAddActivity extends AppCompatActivity {

    @ViewById
    EditText name;

    @ViewById
    EditText contents;

    @Extra
    Long id;
    Task task = null;

    @AfterViews
    void init(){
        if (id==null){
            task = new Task();
        }else{
            task=Task.load(Task.class,id);
            name.setText(""+task.name);
            contents.setText(""+task.contents);
        }
    }
    public void onBackPressed() {
        task.name = name.getText().toString();
        task.contents = contents.getText().toString();
        if (task.name != null && task.name.length() > 0) {
            TaskEvent event=new TaskEvent();

            if (task.getId()==null){
                event.setType(1);
            }else{
                event.setType(2);
            }
            event.setTask(task);
            task.save();
            EventBus.getDefault().post(event);


        }
        super.onBackPressed();
    }

    @OptionsItem(android.R.id.home)
    void back() {
        onBackPressed();
    }

}
