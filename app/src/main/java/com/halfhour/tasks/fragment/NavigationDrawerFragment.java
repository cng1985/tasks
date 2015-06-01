package com.halfhour.tasks.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ada.v4.fragment.BaseFragment;
import com.halfhour.tasks.R;
import com.halfhour.tasks.SettingsActivity;

/**
 * Created by 年高 on 2015/5/16.
 */
public class NavigationDrawerFragment extends BaseFragment implements View.OnClickListener {
    @Override
    public int getlayout() {
        return R.layout.fragment_navigation_drawer;
    }

    @Override
    public void create(Bundle bundle) {

        findViewById(R.id.settings_row).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(),SettingsActivity.class);
        getActivity().startActivity(intent);
    }
}
