package com.bpprojekat2014.classes.fragment;

import com.bpprojekat2014.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateNewProjectFragment extends Fragment{

	public CreateNewProjectFragment(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_create_new_project, container, false);
          
        return rootView;
    }
}
