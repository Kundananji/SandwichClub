package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        JSONObject jsonObject = null;
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        Sandwich sandwich = null;
        JSONObject nameJsonObject = null;
        try {
            jsonObject = new JSONObject(json);

            nameJsonObject = jsonObject.getJSONObject("name");

            String mainName = nameJsonObject.getString("mainName");
            JSONArray alsoKnownAsJsonArray = nameJsonObject.getJSONArray("alsoKnownAs");

            for(int i = 0; i<alsoKnownAsJsonArray.length();i++){
                 alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }

            JSONArray ingredientsJsonArray = jsonObject.getJSONArray("ingredients");

            for(int i = 0; i< ingredientsJsonArray.length(); i++){
                ingredients.add(ingredientsJsonArray.getString(i));
            }


            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description= jsonObject.getString("description");
            String image = jsonObject.getString("image");

            sandwich = new Sandwich(mainName, alsoKnownAs,placeOfOrigin, description, image,  ingredients);









        } catch (JSONException e) {
            e.printStackTrace();

            Log.e("JsonUtils","Fatal error: "+e.getMessage());
        }


        return sandwich;
    }
}
