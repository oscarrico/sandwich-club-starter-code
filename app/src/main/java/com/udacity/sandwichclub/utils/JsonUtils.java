package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESC = "description";
    private static final String IMG = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String jsonString) {
        Sandwich selectedSandwich = new Sandwich();
        try {
            JSONObject jsonSandwich =  new JSONObject(jsonString);
            JSONObject name  = jsonSandwich.getJSONObject(NAME);

            selectedSandwich.setMainName(name.getString(MAIN_NAME));
            selectedSandwich.setAlsoKnownAs(getOtherNames(name));
            selectedSandwich.setPlaceOfOrigin(jsonSandwich.getString(PLACE_OF_ORIGIN));
            selectedSandwich.setDescription(jsonSandwich.getString(DESC));
            selectedSandwich.setImage(jsonSandwich.getString(IMG));
            selectedSandwich.setIngredients(getIngredients(jsonSandwich));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return selectedSandwich;
    }

    private static List<String> getOtherNames(JSONObject name) {
        List<String> otherNames = new ArrayList<>();
        try {
            JSONArray namesArray = name.getJSONArray(ALSO_KNOWN_AS);
            for(int i = 0; i < namesArray.length(); i++) {
                otherNames.add(namesArray.getString(i));
                Log.e("OTHER NAME", namesArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return otherNames;
    }

    private static List<String> getIngredients(JSONObject jsonSandwich) {
        List<String> Ingredients = new ArrayList<>();
        try {
            JSONArray ingredientsArray = jsonSandwich.getJSONArray(INGREDIENTS);
            for(int i = 0; i < ingredientsArray.length(); i++) {
                Ingredients.add(ingredientsArray.getString(i));
                Log.e("Ingredient: ", ingredientsArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Ingredients;
    }
}
