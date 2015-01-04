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
public class EditViewActivityFragment extends Fragment{
	public static Projects projects;
	public static User user;
	private static int indeksProjekta;
	private static int indeksAktivnosti;
	private static View rootView;
	
	private EditText nameActivity;
	private EditText descriptionActivity;
	private EditText durationActivity;
	private EditText createdAt;
	private CheckBox finishedActivity;
	private Button buttonUpdateActivity;
	
	
	public EditViewActivityFragment(){}
	
	public EditViewActivityFragment(Projects proj, User user, int indeksProjekta, int indeksAktivnosti){
		projects = proj;
		this.user = user;
		this.indeksProjekta = indeksProjekta;
		this.indeksAktivnosti = indeksAktivnosti;
	}
	// TBD Ako se radilo editovanje, promijeniti ovu metodu!
	
	private void UptateActivity(String name, String desc, int duration, boolean finished)
	{
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
		    try {
		    	int projectId = projects.getProjects().get(indeksProjekta).getProject_id();
		    	int activityId = projects.getProjects().get(indeksProjekta).getAktivnosti().get(indeksAktivnosti).getActivity_id();
		    	JSONObject aktivnost = new JSONObject();		
		    	aktivnost.put("project_id", projectId);
		    	aktivnost.put("name", name);
		    	aktivnost.put("description", desc);
		    	aktivnost.put("finished", finished);
		    	aktivnost.put("duration", duration);
		    	
		    	JSONObject jsonobj = new JSONObject();
		    	jsonobj.put("activity", aktivnost);
		    	jsonobj.put("username", user.getUsername());
		    	jsonobj.put("key", user.getKey());
		    	
		    	HttpPut httpputreq = new HttpPut("http://projectmng.herokuapp.com/projects/"+
		    	projectId + "/activities/" + activityId + ".json");
		        StringEntity params =new StringEntity(jsonobj.toString());
		        httpputreq.setEntity(params);
		        params.setContentType("application/json;charset=UTF-8");
		        params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
		        // TBD nek vrati json kreirane aktivnosti!
		        // Kad vrati dodati tu aktivnost gdje treba i pozvati ponovo MyActivitiesFragment!
		        HttpResponse response = httpClient.execute(httpputreq);
		        
		        Toast toast = Toast.makeText(this.getActivity(), "Aktivnost je uspjesno izmijenjena!", Toast.LENGTH_SHORT);
		    	toast.show();
		    	
		    }catch (Exception ex) {
		    	Toast toast = Toast.makeText(this.getActivity(), "Došlo je do greske, aktivnost nije izmijenjena!", Toast.LENGTH_SHORT);
		    	toast.show();
		        ex.printStackTrace();
		    } finally {
		        httpClient.getConnectionManager().shutdown();
		    }
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        rootView = inflater.inflate(R.layout.fragment_edit_view_activity, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
        Aktivnost aktivnost = projects.getProjects().get(indeksProjekta).getAktivnosti().get(indeksAktivnosti);
        
        nameActivity=(EditText) rootView.findViewById(R.id.nameActivity);
        descriptionActivity=(EditText) rootView.findViewById(R.id.descriptionActivity);
        durationActivity=(EditText) rootView.findViewById(R.id.durationActivity);
        createdAt=(EditText) rootView.findViewById(R.id.createdAt);
        finishedActivity = (CheckBox) rootView.findViewById(R.id.finishedActivity);
        
        nameActivity.setText(aktivnost.getName());
        descriptionActivity.setText(aktivnost.getDescription());
        durationActivity.setText(String.valueOf(aktivnost.getDuration()));
        createdAt.setText(aktivnost.getCreated_at());
        createdAt.setEnabled(false);
        
        if(aktivnost.isFinished()){
        	finishedActivity.setChecked(true);
        }
        else{finishedActivity.setChecked(false);}
        
        buttonUpdateActivity= (Button) rootView.findViewById(R.id.buttonUpdateActivity);
        buttonUpdateActivity.setOnClickListener(new OnClickListener()
        
	    {
		   @Override
	             public void onClick(View v)
	             {
			   		UptateActivity(nameActivity.getText().toString(),descriptionActivity.getText().toString(),
			   				Integer.parseInt(durationActivity.getText().toString()), finishedActivity.isChecked() );
	             } 
	    }); 
            
        return rootView;
    }
   
}
