package com.halfhour.tasks.render;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ada.adapter.RenderBuiler;
import com.halfhour.tasks.R;
import com.halfhour.tasks.model.Task;

/**
 * Created by 年高 on 2015/5/16.
 */
public class TaskRender extends RenderBuiler<Task> {
    public TaskRender(Context context) {
        super(context);
    }

    @Override
    public void render() {
        name.setText(""+getContent().name);
    }

    @Override
    protected void setUpView(View view) {
        name= (TextView) view.findViewById(R.id.name);
        imageView= (ImageView) view.findViewById(R.id.imageView);
    }

    @Override
    protected void hookListeners(View view) {

    }
  TextView name;
    ImageView imageView;
    @Override
    protected int getlayoutid() {
        return R.layout.render_task;
    }
}
