package com.bpprojekat2014.classes;

import com.bpprojekat2014.R;

import android.os.Bundle;  
import android.support.v4.app.Fragment;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;

public class NewsFragment extends Fragment {
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
              Bundle savedInstanceState) {  
         // TODO Auto-generated method stub  
         return inflater.inflate(R.layout.news_layout, container,false);  
    } 
}

