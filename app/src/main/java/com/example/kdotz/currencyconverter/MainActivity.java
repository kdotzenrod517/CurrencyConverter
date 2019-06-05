package com.example.kdotz.currencyconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    String response;
    String rate;

    public void onClick(View view) throws ExecutionException, InterruptedException, JSONException {

        NumberFormat formatter = new DecimalFormat("#0.00");
        EditText editText = (EditText) findViewById(R.id.editText);
        new Controller().execute("https://free.currconv.com/api/v7/convert?apiKey=a3428ff50b35faa0658d&q=USD_GBP&compact=ultra").get();
        System.out.println(Controller.server_response);
        response = Controller.server_response;
        JSONObject jsonObject = new JSONObject(response);
        rate = jsonObject.getString("USD_GBP");
        System.out.println(rate);

        double finalRate = Double.valueOf(rate);
        String converted = formatter.format(Double.valueOf(editText.getText().toString()) * finalRate);

        Toast.makeText(this, "$" + editText.getText().toString() + " is £" + converted, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
