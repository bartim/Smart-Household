package com.example.sven.myapplication.kochbuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Ingredient;
import com.example.sven.myapplication.kochbuch.model.Step;

/**
 * This activity can be used to add a new ingredient. When done, it will deliver an intent back to its caller with extra NewReceipt.EXTRA_INGREDIENT.
 */
public class NewIngredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] types = new String[]{
                "g",
                "kg",
                "l",
                "ml",
                "stck"
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ingredient);

        setTitle("Zutat hinzufügen");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ((Spinner) findViewById(R.id.newIngredientAmountType)).setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types));
        ((Spinner) findViewById(R.id.newIngredientPriceType)).setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types));

        findViewById(R.id.newIngredientSaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.newIngredientName)).getText().toString();
                String amountStr = ((EditText) findViewById(R.id.newIngredientAmount)).getText().toString();
                String amountType = ((Spinner) findViewById(R.id.newIngredientAmountType)).getSelectedItem().toString();
                String priceStr = ((EditText) findViewById(R.id.newIngredientPrice)).getText().toString();
                String priceType = ((Spinner) findViewById(R.id.newIngredientPriceType)).getSelectedItem().toString();

                int amount = Integer.parseInt(amountStr);
                /* this is a little bit hacky.
                 * We parse the number as Double and multiply by 100 to get rid of the comma.
                 * Because of floating point rounding errors, we add 0.5 and cast to int.
                 * Therefore, if the rounding error is smaller than 0.5, we get correct results.
                 * For big numbers, this might be buggy.
                 */
                int price = (int) (Double.parseDouble(amountStr) * 100 + 0.5);

                Intent intent = new Intent();

                intent.putExtra(NewReceipt.EXTRA_INGREDIENT, new Ingredient(amount, amountType, name, price, priceType));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
