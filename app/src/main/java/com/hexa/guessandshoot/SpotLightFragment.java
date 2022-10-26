package com.hexa.guessandshoot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SpotLightFragment extends Fragment {

    public SpotLightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//
//        Target target = new Target.Builder()
//                .setAnchor(100f, 100f)
//                .setOverlay(getView())
//                .setOnTargetListener(new OnTargetListener() {
//                    @Override
//                    public void onStarted() {
//
//                    }
//
//                    @Override
//                    public void onEnded() {
//
//                    }
//                })
//                .build();
//
//        Spotlight builder = new Spotlight.Builder(getActivity())
//                .setTargets(target)
//                .setBackgroundColor(R.color.background)
//                .setDuration(1000L)
//                .setAnimation(new DecelerateInterpolator(2f))
//                .setContainer(container)
//                .setOnSpotlightListener(new OnSpotlightListener() {
//                    @Override
//                    public void onStarted() {
//
//                    }
//
//                    @Override
//                    public void onEnded() {
//
//                    }
//                }).build();
//
//        builder.start();
        View view =inflater.inflate(R.layout.fragment_spot_light, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}