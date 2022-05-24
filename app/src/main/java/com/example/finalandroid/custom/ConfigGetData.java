package com.example.finalandroid.custom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public  class ConfigGetData  {
    ImageView bmImage;

    public ConfigGetData(ImageView bmImage) {
        this.bmImage = bmImage;
    }


    public void execute(String url) {
        Picasso.get().load(url).into(bmImage);
    }
}
