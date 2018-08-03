package com.redridgeapps.baking.ui.detail;

import android.os.Bundle;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.ActivityDetailBinding;
import com.redridgeapps.baking.ui.base.BaseActivity;

public class DetailActivity extends BaseActivity<ActivityDetailBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_detail;
    }
}
