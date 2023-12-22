package vn.edu.stu.petshopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

public class AboutActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    private GoogleMap mMap;
    TextView tvSDT;
    Button btnCall;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    View navInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.banDo);
        mapFragment.getMapAsync(this);

        addControls();
        addEvents();
    }

    private void addControls() {
        btnCall = findViewById(R.id.btnCall);
        tvSDT = findViewById(R.id.tvSDT);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar);
        navInfo = findViewById(R.id.navInfo);
    }

    private void addEvents() {
        xuLyNav();
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    makePhoneCall();
                } else {
                    requestPermissions();
                }
            }
        });
    }
    private void xuLyNav() {
        setSupportActionBar(toolbar);
        setTitle("About Us");
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle =  new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }
    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                AboutActivity.this,
                Manifest.permission.CALL_PHONE
        )) {
            Toast.makeText(
                    AboutActivity.this,
                    "Vui lòng cấp quyền thủ công trong App Setting",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            ActivityCompat.requestPermissions(
                    AboutActivity.this,
                    new String[]{
                            Manifest.permission.CALL_PHONE
                    },
                    123
            );
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(
                        AboutActivity.this,
                        "Bạn đã từ chối cấp quyền gọi. Hủy thao tác.",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(
                AboutActivity.this,
                Manifest.permission.CALL_PHONE
        );
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void makePhoneCall() {
        String phoneNum = tvSDT.getText().toString();
        Intent callIntent = new Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:" + phoneNum)
        );
        startActivity(callIntent);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        LatLng stu = new LatLng(10.738176385767881, 106.67776772272026);
        mMap.addMarker(new MarkerOptions()
                .position(stu)
                .title("Marker in STU")
                .snippet("Đây la trường đại học STU")
        );

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(stu )      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.navInfo) {
            Intent intent = new Intent(AboutActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navHome){
            Intent intent = new Intent(AboutActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navCate) {
            Intent intent = new Intent(AboutActivity.this, AddCategroriesActivity.class);
            startActivity(intent);
        }else if(itemId == R.id.navShowCate){
            Intent intent = new Intent(AboutActivity.this, ShowCategroriesActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(AboutActivity.this, AddProductsActivity.class);
            startActivity(intent);
        }

        return true;
    }
}