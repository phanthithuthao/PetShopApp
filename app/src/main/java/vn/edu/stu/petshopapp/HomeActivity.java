package vn.edu.stu.petshopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import vn.edu.stu.petshopapp.adapter.SPAdapter;
import vn.edu.stu.petshopapp.model.SP;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvAName;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View navInfo;
    String DB_NAME = "data.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    ListView lvSP;
    ArrayList<SP> ls;
    SPAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        copyDBFromAssets();
        addControls();
        addEvents();
        hienThiAccountName();
        getListFromDb();
    }

    private void getListFromDb() {
        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        Cursor cursor = null;

        try {
            cursor = database.query(
                    "Products",
                    new String[]{"ID", "Name", "Description", "Img", "Type", "Price"},
                    null, null, null, null, null);

            // Assuming adapter is an instance of a custom adapter that handles data changes
            // adapter.clear(); // Commented out this line

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String nameProduct = cursor.getString(1);
                String description = cursor.getString(2);
                byte[] img = cursor.getBlob(3);
                String type = cursor.getString(4);
                int price = cursor.getInt(5);
                adapter.add(new SP(id, nameProduct, description, img, type, price));
            }

            // Assuming adapter is an instance of a custom adapter that handles data changes
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your needs
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
    }

    private void copyDBFromAssets() {
        File dbFile = getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            File dbDir = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!dbDir.exists()) {
                dbDir.mkdir();
            }
            try {
                InputStream is = getAssets().open(DB_NAME);
                String outputFilePath = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DB_NAME;
                OutputStream os = new FileOutputStream(outputFilePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void hienThiAccountName() {
        String userName = getIntent().getStringExtra("username");
        // Hiển thị userName trong TextView
        if (userName != null) {
            tvAName.setText("Hello, " + userName + "!");
        }
    }

    private void addControls() {
        tvAName = findViewById(R.id.tvAName);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar);
        navInfo = findViewById(R.id.navInfo);
        lvSP = findViewById(R.id.lvSpHome);
        ls = new ArrayList<>();
        adapter = new SPAdapter(HomeActivity.this, R.layout.activity_row_products, ls);
        lvSP.setAdapter(adapter);

    }

    private void addEvents() {
        xuLyNav();
    }

    private void xuLyNav() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        setTitle("Home");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.navInfo) {
            Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navHome) {
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navCate) {
            Intent intent = new Intent(HomeActivity.this, CategroriesActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(HomeActivity.this, ProductsActivity.class);
            startActivity(intent);
        }

        return true;
    }

}