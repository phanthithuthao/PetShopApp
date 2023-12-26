package vn.edu.stu.petshopapp.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

import vn.edu.stu.petshopapp.Database.Database;
import vn.edu.stu.petshopapp.DetailCategroriesActivity;
import vn.edu.stu.petshopapp.DetailProductActivity;
import vn.edu.stu.petshopapp.R;
import vn.edu.stu.petshopapp.ShowCategroriesActivity;
import vn.edu.stu.petshopapp.model.Loai;

public class LoaiAdapter extends ArrayAdapter<Loai> {
    Activity context;
    int resource;
    List<Loai> ls;

    public LoaiAdapter(@NonNull Activity context, int resource, @NonNull List<Loai> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.ls = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource, null);

        TextView txtTen = view.findViewById(R.id.tvNameRowCate);
        Loai loai = this.ls.get(position);
        txtTen.setText(loai.getLoai());

        return view;
    }
}
