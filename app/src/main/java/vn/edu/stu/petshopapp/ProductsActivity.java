package vn.edu.stu.petshopapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import vn.edu.stu.petshopapp.Database.Database;
import vn.edu.stu.petshopapp.model.Loai;

public class ProductsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View navInfo;
    final String DATABASE_NAME = "data.sqlite";
    final int REQUEST_CHOOSE_PHOTO = 321;

    SQLiteDatabase database;
    Button btnChooseImg, btnAddPro;
    EditText etNamePro, etDescription, etPrice;
    Spinner spinner;
    ArrayList<Loai> loais;
    String loai;
    ImageView imgProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        addControls();
        addEvents();
        spinner();
    }

    private void spinner() {
        loais = new ArrayList<>();
        database = Database.initDatabase(this, DATABASE_NAME);

        Cursor cursor = database.rawQuery("SELECT * FROM Categories", null);
        while (cursor.moveToNext()) {
            int ID = cursor.getInt(0);
            String loai = cursor.getString(1);
            Loai type = new Loai(ID, loai);
            loais.add(type);
        }

        ArrayAdapter<Loai> adapterLoai = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, loais);
        adapterLoai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterLoai);

        cursor.close();
        database.close();
    }

    private void addControls() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar);
        navInfo = findViewById(R.id.navInfo);
        btnChooseImg = findViewById(R.id.btnChooseImg);
        btnAddPro = findViewById(R.id.btnAddPro);
        spinner = findViewById(R.id.spinner);
        loais = new ArrayList<>();
        etNamePro = findViewById(R.id.etNamePro);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        imgProduct = findViewById(R.id.imgProduct);
    }

    private void addEvents() {
        xuLyNav();
        btnAddPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    private void insert() {
        String ten = etNamePro.getText().toString();
        String mota = etDescription.getText().toString();
        String gia = etPrice.getText().toString();

        Loai selectedLoai = (Loai) spinner.getSelectedItem();
        if (selectedLoai != null) {
            loai = selectedLoai.getLoai();

            byte[] anh = getByteArrayFromImageView(imgProduct);

            ContentValues contentValues = new ContentValues();
            contentValues.put("Name", ten);
            contentValues.put("Description", mota);
            contentValues.put("Img", anh);
            contentValues.put("Type", loai);
            contentValues.put("Price", gia);

            try {
                database = Database.initDatabase(this, DATABASE_NAME);
                database.insert("Products", null, contentValues);
                Toast.makeText(getApplicationContext(), "Product added successfully", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error adding product", Toast.LENGTH_LONG).show();
            } finally {
                if (database != null) {
                    database.close();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please select a category", Toast.LENGTH_LONG).show();
        }
    }

    private byte[] getByteArrayFromImageView(ImageView imgv) {
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void xuLyNav() {
        setSupportActionBar(toolbar);
        setTitle("Add Product");
        navigationView.bringToFront();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                Uri imgUri = data.getData();
                try {
                    InputStream is = getContentResolver().openInputStream(imgUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgProduct.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.navInfo) {
            Intent intent = new Intent(ProductsActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navHome){
            Intent intent = new Intent(ProductsActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navCate) {
            Intent intent = new Intent(ProductsActivity.this, CategroriesActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(ProductsActivity.this, ProductsActivity.class);
            startActivity(intent);
        }

        return true;
    }
}
