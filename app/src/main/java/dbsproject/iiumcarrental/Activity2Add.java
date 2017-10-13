package dbsproject.iiumcarrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Timestamp;

public class Activity2Add extends AppCompatActivity
{
    // UI
    private Button confirmButton;

    // UI for booking
    private EditText inputCarId;
    private EditText inputBookingFrom;
    private EditText inputBookingUntil;

    // UI for car
    private EditText inputCarBrand;
    private EditText inputCarModel;
    Spinner carTransmissionSpinner;
    private EditText inputPricePerHour;
    private EditText inputPricePerHalfDay;
    private EditText inputPricePerDay;

    // Variables
    String carId;
    String carBrand;
    String carModel;
    String carTransmission;
    int pricePerHour;
    int pricePerHalfDay;
    int pricePerDay;
    int carAvailability;

    // Variables for booking
    int ownerId;
    String bookingId;
    Timestamp bookingFrom;
    Timestamp bookingUntil;
    String bookingStatus;

    // Variables for car
    String carTransmissionChoice[] = {"Manual","Auto","Others"};

    // Current activity data
    public int currentActivity;
    private String[] activityTitle = {"Add booking", "Add car"};
    public static final int ACTIVITY2_ADD_BOOK  = 0;
    public static final int ACTIVITY2_ADD_CAR   = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getActivityData();
        setupUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu & add items to the action bar
        getMenuInflater().inflate(R.menu.menu_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks
        int actionId = item.getItemId();
        switch (actionId)
        {
            case R.id.cancelAction:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // Get data from previous activity
    private void getActivityData()
    {
        // Get passed data
        Intent arrivedAtForm = getIntent();
        currentActivity = arrivedAtForm.getIntExtra("passedActivityNo", 0);
    }

    private void setupUI()
    {
        // TODO make edit text not editable
        // EditText editText;
        // editText.setKeyListener(null); or
        // editText.setEnabled(false);

        // Set content view
        switch (currentActivity)
        {
            case ACTIVITY2_ADD_BOOK:
                setContentView(R.layout.activity2_book_form);
                break;

            case ACTIVITY2_ADD_CAR:
                setContentView(R.layout.activity2_car_form);

                carTransmissionSpinner = (Spinner) findViewById(R.id.car_transmission_spinner);
                ArrayAdapter<String> carTransmissionArrayAdapter = new ArrayAdapter<String>(Activity2Add.this, android.R.layout.simple_list_item_activated_1, carTransmissionChoice);
                carTransmissionArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1); // The drop down view
                carTransmissionSpinner.setAdapter(carTransmissionArrayAdapter);

                break;
        }

        // Set activity title
        setTitle(activityTitle[currentActivity]);
    }

    public void onConfirmButtonClick(View view)
    {
        switch (currentActivity)
        {
            case ACTIVITY2_ADD_BOOK:
                break;

            case ACTIVITY2_ADD_CAR:

                // carId is automatically generated at database

                // Assign variables for editText
                inputCarBrand = (EditText) findViewById(R.id.input_car_brand);
                inputCarModel = (EditText) findViewById(R.id.input_car_model);
                inputPricePerHour = (EditText) findViewById(R.id.input_price_perhour);
                inputPricePerHalfDay = (EditText) findViewById(R.id.input_price_perhalfday);
                inputPricePerDay = (EditText) findViewById(R.id.input_price_perday);

                // Get input from editText
                carBrand = inputCarBrand.getText().toString();
                carModel = inputCarModel.getText().toString();
                pricePerHour = Integer.parseInt(inputPricePerHour.getText().toString());
                pricePerHalfDay = Integer.parseInt(inputPricePerHalfDay.getText().toString());
                pricePerDay = Integer.parseInt(inputPricePerDay.getText().toString());

                // Get car transmission from carTransmissionSpinner
                carTransmission = carTransmissionChoice[carTransmissionSpinner.getSelectedItemPosition()];



                break;
        }
        finish();
    }
}
