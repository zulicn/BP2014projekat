package com.bpprojekat2014.classes.fragment;

import com.bpprojekat2014.R;
import com.bpprojekat2014.classes.User;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MyProfileFragment extends Fragment{
	public static User user;
	public MyProfileFragment(User usr)
	{
		user = usr;
	}
	public MyProfileFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        TextView usrData =(TextView) rootView.findViewById(R.id.userData);
        usrData.setText("You are logged in as \"" + user.getUsername() + "\".");
        return rootView;
    }
	
}
