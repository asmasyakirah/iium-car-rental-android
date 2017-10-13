package dbsproject.iiumcarrental;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Activity0SignUp extends Activity
{
    // UI
    private ProgressDialog loadingProgressDialog;
    private EditText inputPhoneNo;
    private EditText inputPassword;
    private EditText inputPasswordConfirm;
    private EditText inputFirstName;
    private EditText inputLastName;

    private String phoneNo;
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String lastName;

    // Tags
    private static final String TAG = "Activity0SignUp";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String SIGNUP_URL = "http://10.111.172.168/iiumcarrental/signup.php";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity0_signup);

        inputPhoneNo = (EditText) findViewById(R.id.input_phone_no);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputPasswordConfirm = (EditText) findViewById(R.id.input_password_confirm);
        inputFirstName = (EditText) findViewById(R.id.input_car_brand);
        inputLastName = (EditText) findViewById(R.id.input_last_name);
    }

    // On sign up button click, this class will connect to database
    // Successful sign up will go to Activity2UserForm class
    // Failed sign up will show JSON message
    public void onSignUpClick(View view)
    {
        // On signup button click, get user input
        phoneNo = inputPhoneNo.getText().toString();
        password = inputPassword.getText().toString();
        passwordConfirm = inputPasswordConfirm.getText().toString();
        firstName = inputFirstName.getText().toString();
        lastName = inputLastName.getText().toString();

        // Then attempt signup
        new AttemptSignUp().execute();
    }

    // Attempt signup class
    class AttemptSignUp extends AsyncTask<String, String, String>
    {
        // Pre-execute progress dialog before starting background thread
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            loadingProgressDialog = new ProgressDialog(Activity0SignUp.this);
            loadingProgressDialog.setMessage("Signing up...");
            loadingProgressDialog.setIndeterminate(false);
            loadingProgressDialog.setCancelable(true);
            loadingProgressDialog.show();
        }

        // Background method
        @Override
        protected String doInBackground(String... args)
        {
            try
            {
                // Build parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", phoneNo);
                params.put("password", password);
                params.put("passwordConfirm", passwordConfirm);
                params.put("firstName", firstName);
                params.put("lastName", lastName);

                // Log report
                Log.d(TAG, "Making HTTP request to " + SIGNUP_URL);

                // Making HTTP request
                JSONParser jsonParser = new JSONParser();
                JSONObject json = jsonParser.makeHttpRequest(SIGNUP_URL, "POST", params);

                // Log report
                Log.d(TAG, "JSON Response: " + json.toString());

                // Check for JSON success tag
                int jsonSuccess = json.getInt(TAG_SUCCESS);
                if (jsonSuccess == 1)
                {
                    Intent goToHome = new Intent(Activity0SignUp.this, Activity1Home.class);
                    startActivity(goToHome);
                    finish();
                }

                // Log report
                Log.d(TAG, json.getString(TAG_MESSAGE));
                return json.getString(TAG_MESSAGE);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        // After background method is completed,
        protected void onPostExecute(String jsonMessage)
        {
            // dismiss the loading dialog
            loadingProgressDialog.dismiss();
            // and show JSON message as a toast
            if (jsonMessage != null)
            {
                Toast.makeText(Activity0SignUp.this, jsonMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    // On sign up link click, this class executes to go back to Activity0LogIn class
    public void logIn(View view)
    {
        Intent goToLogIn = new Intent(this, Activity0LogIn.class);
        startActivity(goToLogIn);
        finish();
    }

}
