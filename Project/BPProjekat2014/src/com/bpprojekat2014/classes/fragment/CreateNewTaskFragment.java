package com.bpprojekat2014.classes.fragment;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.bpprojekat2014.R;
import com.bpprojekat2014.classes.Aktivnost;
import com.bpprojekat2014.classes.Project;
import com.bpprojekat2014.classes.Projects;
import com.bpprojekat2014.classes.User;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class CreateNewTaskFragment extends Fragment{
	public static Projects projects;
	public static User user;
	private int indeksProjekta = 0;
	private int indeksAktivnosti = 0;
	private static View rootView;
	
	private EditText name;
	private EditText status;
	private EditText description;
	private EditText duration;
	private EditText deadline;
	private EditText assignedTo; 
	
	private Button buttonCreateTask;
	
	
	public CreateNewTaskFragment(){}
	
	public CreateNewTaskFragment(Projects proj, User user, int indeksProjekta, int indeksAktivnosti){
		projects = proj;
		this.user = user;
		this.indeksProjekta = indeksProjekta;
		this.indeksAktivnosti = indeksAktivnosti;
	}
	private void kreirajTask(String name, String description, Float status, int duration, String deadline, int assignedTo)
	{
			DefaultHttpClient httpClient = new DefaultHttpClient();
		    try {
		    	int activity_id = projects.getProjects().get(indeksProjekta).getAktivnosti().get(indeksAktivnosti).getActivity_id();
		    	JSONObject task = new JSONObject();		
		    	task.put("name", name);
		    	task.put("activity_id", activity_id);
		    	task.put("description", description);
		    	task.put("status", status);
		    	task.put("duration", duration);
		    	task.put("deadline", deadline);
		    	task.put("user_id", assignedTo);
		    	JSONObject jsonobj = new JSONObject();
		    	jsonobj.put("task", task);
		    	jsonobj.put("username", user.getUsername());
		    	jsonobj.put("key", user.getKey());
		    	
		    	HttpPost httppostreq = new HttpPost("http://projectmng.herokuapp.com/tasks.json");

		        StringEntity params =new StringEntity(jsonobj.toString());
		        httppostreq.setEntity(params);
		        params.setContentType("application/json;charset=UTF-8");
		        params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));

		        HttpResponse response = httpClient.execute(httppostreq);
		        
		        Toast toast = Toast.makeText(this.getActivity(), "Task je uspjesno kreiran!", Toast.LENGTH_SHORT);
		    	toast.show();
		    	
		    }catch (Exception ex) {
		    	Toast toast = Toast.makeText(this.getActivity(), "Došlo je do greske, task nije kreiran!", Toast.LENGTH_SHORT);
		    	toast.show();
		        ex.printStackTrace();
		    } finally {
		        httpClient.getConnectionManager().shutdown();
		    }
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        rootView = inflater.inflate(R.layout.fragment_create_new_task, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
       
    	name=(EditText) rootView.findViewById(R.id.name);
    	status=(EditText) rootView.findViewById(R.id.status);
    	description=(EditText) rootView.findViewById(R.id.description);
    	duration=(EditText) rootView.findViewById(R.id.duration);
    	deadline=(EditText) rootView.findViewById(R.id.deadline);
    	assignedTo=(EditText) rootView.findViewById(R.id.assignedTo);
       
    	buttonCreateTask= (Button) rootView.findViewById(R.id.buttonCreateTask);
    	buttonCreateTask.setOnClickListener(new OnClickListener()
        
	    {
		   @Override
	             public void onClick(View v)
	             {
			   		kreirajTask(name.getText().toString(),description.getText().toString(),
			   				Float.parseFloat(status.getText().toString()), Integer.parseInt(duration.getText().toString()),
			   				deadline.getText().toString(),Integer.parseInt(assignedTo.getText().toString()));
			   		// Neka ga ponovo ucita NE VALJA OVO DOLE!
			   		Fragment fragment = new MyTasksFragment(projects, user, indeksProjekta,indeksAktivnosti);
				    FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment).commit();
			   		
	             } 
	    }); 
            
        return rootView;
    }
   
}
