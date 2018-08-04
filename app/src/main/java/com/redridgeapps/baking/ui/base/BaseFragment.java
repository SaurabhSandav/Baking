package com.redridgeapps.baking.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<VDB extends ViewDataBinding> extends DaggerFragment {

    private VDB binding;

    @LayoutRes
    protected abstract int provideLayout();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, provideLayout(), container, false);
        return binding.getRoot();
    }

    protected VDB getBinding() {
        return binding;
    }
}
