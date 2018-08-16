package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }



        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            Toast.makeText(this, "Sandwich is null", Toast.LENGTH_SHORT).show();
            // Sandwich data unavailable
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
        TextView detailDescriptionTextView = findViewById(R.id.detail_description_tv);
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_as_tv);
        TextView originTextView = findViewById(R.id.detail_place_of_origin_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_detail_tv);
        TextView alsoKnownAsLabelTextView = findViewById(R.id.also_known_as_label);
        TextView originLabelTextView = findViewById(R.id.orgin_label);



        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        String[] alternativeNames = new String[alsoKnownAs.size()];
        for(int i = 0; i<alsoKnownAs.size();i++){
            alternativeNames[i]=alsoKnownAs.get(i);
        }

        List<String> ingredientsList = sandwich.getIngredients();
        ingredientsTextView.setText("");
        for(String ingredient:ingredientsList){
            ingredientsTextView.append("* "+ingredient+"\n");


        }



        if(alternativeNames.length>0) {
            alsoKnownAsTextView.setVisibility(View.VISIBLE);
            alsoKnownAsLabelTextView.setVisibility(View.VISIBLE);

            String alsoKnownAsString = implode(",", alternativeNames);
            alsoKnownAsTextView.setText(alsoKnownAsString);


        }
        else{
            alsoKnownAsTextView.setVisibility(View.GONE);
            alsoKnownAsLabelTextView.setVisibility(View.GONE);
        }

        if(sandwich.getDescription()!=null && !sandwich.getDescription().isEmpty()) {
            detailDescriptionTextView.setText(sandwich.getDescription());
            detailDescriptionTextView.setVisibility(View.VISIBLE);
        }
        else{
            detailDescriptionTextView.setVisibility(View.GONE);
        }

        if(sandwich.getPlaceOfOrigin()!=null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            originTextView.setText(sandwich.getPlaceOfOrigin());
            originTextView.setVisibility(View.VISIBLE);
            originLabelTextView.setVisibility(View.VISIBLE);
        }
        else{
            originTextView.setVisibility(View.GONE);
            originLabelTextView.setVisibility(View.GONE);
        }


    }

    public static String implode(String separator, String... data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length - 1; i++) {
            //data.length - 1 => to not add separator at the end
            if (!data[i].matches(" *")) {//empty string are ""; " "; "  "; and so on
                sb.append(data[i]);
                sb.append(separator);
            }
        }
        sb.append(data[data.length - 1].trim());
        return sb.toString();
    }
}
