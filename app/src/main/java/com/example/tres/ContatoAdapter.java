package com.example.tres;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContatoAdapter extends ArrayAdapter<Contato> {
    private static final String TAG ="XPTO";

    public class Handler {
        EditText editid;
        EditText edittitulo;
        EditText editnumber;
        Spinner spincategoria;
        ImageView imgfoto;
        Button btupdate, btinsert, btdelete, btcall;
    }

    Context ctx;
    ArrayList<Contato> filmes;
    int idresource;
    ISacaFotoListener lst;
    public  void setOnSacaFotoListener(ISacaFotoListener l){
        this.lst=l;
    }
    public ContatoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Contato> objects) {
        super(context, resource, objects);
        ctx = context;
        idresource = resource;
        filmes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        final Contato filme = filmes.get(position);
        Handler handler= new Handler();
        if(view ==null){
            view = LayoutInflater.from(ctx).inflate(idresource,parent,false);
            handler.editid=view.findViewById(R.id.edit_id_itemfilme);
            handler.edittitulo=view.findViewById(R.id.edit_titulo_itemfilme);
            handler.editnumber=view.findViewById(R.id.edit_number_itemfilme);
            handler.spincategoria=view.findViewById(R.id.spinner_categoria_itemfilme);
            handler.imgfoto=view.findViewById(R.id.img_foto_itemfilme);
//            handler.btinsert= view.findViewById(R.id.bt_insert_itemfilme);
            handler.btdelete= view.findViewById(R.id.bt_delete_itemfime);
            handler.btupdate= view.findViewById(R.id.bt_update_itemfilme);
            handler.btcall= view.findViewById(R.id.bt_call);
            view.setTag(handler);
        }else{

             handler=(Handler) view.getTag();
        }
        handler.editid.setText(String.valueOf(filme.id));
        handler.edittitulo.setText(filme.titulo);
        handler.editnumber.setText(filme.number);
        ArrayAdapter<CharSequence>adpt = ArrayAdapter.createFromResource(ctx,R.array.categorias,R.layout.support_simple_spinner_dropdown_item);
        adpt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        handler.spincategoria.setAdapter(adpt);
        handler.spincategoria.setSelection(App.retornaIndex(filme.categoria));
        handler.imgfoto.setImageBitmap(filme.fromArrayToBitmap());
        handler.imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lst.OnSacaFotoHandler(position);
            }
        });
//        handler.btinsert.setTag(filme);
//        handler.btinsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Filme filme= (Filme) view.getTag();
//                Log.i(TAG,filme.titulo);
//            }
//        });
        handler.btdelete.setTag(filme);
        handler.btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contato filme= (Contato) view.getTag();
                Log.i(TAG,filme.titulo);
                App.filmes.remove(filme);
                App.gravarLista();
                ((MainActivity)ctx).adpt.notifyDataSetChanged();
                Toast.makeText(ctx, "Register Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        handler.btupdate.setTag(filme);
        Handler finalHandler = handler;
        handler.btupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contato filme= (Contato)view.getTag();
                Log.i(TAG,filme.titulo);
                int id= Integer.parseInt(finalHandler.editid.getText().toString());
                String titulo = finalHandler.edittitulo.getText().toString();
                String number = finalHandler.editnumber.getText().toString();
                String categoria = finalHandler.spincategoria.getSelectedItem().toString();
                Drawable drw = finalHandler.imgfoto.getDrawable();
                Bitmap foto = ((BitmapDrawable)drw).getBitmap();
                App.updateFilme(filme.id,id,titulo,number,categoria,foto);
                App.gravarLista();
                ((MainActivity)ctx).adpt.notifyDataSetChanged();
            }
        });

        handler.btcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String number = finalHandler.editnumber.getText().toString();
                intent.setData(Uri.parse("tel:" + number));
                ctx.startActivity(intent);
            }
        });



        return view;
    }
}
