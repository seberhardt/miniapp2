package com.example.sasha.miniapp2;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sasha on 2/28/2018.
 */

public class Recipe implements Parcelable{
    //this class should have all fields for a recipe
    //use same helper method used in lecture example to read from a JSON file
    //static method that reads from recipes.json, creates ArrayList of recipe and returns it
    public String title;
    public String imageUrl;
    public String instructionUrl;
    public String description;
    public String servings;
    public String prepTime;
    public String dietLabel;


    public Recipe(){

    }

    public static ArrayList<Recipe> getRecipesFromFile(String filename, Context context) {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

        try {
            String jsonString = loadJsonFromAsset("recipes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");

            for (int i = 0; i<recipes.length(); i++){
                Recipe recipe = new Recipe();
                recipe.title = recipes.getJSONObject(i).getString("title");
                recipe.description = recipes.getJSONObject(i).getString("description");
                recipe.dietLabel = recipes.getJSONObject(i).getString("dietLabel");
                recipe.imageUrl = recipes.getJSONObject(i).getString("image");
                recipe.instructionUrl = recipes.getJSONObject(i).getString("url");
                recipe.prepTime = recipes.getJSONObject(i).getString("prepTime");
                recipe.servings = recipes.getJSONObject(i).getString("servings");

                recipeList.add(recipe);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }


    private static String loadJsonFromAsset(String filename, Context context) {
     String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public Recipe(Parcel in){
    String[] data = new String[7];
    in.readStringArray(data);
    this.title = data[0];
    this.dietLabel = data[1];
    this.prepTime = data[2];
    this.servings = data[3];
    this.instructionUrl = data[4];
    this.imageUrl = data[5];
    this.description = data[6];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.title,
        this.dietLabel, this.prepTime, this.servings, this.instructionUrl,
                this.imageUrl, this.description});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}