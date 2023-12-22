package vn.edu.stu.petshopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import vn.edu.stu.petshopapp.Database.Database;

public class AddCategroriesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View navInfo;
    SQLiteDatabase database;
    EditText etNameCate;
    Button btnAddCate;
    String DATABASE_NAME = "data.sqlite";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categrories);
        addControls();
        addEvents();
    }

    private void addControls() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar);
        navInfo = findViewById(R.id.navInfo);
        btnAddCate = findViewById(R.id.btnAddCate);
        etNameCate = findViewById(R.id.etNameCate);
    }

    private void addEvents() {
        xuLyNav();
        btnAddCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });

    }

    private void insert() {
        String tenLoai = etNameCate.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", tenLoai);
        try {
            database = Database.initDatabase(this, DATABASE_NAME);
            database.insert("Categories", null, contentValues);
            Toast.makeText(getApplicationContext(), "Categories added successfully", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error adding product", Toast.LENGTH_LONG).show();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    private void xuLyNav() {
        setSupportActionBar(toolbar);
        setTitle("Add Categories");
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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.navInfo) {
            Intent intent = new Intent(AddCategroriesActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navHome){
            Intent intent = new Intent(AddCategroriesActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navCate) {
            Intent intent = new Intent(AddCategroriesActivity.this, AddCategroriesActivity.class);
            startActivity(intent);
        }else if(itemId == R.id.navShowCate){
            Intent intent = new Intent(AddCategroriesActivity.this, ShowCategroriesActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(AddCategroriesActivity.this, AddProductsActivity.class);
            startActivity(intent);
        }

        return true;
    }
}