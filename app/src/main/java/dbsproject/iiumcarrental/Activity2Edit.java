package dbsproject.iiumcarrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity2Edit extends AppCompatActivity
{
    // UI
    EditText inputPhoneNo;
    EditText inputPassword;
    EditText inputFirstName;
    EditText inputLastName;
    Button confirmButton;

    // Variables
    String phoneNo;
    String password;
    String firstName;
    String lastName;

    // Activity data from previous
    public int currentActivity;
    private String[] activityTitle = {"Edit my profile", "Edit car details"};
    private int[] activityImage = {R.drawable.user, R.drawable.truck};
    public static final int ACTIVITY2_EDIT_PROFILE  = 0;
    public static final int ACTIVITY2_EDIT_CAR      = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getActivityData();
        getDatabaseData();
        setupUI();
    }

    private void getDatabaseData()
    {
        // TODO READ userdetails or cardetails from database
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu & add items to the action bar
        getMenuInflater().inflate(R.menu.menu_save_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks
        int actionId = item.getItemId();
        switch (actionId)
        {
            case R.id.saveAction:
                // TODO UPDATE userdetails or cardetails from database
                // TODO return data and finish();
                break;

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
        // Set content view
        switch (currentActivity)
        {
            case ACTIVITY2_EDIT_PROFILE:
                setContentView(R.layout.activity2_profile_form);
                break;

            case ACTIVITY2_EDIT_CAR:
                setContentView(R.layout.activity2_car_form);
                break;
        }

        // Set activity title
        setTitle(activityTitle[currentActivity]);

        // Set confirm button invisible, for edit activity. Its only for add activity only
        confirmButton = (Button) findViewById(R.id.confirm_button);
        confirmButton.setVisibility(View.INVISIBLE);
    }
}