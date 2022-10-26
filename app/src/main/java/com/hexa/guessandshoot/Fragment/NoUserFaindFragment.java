package com.hexa.guessandshoot.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hexa.guessandshoot.Activity.Auth.LoginActivity;
import com.hexa.guessandshoot.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoUserFaindFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoUserFaindFragment extends Fragment {



    public NoUserFaindFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NoUserFaindFragment newInstance() {
        NoUserFaindFragment fragment = new NoUserFaindFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_no_user_faind, container, false);
        Button button = view.findViewById(R.id.sign_in);
        button.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(),LoginActivity.class) ;
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP ) ;
            startActivity(i);
        });
        return  view ;
    }
}