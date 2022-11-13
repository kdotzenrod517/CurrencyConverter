package com.kdotz.currencyconerter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    String response;
    String rate;
    Spinner sItems;

    public void onClick(View view) throws ExecutionException, InterruptedException, JSONException {
        EditText editText = (EditText) findViewById(R.id.editText);

        if(!editText.getText().toString().equals("")) {
            NumberFormat formatter = new DecimalFormat("#,###.00");
            new Controller().execute("https://free.currconv.com/api/v7/convert?apiKey=7390ab9a542be6318301&q=USD_" + sItems.getSelectedItem().toString() + "&compact=ultra").get();
            System.out.println(Controller.server_response);
            response = Controller.server_response;
            JSONObject jsonObject = new JSONObject(response);
            rate = jsonObject.getString("USD_" + sItems.getSelectedItem().toString());
            System.out.println(rate);

            double finalRate = Double.valueOf(rate);
            String converted = formatter.format(Double.valueOf(editText.getText().toString()) * finalRate);

            Toast.makeText(this, "$" + editText.getText().toString() + " is " + converted + " " + sItems.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
        }  else {
            Toast.makeText(this, "Please enter a USD amount.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("GBP");
        spinnerArray.add("THB");
        spinnerArray.add("KRW");
        spinnerArray.add("VND");
        spinnerArray.add("LAK");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapter);
    }
}
