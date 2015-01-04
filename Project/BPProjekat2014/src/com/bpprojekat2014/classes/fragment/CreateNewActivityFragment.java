package com.bpprojekat2014.classes.fragment;

import org.apache.http.HttpResponse;
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
public class CreateNewActivityFragment extends Fragment{
	public static Projects projects;
	public static User user;
	private int indeksProjekta = 0;
	private static View rootView;
	private EditText nameActivity;
	private EditText descriptionActivity;
	private EditText durationActivity;
	private Button buttonDoneActivity;
	
	
	public CreateNewActivityFragment(){}
	
	public CreateNewActivityFragment(Projects proj, User user, int indeksProjekta ){
		projects = proj;
		this.user = user;
		this.indeksProjekta = indeksProjekta;
	}
	private void kreirajAktivnost(String name, String desc, int duration, boolean finished)
	{
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
		    try {
		    	JSONObject aktivnost = new JSONObject();		
		    	aktivnost.put("name", name);
		    	aktivnost.put("description", desc);
		    	aktivnost.put("duration", duration);
		    	aktivnost.put("finished", finished);

		    	JSONObject jsonobj = new JSONObject();
		    	jsonobj.put("activity", aktivnost);
		    	jsonobj.put("username", user.getUsername());
		    	jsonobj.put("key", user.getKey());
		    	
		    	HttpPost httppostreq = new HttpPost("https://projectmng.herokuapp.com/projects/"+
		    	projects.getProjects().get(indeksProjekta).getProject_id() + "/activities.json");
		    	
		        StringEntity params =new StringEntity(jsonobj.toString());
		        httppostreq.setEntity(params);
		        params.setContentType("application/json;charset=UTF-8");
		        params.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
		        // TBD nek vrati json kreirane aktivnosti!
		        // Kad vrati dodati tu aktivnost gdje treba i pozvati ponovo MyActivitiesFragment!
		        HttpResponse response = httpClient.execute(httppostreq);
		        
		        Toast toast = Toast.makeText(this.getActivity(), "Aktivnost je uspjesno kreirana!", Toast.LENGTH_SHORT);
		    	toast.show();
		    	
		    }catch (Exception ex) {
		    	Toast toast = Toast.makeText(this.getActivity(), "Došlo je do greske, aktivnost nije kreirana!", Toast.LENGTH_SHORT);
		    	toast.show();
		        ex.printStackTrace();
		    } finally {
		        httpClient.getConnectionManager().shutdown();
		    }
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        rootView = inflater.inflate(R.layout.fragment_create_new_activity, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
        nameActivity=(EditText) rootView.findViewById(R.id.nameActivity);
        descriptionActivity=(EditText) rootView.findViewById(R.id.descriptionActivity);
        durationActivity=(EditText) rootView.findViewById(R.id.durationActivity);
        // TBD da li je finished?
        //budget=(EditText) rootView.findViewById(R.id.budget);
     
        buttonDoneActivity= (Button) rootView.findViewById(R.id.buttonDoneActivity);
        buttonDoneActivity.setOnClickListener(new OnClickListener()
        
	    {
		   @Override
	             public void onClick(View v)
	             {
			   		kreirajAktivnost(nameActivity.getText().toString(),descriptionActivity.getText().toString(),
			   				Integer.parseInt(durationActivity.getText().toString()), false /*,finishedActivity.getText().toString() */);
			   		// Neka ga ponovo ucita
			   		Fragment fragment = new MyActivitiesFragment(projects, user, indeksProjekta);
				    FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment).commit();
			   		
	             } 
	    }); 
            
        return rootView;
    }
   
}
