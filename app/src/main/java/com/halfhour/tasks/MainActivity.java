package com.halfhour.tasks;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.halfhour.tasks.fragment.TaskListFragment;
import com.rey.material.app.ToolbarManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(value = R.layout.activity_main)
public class MainActivity extends ActionBarActivity implements ToolbarManager.OnToolbarGroupChangedListener {
    @ViewById
    Toolbar main_toolbar;

    @ViewById
    FrameLayout container;

    @ViewById
    DrawerLayout main_dl;
    private ToolbarManager mToolbarManager;

    @AfterViews
    void init() {


        mToolbarManager = new ToolbarManager(this, main_toolbar, 0, R.style.ToolbarRippleStyle, R.anim.abc_fade_in, R.anim.abc_fade_out);
        mToolbarManager.setNavigationManager(new ToolbarManager.BaseNavigationManager(R.style.NavigationDrawerDrawable, this, main_toolbar, main_dl) {
            @Override
            public void onNavigationClick() {
                if (mToolbarManager.getCurrentGroup() != 0)
                    mToolbarManager.setCurrentGroup(0);
                else
                    main_dl.openDrawer(Gravity.START);
            }

            @Override
            public boolean isBackState() {
                return super.isBackState() || mToolbarManager.getCurrentGroup() != 0;
            }

            @Override
            protected boolean shouldSyncDrawerSlidingProgress() {
                return super.shouldSyncDrawerSlidingProgress() && mToolbarManager.getCurrentGroup() == 0;
            }

        });
        mToolbarManager.registerOnToolbarGroupChangedListener(this);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("tasks");
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container,new TaskListFragment(), "tasks").commit();
        }
    }

    @Override
    public void onToolbarGroupChanged(int i, int i1) {
        mToolbarManager.notifyNavigationStateChanged();
    }
}
