package com.tv.guditvapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tv.guditvapp.dashboard.model.SectionDataModel;

public class AppSharedPreUtils {

    private static AppSharedPreUtils sharePref = new AppSharedPreUtils();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private AppSharedPreUtils() {} //prevent creating multiple instances by making the constructor private

    //The context passed into the getInstance should be application level context.
    public static AppSharedPreUtils getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharePref;
    }

    public void saveDashBoardSectionData(SectionDataModel dataModel){
        Gson gson = new Gson();
        String json = gson.toJson(dataModel);
        editor.putString("SectionDataModelObjKey", json);
        editor.commit();
    }

    public SectionDataModel getDashBoardSectionData(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("SectionDataModelObjKey", "");
        GudiLogs.logDebug("SHARED", "mVideoListSectionOne Image -> " + json);


        return gson.fromJson(json, SectionDataModel.class);
    }

    public void saveStringValues(String valuesKey, String values){
        editor.putString(valuesKey, values);
        editor.commit();
    }

    public String getStringValues(String valuesKey){
        return sharedPreferences.getString(valuesKey, "");
    }

    public void saveIntValues(String valuesKey, int values){
        editor.putInt(valuesKey, values);
        editor.commit();
    }

    public int getIntValues(String valuesKey){
        return sharedPreferences.getInt(valuesKey, 0);
    }

    public void removeValues(String valueKey) {
        editor.remove(valueKey);
        editor.commit();
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }

}
