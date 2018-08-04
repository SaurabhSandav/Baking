package com.redridgeapps.baking.ui.detail.step_detail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redridgeapps.baking.R;
import com.redridgeapps.baking.databinding.FragmentStepDetailBinding;
import com.redridgeapps.baking.model.Step;
import com.redridgeapps.baking.ui.base.BaseFragment;

public class StepDetailFragment extends BaseFragment<FragmentStepDetailBinding> {

    private static final String ARG_STEP = "step";

    private Step step;

    public static StepDetailFragment newInstance(Step step) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP, step);

        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            step = getArguments().getParcelable(ARG_STEP);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getBinding().setStep(step);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_step_detail;
    }
}
