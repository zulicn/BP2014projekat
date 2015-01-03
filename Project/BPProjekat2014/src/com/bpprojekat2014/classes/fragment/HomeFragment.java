package com.bpprojekat2014.classes.fragment;

import com.bpprojekat2014.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
 
import android.app.Activity;
import android.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.ImageView;

import android.widget.Toast;



  
public class HomeFragment extends Fragment{
     
    
    private Button uploadButton, selectImageButton;
    private ImageView image;
    private Bitmap bitmap;
    private String picturePath;
    
    private static final int PICK_IMAGE = 1;
    
    private static Integer id_task;
	public HomeFragment(){}
	public HomeFragment(Integer id){
	id_task = id;
	}
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
       
     // find the views
        image = (ImageView) rootView.findViewById(R.id.uploadImage);
        uploadButton = (Button) rootView.findViewById(R.id.uploadButton);
        
     // on click select an image
        selectImageButton = (Button)rootView. findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
          selectImageFromGallery();

         }
        });
        
     // when uploadButton is clicked
        uploadButton.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
        	 doNewUpload(picturePath);
         }
        });
        
        return rootView;
    }
    
    /**
     * Opens dialog picker, so the user can select image from the gallery. The
     * result is returned in the method <code>onActivityResult()</code>
     */
    public void selectImageFromGallery() {
     Intent intent = new Intent();
     intent.setType("image/*");
     intent.setAction(Intent.ACTION_GET_CONTENT);
     startActivityForResult(Intent.createChooser(intent, "Select Picture"),
       PICK_IMAGE);
    }

    /**
     * Retrives the result returned from selecting image, by invoking the method
    
     */
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);

     if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK
       && null != data) {
      Uri selectedImage = data.getData();
      String[] filePathColumn = { MediaStore.Images.Media.DATA };

      Cursor cursor = getActivity().getContentResolver().query(selectedImage,
        filePathColumn, null, null, null);
      cursor.moveToFirst();

      int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
       picturePath = cursor.getString(columnIndex);
      cursor.close();

     decodeFile(picturePath);
     

     }
    }

    /**
     * The method decodes the image file to avoid out of memory issues. Sets the
     * selected image in to the ImageView.
     * 
     * @param filePath
     */
    public void decodeFile(String filePath) {
     // Decode image size
     BitmapFactory.Options o = new BitmapFactory.Options();
     o.inJustDecodeBounds = true;
     BitmapFactory.decodeFile(filePath, o);

     // The new size we want to scale to
     final int REQUIRED_SIZE = 1024;

     // Find the correct scale value. It should be the power of 2.
     int width_tmp = o.outWidth, height_tmp = o.outHeight;
     int scale = 1;
     while (true) {
      if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
       break;
      width_tmp /= 2;
      height_tmp /= 2;
      scale *= 2;
     }

     // Decode with inSampleSize
     BitmapFactory.Options o2 = new BitmapFactory.Options();
     o2.inSampleSize = scale;
     bitmap = BitmapFactory.decodeFile(filePath, o2);

     image.setImageBitmap(bitmap);
    }

    /**
     * The class connects with server and uploads the photo
     * 
     * 
     */
   
    
    
    public void doNewUpload(String filePath){
    	SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
	    String username=pref.getString("username",null);
		String key=pref.getString("key",null);
		String task=Integer.toString(id_task);
		String pom = "https://projectmng.herokuapp.com/tasks/%1$s/uploads.json";
		String url = String.format(pom,task);

        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            FileBody filebodyImage = new FileBody(new File(filePath),"image/jpeg");
            Long length=filebodyImage.getContentLength();
            System.out.println(length);
            StringBody title = new StringBody(task);
            System.out.println(title);

            StringBody key2 = new StringBody(
                    key);
            StringBody username2 = new StringBody(
                    username);
            MultipartEntity multipart=new MultipartEntity();
            multipart.addPart("file_data",filebodyImage);
            multipart.addPart("task_id",title);
            multipart.addPart("key", key2);
            multipart.addPart("username", username2);
            httpPost.setEntity(multipart);
            httpPost.setEntity(multipart);
            httpPost.setEntity(multipart);
            System.out.println("Executing Request "+httpPost.getRequestLine());
            HttpResponse httpResponse=httpClient.execute(httpPost);
            HttpEntity httpEntity=httpResponse.getEntity();
            System.out.println( httpResponse.getStatusLine( ) );
            if (httpEntity != null) 
            {
              System.out.println( EntityUtils.toString( httpEntity ) );
            } 
            // end if
            if (httpEntity != null) {
                httpEntity.consumeContent( );
              } // end if

              httpClient.getConnectionManager( ).shutdown( );
              Toast.makeText(getActivity(),"Image uploaded",Toast.LENGTH_SHORT).show();
              System.out.println("Uplaod file Executed");
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    
    }

    
     
}

