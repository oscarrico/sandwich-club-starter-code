package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView alsoKnowAs;
    private TextView description;
    private TextView origin;
    private TextView ingridients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnowAs = (TextView) findViewById(R.id.also_known_tv);
        description = (TextView) findViewById(R.id.description_tv);
        ingridients = (TextView) findViewById(R.id.ingredients_tv);
        origin = (TextView) findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            Log.e("Error", "intent == null");
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.e("Error", "EXTRA_POSITION not found in intent");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            Log.e("Error", "Sandwich data unavailable");
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        for(String name: sandwich.getAlsoKnownAs()) {
            alsoKnowAs.append(name + "\n");
        }
        for(String ingridient: sandwich.getIngredients()) {
            ingridients.append(ingridient + "\n");
        }

        description.setText(sandwich.getDescription());
        origin.setText(sandwich.getPlaceOfOrigin());
    }

}
