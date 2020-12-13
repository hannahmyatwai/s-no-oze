package com.example.alarmproject;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class ProfileDataSource {


    private ArrayList<CardData> dataArrayList;

    ProfileDataSource(){
        dataArrayList = new ArrayList<>();
    }

    void addData(String name, String path){
        CardData c = new CardData(name, path);
        dataArrayList.add(c);
    }

    void addData(String name, Bitmap bitmapImage, Context context){

        String path = ProfileUtils.saveToInternalStorage(bitmapImage, name, context);
        CardData c = new CardData(name, path);
        dataArrayList.add(c);
    }

    void removeDataData(int position){
        dataArrayList.remove(position);
    }

    String getName(int i){
        return dataArrayList.get(i).getName();
    }

    String getPath(int i){
        return dataArrayList.get(i).getPath();
    }

    Bitmap getImage(int i){
        String name = dataArrayList.get(i).getName();
        String path = dataArrayList.get(i).getPath();
        return ProfileUtils.loadImageFromStorage(path, name);

    }

    int getSize(){
        return dataArrayList.size();
    }

    private static class CardData{

        private String name;
        private String path;

        private CardData( String name, String path){
            this.name = name;
            this.path = path;
        }


        private String getName() {
            return name;
        }


        private String getPath(){
            return path;
        }
    }
}