package vn.edu.stu.petshopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import vn.edu.stu.petshopapp.Database.Database;

public class UpdateCategoriesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View navInfo;
    SQLiteDatabase database;
    EditText etNameCate;
    Button btnUpdateCate,btnDelCate;
    String DATABASE_NAME = "data.sqlite";
    int id = -1;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_categories);
        addControls();
        addEvents();
    }

    private void addControls() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar);
        navInfo = findViewById(R.id.navInfo);
        btnUpdateCate = findViewById(R.id.btnUpdateCate);
        btnDelCate = findViewById(R.id.btnDelCate);
        etNameCate = findViewById(R.id.etNameCate);

    }

    private void addEvents() {
        xuLyNav();
        xuLyHienThi();
        btnUpdateCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XuLyUpdateCate();
            }
        });
        btnDelCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this category?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the "Yes" button, proceed with the deletion
                        XuLyDelCate();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the "No" button, do nothing
                    }
                });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void XuLyDelCate() {
        try {
            database = Database.initDatabase(this, DATABASE_NAME);
            int rowsAffected = database.delete("Categories", "ID=?", new String[]{String.valueOf(id)});

            if (rowsAffected > 0) {
                Toast.makeText(getApplicationContext(), "Category deleted successfully", Toast.LENGTH_LONG).show();
                finish(); // Close the activity after successful deletion
            } else {
                Toast.makeText(getApplicationContext(), "Error deleting category", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error deleting category", Toast.LENGTH_LONG).show();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        Intent intent = new Intent(UpdateCategoriesActivity.this,ShowCategroriesActivity.class);
        startActivity(intent);
    }

    private void xuLyHienThi() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", -1);
        if (id == -1) {
            Toast.makeText(UpdateCategoriesActivity.this, "Loi", Toast.LENGTH_SHORT).show();
        }
        database = Database.initDatabase(this, DATABASE_NAME);
        cursor = database.rawQuery("SELECT ID, Name FROM Categories WHERE ID = ?", new String[]{id + ""});
        cursor.moveToFirst();
        int ID = cursor.getInt(0);
        String Ten = cursor.getString(1);
        etNameCate.setText(Ten);
        cursor.close();
        database.close();
    }

    private void XuLyUpdateCate() {
        String ten = etNameCate.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", ten);
        try {
            database = Database.initDatabase(this, DATABASE_NAME);
            int rowsAffected = database.update("Categories", contentValues, "ID=?", new String[]{String.valueOf(id)});

            if (rowsAffected > 0) {
                Toast.makeText(getApplicationContext(), "Categories updated successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error updating Categories", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error updating Categories", Toast.LENGTH_LONG).show();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        Intent intent = new Intent(UpdateCategoriesActivity.this,ShowCategroriesActivity.class);
        startActivity(intent);

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
            Intent intent = new Intent(UpdateCategoriesActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navHome){
            Intent intent = new Intent(UpdateCategoriesActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navCate) {
            Intent intent = new Intent(UpdateCategoriesActivity.this, AddCategroriesActivity.class);
            startActivity(intent);
        }else if(itemId == R.id.navShowCate){
            Intent intent = new Intent(UpdateCategoriesActivity.this, ShowCategroriesActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(UpdateCategoriesActivity.this, AddProductsActivity.class);
            startActivity(intent);
        }

        return true;
    }
}