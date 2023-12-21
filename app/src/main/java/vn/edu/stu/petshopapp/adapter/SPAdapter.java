package vn.edu.stu.petshopapp.adapter;

import android.app.Activity;
import android.content.Intent;
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

import java.util.List;

import vn.edu.stu.petshopapp.AboutActivity;
import vn.edu.stu.petshopapp.DetailProductActivity;
import vn.edu.stu.petshopapp.R;
import vn.edu.stu.petshopapp.model.SP;

public class SPAdapter extends ArrayAdapter<SP> {
    Activity context;
    int resource;
    List<SP> ls;
    public SPAdapter(@NonNull Activity context, int resource, @NonNull List<SP> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.ls=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource, null);

        TextView txtMa = view.findViewById(R.id.tvNameRowProducts);
        TextView txtLoai = view.findViewById(R.id.tvTypeRowProducts);
        ImageView imgProduct = view.findViewById(R.id.imgRowProduct);

        SP sp = this.ls.get(position);

        // Chuyển đổi ID và Loai thành String trước khi setText
        txtMa.setText(String.valueOf(sp.getID()));
        txtLoai.setText(sp.getLoai());

        // Kiểm tra sp.Anh có giá trị trước khi giải mã
        if (sp.Anh != null && sp.Anh.length > 0) {
            Bitmap bmAVT = BitmapFactory.decodeByteArray(sp.Anh, 0, sp.Anh.length);
            imgProduct.setImageBitmap(bmAVT);
        }
        Button btnView = view.findViewById(R.id.btnViewRowProduct);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("ID", sp.ID);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
