package com.bpprojekat2014.classes.fragment;

import com.bpprojekat2014.R;
import com.bpprojekat2014.classes.Projects;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyDocumentsFragment extends Fragment {
	
	public static Projects projects;
	public MyDocumentsFragment(){}
	public MyDocumentsFragment(Projects proj){
	projects = proj;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_my_documents, container, false);
        RelativeLayout relativeLayout = (RelativeLayout)rootView.findViewById(R.id.projects_layout);
        int number=projects.countProjects();
        
        int prijasnji=0, prijasnje_dugme=0;
        for (int i = 0; i < number; i++)
         {
            TextView tv = new TextView(getActivity());
            tv.setText(projects.getProjects().get(i).getName());
            tv.setTextColor(Color.parseColor("#22CB83")); 
             tv.setTextSize(25);
            int curTextViewId = prijasnji + 1;
            tv.setId(curTextViewId);
            
            Button btn = new Button(getActivity());
            btn.setText("");
            btn.setTextColor(Color.parseColor("#0494D2"));
            btn.setTextSize(10);
            btn.setBackgroundResource(R.drawable.round_button_blue);
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.download, 0, 0, 0);
            int curButtonId = prijasnje_dugme + 1;
            btn.setId(curButtonId);
 
            final RelativeLayout.LayoutParams params = 
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 
                                                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            final RelativeLayout.LayoutParams params2 = 
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                                                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            params.addRule(RelativeLayout.BELOW, prijasnji);
            params.setMargins(20, 20, 0, 0);
            tv.setLayoutParams(params);
            params2.addRule(RelativeLayout.BELOW, prijasnje_dugme);
            params2.addRule(RelativeLayout.RIGHT_OF, curTextViewId);
            params2.setMargins(300, 20, 100, 0);
            btn.setLayoutParams(params2);
           
            prijasnji = curTextViewId;
            prijasnje_dugme = curButtonId;
            try{
                relativeLayout.addView(tv, params);
                relativeLayout.addView(btn, params2);
         }catch(Exception e){
                e.printStackTrace();
         }
        }
        
        
        return rootView;
    }

}
