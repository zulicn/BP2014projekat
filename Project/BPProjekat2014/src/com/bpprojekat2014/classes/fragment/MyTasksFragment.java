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

public class MyTasksFragment extends Fragment{
	public static Projects projects;
	public static User user;
	private int indeksProjekta = 0;
	private int indeksAktivnosti = 0;
	private Button btnCreateTask;
	public MyTasksFragment(){}
	public MyTasksFragment(Projects proj, User usr, int indeksProjekta, int indeksAktivnosti){
		projects = proj;
		user = usr;
		this.indeksProjekta = indeksProjekta;
		this.indeksAktivnosti = indeksAktivnosti;
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_my_tasks, container, false);    
        RelativeLayout relativeLayout = (RelativeLayout)rootView.findViewById(R.id.tasks_layout);
        
        // Kreiranje nove aktivnosti (okruglil "+" button)
        btnCreateTask= (Button) rootView.findViewById(R.id.crNewTask);
        btnCreateTask.setOnClickListener(new OnClickListener()
	    {
		   @Override
	             public void onClick(View v)
	             {
			   		// Kreiranje taska
			   		Fragment fragment = new CreateNewTaskFragment(projects, user, indeksProjekta, indeksAktivnosti);
				    FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment).commit();
	             } 
	    }); 
        
        int number = projects.getProjects().get(indeksProjekta).getAktivnosti().get(indeksAktivnosti).countTasks();
        Button[] btn = new Button[number];
        TextView[] tv = new TextView[number];
        int prijasnji=0, prijasnje_dugme=0;
        for (int i = 0; i < number; i++)
         {
            tv[i] = new TextView(getActivity());
            tv[i].setText(projects.getProjects().get(indeksProjekta).getAktivnosti().get(indeksAktivnosti).getTaskovi().get(i).getName());
            tv[i].setTextColor(Color.parseColor("#22CB83")); 
            tv[i].setTextSize(25);
            int curTextViewId = prijasnji + 1;
            tv[i].setId(curTextViewId);
            
            btn[i] = new Button(getActivity());
            btn[i].setText("");
            btn[i].setTextColor(Color.parseColor("#0494D2"));
            btn[i].setTextSize(10);
            btn[i].setBackgroundResource(R.drawable.round_button_blue);
            btn[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.penci, 0, 0, 0);
            int curButtonId = prijasnje_dugme + 1;
            btn[i].setId(curButtonId);
            
            tv[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {       
                	// Promijeniti 
                	Fragment fragment = new EditViewTaskFragment(projects, user, indeksProjekta, indeksAktivnosti, v.getId()-1);
				    FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment).commit();                           		
                }
              });
            
            /* Button za kreiranje necega dalje ne treba jer nema sta dalje kreirati!
            btn[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {            	
                	Fragment fragment = new MyTasksFragment(projects, user, v.getId()-1);
				    FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment).commit();                           		
                }
              });
              */
            final RelativeLayout.LayoutParams params = 
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 
                                                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            final RelativeLayout.LayoutParams params2 = 
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                                                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            params.addRule(RelativeLayout.BELOW, prijasnji);
            params.setMargins(20, 20, 0, 0);
            tv[i].setLayoutParams(params);
            params2.addRule(RelativeLayout.BELOW, prijasnje_dugme);
            params2.addRule(RelativeLayout.RIGHT_OF, curTextViewId);
            params2.setMargins(300, 20, 100, 0);
            btn[i].setLayoutParams(params2);
           
            prijasnji = curTextViewId;
            prijasnje_dugme = curButtonId;
            try{
                relativeLayout.addView(tv[i], params);
                relativeLayout.addView(btn[i], params2);
         }catch(Exception e){
                e.printStackTrace();
         }
        }
        
        
        return rootView;
    }
	
    
}
