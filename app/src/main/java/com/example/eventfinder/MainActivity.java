package com.example.eventfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;

    private static final String PERMISSION_REQUEST_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int PERMISSION_RED_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





////// LOCATION ACCESS PERMISSION

        if (ContextCompat.checkSelfPermission(this, PERMISSION_REQUEST_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted
            System.out.println("Location permission granted");
        }
        else if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_REQUEST_LOCATION)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This app requires location permission")
                    .setTitle("Permission required")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{PERMISSION_REQUEST_LOCATION}, PERMISSION_RED_CODE);
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("cancel", ((dialogInterface, i) -> dialogInterface.dismiss()));
            builder.show();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION_REQUEST_LOCATION}, PERMISSION_RED_CODE);
        }
////// LOCATION ACCESS PERMISSION


//////   FOR TABS "SEARCH" AND "FAVORITES"

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

//        FOR TABS "SEARCH" AND "FAVORITES"
    }


////// LOCATION ACCESS PERMISSION

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_RED_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("Loc permission granted");
            }
            else if (!ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISSION_REQUEST_LOCATION)) {
                System.out.println("Grant the location permission");
            }
        }
    }

/*  LOCATION ACCESS PERMISSION */



}