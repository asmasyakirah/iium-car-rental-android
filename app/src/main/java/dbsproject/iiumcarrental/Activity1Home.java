package dbsproject.iiumcarrental;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Activity1Home extends AppCompatActivity
{

    // UI
    private ProgressDialog loadingProgressDialog;

    // Variables
    private ArrayList<String> carID = new ArrayList<>();
    private ArrayList<String> ownerID = new ArrayList<>();
    private ArrayList<String> carAvailability = new ArrayList<>();
    private ArrayList<String> carModel = new ArrayList<>();
    private ArrayList<String> carBrand = new ArrayList<>();
    private ArrayList<String> carTransmission = new ArrayList<>();
    private ArrayList<String> perHour = new ArrayList<>();
    private ArrayList<String> perHalfDay = new ArrayList<>();
    private ArrayList<String> perDay = new ArrayList<>();

    // Tags
    private static final String TAG = "Activity1Home";
    private static final String TAG_CARS = "cars";
    private static final String TAG_CAR_ID = "carID";
    private static final String TAG_OWNER_ID = "ownerID";
    private static final String TAG_CAR_AVAILABILITY = "carAvailability";
    private static final String TAG_CAR_MODEL = "carModel";
    private static final String TAG_CAR_BRAND = "carBrand";
    private static final String TAG_CAR_TRANSMISSION = "carTransmission";
    private static final String TAG_PER_HOUR = "perHour";
    private static final String TAG_PER_HALFDAY = "perHalfDay";
    private static final String TAG_PER_DAY = "perDay";
    private static final String VIEWCARLIST_URL = "http://10.111.172.168/iiumcarrental/viewcarlist.php";

    // Profile belongings (his/hers or others)
    public static final int MINE = 1;
    public static final int NOT_MINE = 1;

    // Current activity data
    String phoneNo;

    // Next activity data
    public static final int ACTIVITY3_LISTVIEWER_BOOK   = 0;
    public static final int ACTIVITY3_LISTVIEWER_CAR    = 1;
    public static final int ACTIVITY3_LISTVIEWER_SEARCH = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1_home);

        Intent arrivedAtHome = getIntent();
        phoneNo = arrivedAtHome.getStringExtra("passedPhoneNo");
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        new LoadCarList().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu & add items to the action bar
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Get action bar item clicks
        int actionId = item.getItemId();

        // Action for each action bar button press
        switch (actionId)
        {
            case R.id.viewMyProfileAction:
                Intent goToMyProfile = new Intent(this, Activity3Profile.class);
                goToMyProfile.putExtra("passedMineNotMine", MINE);
                startActivity(goToMyProfile);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBookButtonClick(View view)
    {
        Intent goToViewBookList = new Intent(this, Activity3ListViewer.class);
        goToViewBookList.putExtra("passedActivityNo", ACTIVITY3_LISTVIEWER_BOOK);
        goToViewBookList.putExtra("passedMineNotMine", MINE);
        goToViewBookList.putExtra("passedPhoneNo", phoneNo);
        goToViewBookList.putStringArrayListExtra("passedCarId", carID);
        goToViewBookList.putStringArrayListExtra("passedOwnerId", ownerID);
        goToViewBookList.putStringArrayListExtra("passedCarAvailability", carAvailability);
        goToViewBookList.putStringArrayListExtra("passedCarModel", carModel);
        goToViewBookList.putStringArrayListExtra("passedCarBrand", carBrand);
        goToViewBookList.putStringArrayListExtra("passedCarTransmission", carTransmission);
        goToViewBookList.putStringArrayListExtra("passedPerHour", perHour);
        goToViewBookList.putStringArrayListExtra("passedPerHalfDay", perHalfDay);
        goToViewBookList.putStringArrayListExtra("passedPerDay", perDay);
        startActivity(goToViewBookList);
    }

    public void onCarButtonClick(View view)
    {
        Intent goToViewCarList = new Intent(this, Activity3ListViewer.class);
        goToViewCarList.putExtra("passedActivityNo", ACTIVITY3_LISTVIEWER_CAR);
        goToViewCarList.putExtra("passedMineNotMine", MINE);
        goToViewCarList.putExtra("passedPhoneNo", phoneNo);
        goToViewCarList.putStringArrayListExtra("passedCarId", carID);
        goToViewCarList.putStringArrayListExtra("passedOwnerId", ownerID);
        goToViewCarList.putStringArrayListExtra("passedCarAvailability", carAvailability);
        goToViewCarList.putStringArrayListExtra("passedCarModel", carModel);
        goToViewCarList.putStringArrayListExtra("passedCarBrand", carBrand);
        goToViewCarList.putStringArrayListExtra("passedCarTransmission", carTransmission);
        goToViewCarList.putStringArrayListExtra("passedPerHour", perHour);
        goToViewCarList.putStringArrayListExtra("passedPerHalfDay", perHalfDay);
        goToViewCarList.putStringArrayListExtra("passedPerDay", perDay);
        startActivity(goToViewCarList);
    }

    public void onSearchButtonClick(View view)
    {
        Intent goToViewSearchList = new Intent(this, Activity3ListViewer.class);
        goToViewSearchList.putExtra("passedActivityNo", ACTIVITY3_LISTVIEWER_SEARCH);
        goToViewSearchList.putExtra("passedMineNotMine", NOT_MINE);
        startActivity(goToViewSearchList);
        /*
        Intent goToSearch = new Intent(this, Activity4Search.class);
        goToSearch.putExtra("passedPhoneNo", phoneNo);
        */
        goToViewSearchList.putStringArrayListExtra("passedCarId", carID);
        goToViewSearchList.putStringArrayListExtra("passedOwnerId", ownerID);
        goToViewSearchList.putStringArrayListExtra("passedCarAvailability", carAvailability);
        goToViewSearchList.putStringArrayListExtra("passedCarModel", carModel);
        goToViewSearchList.putStringArrayListExtra("passedCarBrand", carBrand);
        goToViewSearchList.putStringArrayListExtra("passedCarTransmission", carTransmission);
        goToViewSearchList.putStringArrayListExtra("passedPerHour", perHour);
        goToViewSearchList.putStringArrayListExtra("passedPerHalfDay", perHalfDay);
        goToViewSearchList.putStringArrayListExtra("passedPerDay", perDay);
        startActivity(goToViewSearchList);
    }

    public class LoadCarList extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... arg0)
        {
            updateJSONdata();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            super.onPostExecute(result);
        }
    }


    public void updateJSONdata()
    {
        // Build parameters
        HashMap<String, String> params = new HashMap<>();
        params.put("username", phoneNo);

        // Log report
        Log.d(TAG, "Making HTTP request to " + VIEWCARLIST_URL);

        // Making HTTP request
        JSONParser jParser = new JSONParser();
        JSONObject json = jParser.makeHttpRequest(VIEWCARLIST_URL, "POST", params);

        // Log report
        Log.d(TAG, "JSON Response: " + json.toString());

        //when parsing JSON stuff, we should probably
        //try to catch any exceptions:
        try
        {
            // Get allCars array of objects
            JSONArray allCars = json.getJSONArray(TAG_CARS);

            // Clear array list first
            carID.clear();
            ownerID.clear();
            carAvailability.clear();
            carModel.clear();
            carBrand.clear();
            carTransmission.clear();
            perHour.clear();
            perHalfDay.clear();
            perDay.clear();

            // Loop through all cars
            for (int i=0; i< allCars.length(); i++)
            {
                JSONObject c = allCars.getJSONObject(i);

                // Gets the content of each tag
                carID.add(c.getString(TAG_CAR_ID));
                ownerID.add(c.getString(TAG_OWNER_ID));
                carAvailability.add(c.getString(TAG_CAR_AVAILABILITY));
                carModel.add(c.getString(TAG_CAR_MODEL));
                carBrand.add(c.getString(TAG_CAR_BRAND));
                carTransmission.add(c.getString(TAG_CAR_TRANSMISSION));
                perHour.add(c.getString(TAG_PER_HOUR));
                perHalfDay.add(c.getString(TAG_PER_HALFDAY));
                perDay.add(c.getString(TAG_PER_DAY));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
