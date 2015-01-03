package com.bpprojekat2014.classes.fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.bpprojekat2014.R;
import com.bpprojekat2014.classes.Document;
import com.bpprojekat2014.classes.Downloader;
import com.bpprojekat2014.classes.Projects;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MyDocumentsFragment extends Fragment {
	


	private static ArrayList<Document> documents;
	private static File folder;
	  
	  
	  
	  
	public MyDocumentsFragment(){}
	public MyDocumentsFragment(ArrayList<Document> docs){
	documents = docs;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	 
    
  
        View rootView = inflater.inflate(R.layout.fragment_my_documents, container, false);
        RelativeLayout relativeLayout = (RelativeLayout)rootView.findViewById(R.id.projects_layout);
    	
        int number=documents.size();
      
        Button[] btn = new Button[number];
        
        int prijasnji=0, prijasnje_dugme=0;
        for (int i = 0; i < number; i++)
         {
            TextView tv = new TextView(getActivity());
            String title = documents.get(i).getFilename();
            if(title.length()>14)title = title.substring(0, Math.min(title.length(), 14))+"...";
            tv.setText(title);
            tv.setTextColor(Color.parseColor("#22CB83")); 
             tv.setTextSize(25);
            int curTextViewId = prijasnji + 1;
            tv.setId(curTextViewId);
            
            btn[i] = new Button(getActivity());
            btn[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.download, 0, 0, 0);
            int curButtonId = prijasnje_dugme + 1;
            btn[i].setId(curButtonId);
            btn[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            	    String username=pref.getString("username",null);
            		String key=pref.getString("key",null);
            		String pom ="https://projectmng.herokuapp.com/tasks/3/uploads/"+v.getId()+"?username=%1$s&key=%2$s";
            		String url = String.format(pom,username,key);
            	
                	Uri uri = Uri.parse(url);
                	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                	startActivity(intent);
            		
            		
                }
              });
         
          
 
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
            btn[i].setLayoutParams(params2);
           
            prijasnji = curTextViewId;
            prijasnje_dugme = curButtonId;
            try{
                relativeLayout.addView(tv, params);
                relativeLayout.addView(btn[i], params2);
         }catch(Exception e){
                e.printStackTrace();
         }
        }
        
    	
        
        return rootView;
        
    }
    
    public void tryDownloadingPDF(String url){
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "pdf");
        folder.mkdir();
     
        //Download the file
        Downloader.DownloadFile(url);
         
        //Show the downloaded file
        showPdf();
    }
    
    public void showPdf()
    {
        File file = new File(Environment.getExternalStorageDirectory()+"/pdf/Read.pdf");
        PackageManager packageManager = getActivity().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
    }
    
}
