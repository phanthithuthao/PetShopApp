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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import vn.edu.stu.petshopapp.Database.Database;


public class DetailProductActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvAName;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    View navInfo;
    SQLiteDatabase database;
    String DB_NAME = "data.sqlite";

    ImageView imgProduct;
    TextView tvShowDetailID,tvShowDetailNP,tvShowDescription,tvShowType,tvShowPrice;
    Button btnAddCart,btnAddNewProduct,btnUpdateProduct;
    int id = -1;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        addControls();
        addEvents();
        hienThiAccountName();

    }

    private void addControls() {
        tvAName = findViewById(R.id.tvAName);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar);
        navInfo = findViewById(R.id.navInfo);
        imgProduct = findViewById(R.id.imgProduct);
        tvShowDetailID = findViewById(R.id.tvShowDetailID);
        tvShowDetailNP = findViewById(R.id.tvShowDetailNP);
        tvShowDescription = findViewById(R.id.tvShowDescription);
        tvShowType = findViewById(R.id.tvShowType);
        tvShowPrice = findViewById(R.id.tvShowPrice);
        btnAddCart = findViewById(R.id.btnAddCart);
        btnAddNewProduct = findViewById(R.id.btnAddNewProduct);
        btnUpdateProduct = findViewById(R.id.btnUpdateProduct);
    }

    private void addEvents() {
        xuLyNav();
        xuLyChiTietSanPham();
        btnAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProductActivity.this, AddProductsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void xuLyChiTietSanPham() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", -1);
        if (id == -1) {
            Toast.makeText(DetailProductActivity.this, "Loi", Toast.LENGTH_SHORT).show();
        }

        database = Database.initDatabase(this, DB_NAME);
        cursor = database.rawQuery("SELECT ID, Name, Description, Img, Type, Price FROM Products WHERE ID = ?", new String[]{id + ""});
        cursor.moveToFirst();

        int ID = cursor.getInt(0);
        String Ten = cursor.getString(1);
        String Mota = cursor.getString(2);
        byte[] Anh = cursor.getBlob(3);
        String Loai = cursor.getString(4);
        Integer Gia = cursor.getInt(5);

        Bitmap bitmap = BitmapFactory.decodeByteArray(Anh, 0, Anh.length);

        imgProduct.setImageBitmap(bitmap);
        tvShowDetailID.setText(ID+"");
        tvShowDetailNP.setText(Ten);
        tvShowDescription.setText(Mota);
        tvShowType.setText(Loai);
        tvShowPrice.setText(Gia+"");

        cursor.close();
        database.close();
    }

    private void xuLyNav() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        setTitle("Detail Product");
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

    private void hienThiAccountName() {
        String userName = getIntent().getStringExtra("username");
        // Hiển thị userName trong TextView
        if (userName != null) {
            tvAName.setText("Hello, " + userName + "!");
        }
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
            Intent intent = new Intent(DetailProductActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navHome) {
            Intent intent = new Intent(DetailProductActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.navCate) {
            Intent intent = new Intent(DetailProductActivity.this, AddCategroriesActivity.class);
            startActivity(intent);
        } else if(itemId == R.id.navShowCate){
            Intent intent = new Intent(DetailProductActivity.this, ShowCategroriesActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(DetailProductActivity.this, AddProductsActivity.class);
            startActivity(intent);
        }

        return true;
    }
}