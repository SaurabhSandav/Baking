package com.redridgeapps.baking.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<VDB extends ViewDataBinding> extends DaggerAppCompatActivity {

    private VDB binding;

    @LayoutRes
    protected abstract int provideLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, provideLayout());
    }

    protected VDB getBinding() {
        return binding;
    }
}
