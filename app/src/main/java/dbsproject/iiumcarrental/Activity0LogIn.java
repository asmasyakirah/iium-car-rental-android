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


public class Activity0LogIn extends Activity
{
    // UI
    private ProgressDialog loadingProgressDialog;
    private EditText inputPhoneNo;
    private EditText inputPassword;

    // Variables
    String phoneNo;
    String password;

    // Tags
    private static final String TAG = "Activity0LogIn";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String LOGIN_URL = "http://10.111.172.168/iiumcarrental/login.php";
    //it's important to note that the message is both in the parent branch of
    //our JSON tree that displays a "Post Available" or a "No Post Available" message,
    //and there is also a message for each individual post, listed under the "posts"
    //category, that displays what the user typed as their message.


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity0_login);

        // Setup user login input
        inputPhoneNo = (EditText) findViewById(R.id.input_phone_no);
        inputPassword = (EditText) findViewById(R.id.input_password);
    }


    public void onLogInClick(View view)
    {
        // On log in button click, get user input
        phoneNo = inputPhoneNo.getText().toString();
        password = inputPassword.getText().toString();

        // Then attempt login
        new AttemptLogIn().execute();
    }


    class AttemptLogIn extends AsyncTask<String, String, String>
    {
        // Pre-execute progress dialog before starting background thread
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            loadingProgressDialog = new ProgressDialog(Activity0LogIn.this);
            loadingProgressDialog.setMessage("Logging in...");
            loadingProgressDialog.setIndeterminate(false);
            loadingProgressDialog.setCancelable(true);
            loadingProgressDialog.show();
        }

        // Background method
        @Override
        protected String doInBackground(String... args)
        {
            // Check for success tag
            int jsonSuccess;

            try
            {
                // Build parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", phoneNo);
                params.put("password", password);

                // Log report
                Log.d(TAG, "Making HTTP request to " + LOGIN_URL);

                // Making HTTP request
                JSONParser jsonParser = new JSONParser();
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                // Log report
                Log.d(TAG, "JSON Response: " + json.toString());

                // JSON success tag
                jsonSuccess = json.getInt(TAG_SUCCESS);
                if (jsonSuccess == 1)
                {
                    Intent goToHome = new Intent(Activity0LogIn.this, Activity1Home.class);
                    goToHome.putExtra("passedPhoneNo", phoneNo);
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
                Toast.makeText(Activity0LogIn.this, jsonMessage, Toast.LENGTH_LONG).show();
            }
        }
    }


    // On sign up link click, this class executes to go to Activity0SignUp class
    public void onSignUpClick(View view)
    {
        Intent goToSignUp = new Intent(this, Activity0SignUp.class);
        startActivity(goToSignUp);
        finish();
    }

}
