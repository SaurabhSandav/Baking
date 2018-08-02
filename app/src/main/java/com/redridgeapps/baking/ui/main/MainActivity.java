package com.redridgeapps.baking.ui.main;

import android.os.Bundle;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.ActivityMainBinding;
import com.redridgeapps.baking.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_main;
    }
}
