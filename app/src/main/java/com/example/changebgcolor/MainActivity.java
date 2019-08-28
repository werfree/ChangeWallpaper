package com.example.changebgcolor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.squareup.picasso.Picasso;



public class MainActivity extends Activity {
    Bitmap bitmap;
    Display display;
    int w, h;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        w = point.x;
        h = point.y;
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= 21) {
            //do your check here
            /*if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                setImage();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }*/
            //progressBar.setIndeterminateDrawable(getResources().getDrawable(R.color.hola_orange_dark));

        }
        else{
            //progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.hola_orange_dark), PorterDuff.Mode.SRC_IN);
        }
        setImage();
    }

    private void setImage() {
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        final String pathURL = "https://source.unsplash.com/random/" + w + "X" +h;
        Thread thread=null;
        if(checkConnection()){
            try{
                progressBar.setVisibility(View.VISIBLE);

                thread = new Thread(){
                    @Override
                    public void run() {
                        try{
                            //progressBar.setVisibility(View.VISIBLE);
                            bitmap = Picasso.get().load(pathURL).get();
                            //wallpaperManager.clear();
                            wallpaperManager.setBitmap(bitmap.createScaledBitmap(bitmap,w-75,h-10,true));
                            //wallpaperManager.setBitmap(bitmap);
                            progressBar.setVisibility(View.INVISIBLE);



                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        finally {
                            stopapp();
                        }
                    }
                };
                thread.start();
                Toast.makeText(MainActivity.this,"~By SAyantan", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(this,"Bitmap= "+e.getMessage(),Toast.LENGTH_LONG).show();
                finish();
            }

        }
        else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_LONG).show();
        }

    }

    private void stopapp() {

        if(android.os.Build.VERSION.SDK_INT >= 21)
        {
            finishAndRemoveTask();
        }
        else
        {
            finish();
        }
    }

    private boolean checkConnection() {
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        return;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(android.os.Build.VERSION.SDK_INT >= 21)
        {
            finishAndRemoveTask();
        }
        else
        {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(android.os.Build.VERSION.SDK_INT >= 21)
        {
            finishAndRemoveTask();
        }
        else
        {
            finish();
        }
    }
}


/*android:centerColor="#007DD6"
        android:endColor="#007DD6"
        android:startColor="#007DD6"*/