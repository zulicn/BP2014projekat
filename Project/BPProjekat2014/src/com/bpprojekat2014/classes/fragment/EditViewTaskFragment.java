package com.bpprojekat2014.classes.fragment;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.bpprojekat2014.R;
import com.bpprojekat2014.classes.Aktivnost;
import com.bpprojekat2014.classes.Project;
import com.bpprojekat2014.classes.Projects;
import com.bpprojekat2014.classes.Task;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
public class EditViewTaskFragment extends Fragment{
	public static Projects projects;
	public static User user;
	private static int indeksProjekta;
	private static int indeksAktivnosti;
	private static int indeksTaska;
	private static View rootView;
	
	private EditText name;
	private EditText status;
	private EditText description;
	private EditText duration;
	private EditText deadline;
	private EditText createdAt;
	private EditText assignedTo;
	
	private Button buttonUpdateTask;
	
	
	public EditViewTaskFragment(){}
	
	public EditViewTaskFragment(Projects proj, User user, int indeksProjekta, int indeksAktivnosti, int indeksTaska){
		projects = proj;
		this.user = user;
		this.indeksProjekta = indeksProjekta;
		this.indeksAktivnosti = indeksAktivnosti;
		this.indeksTaska = indeksTaska;
	}
	
	private void UpdateTask(String name, String description, Float status, int duration, String deadline, int assignedTo)
	{
			DefaultHttpClient httpClient = new DefaultHttpClient();
		    try {
		    	int task_id = projects.getProjects().get(indeksProjekta).getAktivnosti().get(indeksAktivnosti).getTaskovi().get(indeksTaska).getTask_id();
		    	int activityId = projects.getProjects().get(indeksProjekta).getAktivnosti().get(indeksAktivnosti).getActivity_id();
		    	
		    	JSONObject task = new JSONObject();		
		    	task.put("name", name);
		    	task.put("activity_id", activityId);
		    	task.put("description", description);
		    	task.put("status", status);
		    	task.put("duration", duration);
		    	task.put("deadline", deadline);
		    	task.put("user_id", assignedTo);
		    	JSONObject jsonobj = new JSONObject();
		    	jsonobj.put("task", task);
		    	jsonobj.put("username", user.getUsername());
		    	jsonobj.put("key", user.getKey());
		    	
		    	HttpPut httpputreq = new HttpPut("http://projectmng.herokuapp.com/tasks/"+
		    			task_id + ".json");

		        StringEntity params =new StringEntity(jsonobj.toString());
		        httpputreq.setEntity(params);
		        params.setContentType("application/json;charset=UTF-8");
		        params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));

		        HttpResponse response = httpClient.execute(httpputreq);
		        
		        Toast toast = Toast.makeText(this.getActivity(), "Task je uspjesno izmijenjen!", Toast.LENGTH_SHORT);
		    	toast.show();
		    	
		    }catch (Exception ex) {
		    	Toast toast = Toast.makeText(this.getActivity(), "Došlo je do greske, task nije izmijenjen!", Toast.LENGTH_SHORT);
		    	toast.show();
		        ex.printStackTrace();
		    } finally {
		        httpClient.getConnectionManager().shutdown();
		    }
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        rootView = inflater.inflate(R.layout.fragment_edit_view_task, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
        Task task= projects.getProjects().get(indeksProjekta).getAktivnosti().get(indeksAktivnosti).getTaskovi().get(indeksTaska);
        
        name=(EditText) rootView.findViewById(R.id.name);
        description=(EditText) rootView.findViewById(R.id.description);
        status=(EditText) rootView.findViewById(R.id.status);
        duration=(EditText) rootView.findViewById(R.id.duration);
        deadline=(EditText) rootView.findViewById(R.id.deadline);
        createdAt=(EditText) rootView.findViewById(R.id.createdAt);
        assignedTo=(EditText) rootView.findViewById(R.id.assignedTo);
        
        
        name.setText(task.getName());
        description.setText(task.getDescription());
        status.setText(String.valueOf(task.getStatus()));
        duration.setText(String.valueOf(task.getDuration()));
        deadline.setText(task.getDeadline());
        createdAt.setText(task.getCreated_at());
        createdAt.setEnabled(false);
        // TBD kad bude api za ovo staviti da se vidi userID
        assignedTo.setText(String.valueOf(task.getUser_id()));
        
        buttonUpdateTask= (Button) rootView.findViewById(R.id.buttonUpdateTask);
        buttonUpdateTask.setOnClickListener(new OnClickListener()
        
	    {
		   @Override
	             public void onClick(View v)
	             {
				   UpdateTask(name.getText().toString(),description.getText().toString(),
			   				Float.parseFloat(status.getText().toString()),Integer.parseInt(duration.getText().toString()),
			   				deadline.getText().toString(), Integer.parseInt(assignedTo.getText().toString()));
			   		 
			   		Fragment fragment = new MyTasksFragment(projects, user, indeksProjekta, indeksAktivnosti);
				    FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment).commit();
	             	
	             } 
	    }); 
            
        return rootView;
    }
   
}
