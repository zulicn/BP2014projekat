package com.bpprojekat2014.classes.fragment;

import com.bpprojekat2014.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment{
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		String strtext=getActivity().getIntent().getStringExtra("myString");
		TextView sessionTitle = (TextView) rootView.findViewById(R.id.session1);
	    sessionTitle.setText(strtext);
		
        
          
        return rootView;
    }
}
