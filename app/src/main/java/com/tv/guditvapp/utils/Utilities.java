package com.tv.guditvapp.utils;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Utilities {

    public static boolean isStringNotNull(String name) {
        if (name != null && !name.trim().equalsIgnoreCase("")
                && !name.equalsIgnoreCase("null")) {
            return true;
        }
        return false;
    }

    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static String getAssetJsonData(Context context, String fileName) {
        String json;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static void setImageViewByGlideAsList(final Context context, final ImageView imageView,
                                           final ArrayList<String> mImageListSectionOne, final long milliSecond){
       // Glide.with(context).load(imageUrl).into(imageView);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;
            public void run() {
                //imageView1.setImageResource(imageArray[i]);
                Glide.with(context).load(mImageListSectionOne.get(i)).into(imageView);
                i++;
                if (i > mImageListSectionOne.size() - 1) {
                    i = 0;
                }
                handler.postDelayed(this, milliSecond);
            }
        };
        handler.postDelayed(runnable, milliSecond);
    }

    public static void setImageViewByGlide(final Context context, final ImageView imageView,
                                           final String mImageListSectionOne, final long second){
        // Glide.with(context).load(imageUrl).into(imageView);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;
            public void run() {
                //imageView1.setImageResource(imageArray[i]);
                Glide.with(context).load(mImageListSectionOne).into(imageView);
                handler.postDelayed(this, second*1000);
            }
        };
        handler.postDelayed(runnable, second*1000);
    }

    public static void setMarqueeSpeed(TextView tv, float speed) {
        if (tv != null) {
            try {
                Field f = null;
                if (tv instanceof AppCompatTextView) {
                    f = tv.getClass().getSuperclass().getDeclaredField("mMarquee");
                } else {
                    f = tv.getClass().getDeclaredField("mMarquee");
                }
                if (f != null) {
                    f.setAccessible(true);
                    Object marquee = f.get(tv);
                    if (marquee != null) {
                        String scrollSpeedFieldName = "mScrollUnit";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            scrollSpeedFieldName = "mPixelsPerSecond";
                        }
                        Field mf = marquee.getClass().getDeclaredField(scrollSpeedFieldName);
                        mf.setAccessible(true);
                        mf.setFloat(marquee, speed);
                    }
                } else {
                    GudiLogs.logDebug("Marquee", "mMarquee object is null.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
