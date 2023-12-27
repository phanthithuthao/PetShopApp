    package vn.edu.stu.petshopapp;

    import static android.provider.SyncStateContract.Helpers.update;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.ActionBarDrawerToggle;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.core.view.GravityCompat;
    import androidx.drawerlayout.widget.DrawerLayout;

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
    import android.widget.TextView;
    import android.widget.Toast;

    import com.google.android.material.navigation.NavigationView;

    import java.io.ByteArrayOutputStream;
    import java.io.FileNotFoundException;
    import java.io.InputStream;
    import java.util.ArrayList;

    import vn.edu.stu.petshopapp.Database.Database;
    import vn.edu.stu.petshopapp.adapter.SPAdapter;
    import vn.edu.stu.petshopapp.model.Loai;

    public class UpdateProductActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

        DrawerLayout drawerLayout;
        NavigationView navigationView;
        Toolbar toolbar;
        View navInfo;
        final String DATABASE_NAME = "data.sqlite";
        final int REQUEST_CHOOSE_PHOTO = 321;

        SQLiteDatabase database;
        Button btnChooseImg, btnUpdatePro;
        EditText etNamePro, etDescription, etPrice;
        TextView tvShowID;
        Spinner spinner;
        ArrayList<Loai> loais;
        ImageView imgProduct;
        int id = -1;
        Cursor cursor;
        String loai;
        ArrayAdapter<Loai> adapterLoai;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_product);
            addControls();
            spinner();
            addEvents();


        }

        private void addControls() {
            drawerLayout = findViewById(R.id.drawerLayout);
            navigationView = findViewById(R.id.navView);
            toolbar = findViewById(R.id.toolbar);
            navInfo = findViewById(R.id.navInfo);
            btnChooseImg = findViewById(R.id.btnChooseImg);
            btnUpdatePro = findViewById(R.id.btnUpdatePro);
            spinner = findViewById(R.id.spinner);
            loais = new ArrayList<>();
            etNamePro = findViewById(R.id.etNamePro);
            etDescription = findViewById(R.id.etDescription);
            etPrice = findViewById(R.id.etPrice);
            imgProduct = findViewById(R.id.imgProduct);
            tvShowID = findViewById(R.id.tvShowID);
        }

        private void addEvents() {
            xuLyNav();
            xuLyHienThi();
            btnChooseImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle image selection here
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
                }
            });
            btnUpdatePro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   xuLyUpdate();
                    Intent intent = new Intent(UpdateProductActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            });
        }

        private void xuLyUpdate() {
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
                    int rowsAffected = database.update("Products", contentValues, "ID=?", new String[]{String.valueOf(id)});

                    if (rowsAffected > 0) {
                        Toast.makeText(getApplicationContext(), "Product updated successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error updating product", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error updating product", Toast.LENGTH_LONG).show();
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

            adapterLoai = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, loais);
            adapterLoai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapterLoai);


            cursor.close();
            database.close();
        }
        private void xuLyHienThi() {
            Intent intent = getIntent();
            id = intent.getIntExtra("ID", -1);
            if (id == -1) {
                Toast.makeText(UpdateProductActivity.this, "Loi", Toast.LENGTH_SHORT).show();
            }

            database = Database.initDatabase(this, DATABASE_NAME);
            cursor = database.rawQuery("SELECT ID, Name, Description, Img, Type, Price FROM Products WHERE ID = ?", new String[]{id + ""});
            cursor.moveToFirst();

            int ID = cursor.getInt(0);
            String Ten = cursor.getString(1);
            String Mota = cursor.getString(2);
            byte[] Anh = cursor.getBlob(3);
            int typeColumnIndex = cursor.getColumnIndex("Type");

            Integer Gia = cursor.getInt(5);
            Bitmap bitmap = BitmapFactory.decodeByteArray(Anh, 0, Anh.length);
            imgProduct.setImageBitmap(bitmap);
            tvShowID.setText(ID+"");
            etNamePro.setText(Ten);
            etDescription.setText(Mota);
            etPrice.setText(Gia+"");
            if (typeColumnIndex != -1) {
                String Loai = cursor.getString(typeColumnIndex);

                // Find the position of the Loai object in the list
                int selectedPosition = -1;
                for (int i = 0; i < loais.size(); i++) {
                    if (loais.get(i).getLoai().equals(Loai)) {
                        selectedPosition = i;
                        break;
                    }
                }

                if (selectedPosition != -1) {
                    // Set the correct selection in the spinner
                    spinner.setSelection(selectedPosition);
                }
            }
            cursor.close();
            database.close();
        }

        private void xuLyNav() {
            setSupportActionBar(toolbar);
            setTitle("Update Product");
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
                Intent intent = new Intent(UpdateProductActivity.this, AboutActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.navHome){
                Intent intent = new Intent(UpdateProductActivity.this, HomeActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.navCate) {
                Intent intent = new Intent(UpdateProductActivity.this, AddCategroriesActivity.class);
                startActivity(intent);
            } else if(itemId == R.id.navShowCate){
                Intent intent = new Intent(UpdateProductActivity.this, ShowCategroriesActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(UpdateProductActivity.this, AddProductsActivity.class);
                startActivity(intent);
            }

            return true;
        }

    }