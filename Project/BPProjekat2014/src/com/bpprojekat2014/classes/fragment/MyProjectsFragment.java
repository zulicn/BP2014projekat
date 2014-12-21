package com.bpprojekat2014.classes.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bpprojekat2014.MainPage;
import com.bpprojekat2014.R;
import com.bpprojekat2014.classes.Projects;
import com.bpprojekat2014.classes.User;

public class MyProjectsFragment extends Fragment{
	public static Projects projects;
	public static User user;
	private Button btnCreateProject;
	public MyProjectsFragment(){}
	public MyProjectsFragment(Projects proj, User usr){
		projects = proj;
		user = usr;
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_my_projects, container, false);
        // Kreiranje novog projekta (okruglil "+" button)
        btnCreateProject= (Button) rootView.findViewById(R.id.crNewPr);
        btnCreateProject.setOnClickListener(new OnClickListener()
	    {
		   @Override
	             public void onClick(View v)
	             {
				    Fragment fragment = new CreateNewProjectFragment(projects, user);
				    FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment).commit();
	             } 
	    }); 
        RelativeLayout relativeLayout = (RelativeLayout)rootView.findViewById(R.id.projects_layout);
        int number=projects.countProjects();
        
        int prijasnji=0, prijasnje_dugme=0;
        for (int i = 0; i < number; i++)
         {
            TextView tv = new TextView(getActivity());
            tv.setText(projects.getProjects().get(i).getName());
            tv.setTextColor(Color.parseColor("#22CB83")); 
             tv.setTextSize(25);
            // tv.setBackgroundColor(Color.WHITE);
            int curTextViewId = prijasnji + 1;
            tv.setId(curTextViewId);
            
            Button btn = new Button(getActivity());
            btn.setText("");
            btn.setTextColor(Color.parseColor("#0494D2"));
            btn.setTextSize(10);
            btn.setBackgroundResource(R.drawable.round_button_blue);
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.penci, 0, 0, 0);
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
