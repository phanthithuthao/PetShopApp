package vn.edu.stu.petshopapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.edu.stu.petshopapp.R;
import vn.edu.stu.petshopapp.UpdateCategoriesActivity;
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
        txtTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateCategoriesActivity.class);
                intent.putExtra("ID",loai.getID());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
