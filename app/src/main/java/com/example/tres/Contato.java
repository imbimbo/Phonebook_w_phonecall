package com.example.tres;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Contato implements Parcelable, Serializable {
    public int id;
    public String titulo;
    public String number;
    public String categoria;
    public byte[] foto;

    public Contato(int id, String titulo, String number, String categoria, Bitmap bmp) {
        this.id = id;
        this.titulo = titulo;
        this.number = number;
        this.categoria = categoria;
        this.foto = fromBitmapToArray(bmp);
    }

    public Contato(int id, String titulo, String number, String categoria, byte[] f) {
        this.id = id;
        this.titulo = titulo;
        this.number = number;
        this.categoria = categoria;
        this.foto = f;
    }

    public static byte[] fromBitmapToArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        return  stream.toByteArray();
    }

    public  Bitmap fromArrayToBitmap(){
      return   BitmapFactory.decodeByteArray(foto,0,foto.length);
    }

    protected Contato(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        number = in.readString();
        categoria = in.readString();
        foto = in.createByteArray();
    }

    public static final Creator<Contato> CREATOR = new Creator<Contato>() {
        @Override
        public Contato createFromParcel(Parcel in) {
            return new Contato(in);
        }

        @Override
        public Contato[] newArray(int size) {
            return new Contato[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(titulo);
        parcel.writeString(number);
        parcel.writeString(categoria);
        parcel.writeByteArray(foto);
    }
}
