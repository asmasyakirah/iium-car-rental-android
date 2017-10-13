package dbsproject.iiumcarrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity3ListViewer extends AppCompatActivity
{
    // Variables
    private String phoneNo;
    private ArrayList<String> carID = new ArrayList<>();
    private ArrayList<String> ownerID = new ArrayList<>();
    private ArrayList<String> carAvailability = new ArrayList<>();
    private ArrayList<String> carModel = new ArrayList<>();
    private ArrayList<String> carBrand = new ArrayList<>();
    private ArrayList<String> carTransmission = new ArrayList<>();
    private ArrayList<String> perHour = new ArrayList<>();
    private ArrayList<String> perHalfDay = new ArrayList<>();
    private ArrayList<String> perDay = new ArrayList<>();

    // Car belongings (his/hers or others)
    public int mineNotMine;
    public static final int NOT_MINE    = 0;
    public static final int MINE        = 1;

    // Current activity data
    public int currentActivity;
    private String[] activityTitle = {"My booking list", "My car list", "Search result"};
    private int[] activityMenu = { R.menu.menu_cancel, R.menu.menu_add};
    public static final int ACTIVITY3_LISTVIEWER_BOOK   = 0;
    public static final int ACTIVITY3_LISTVIEWER_CAR    = 1;
    public static final int ACTIVITY3_LISTVIEWER_SEARCH = 2;

    // Next activity data
    public int toActivity;
    public static final int ACTIVITY3_VIEWER_BOOK   = 0;
    public static final int ACTIVITY3_VIEWER_CAR    = 1;
    public static final int ACTIVITY2_ADD_BOOK      = 0;
    public static final int ACTIVITY2_ADD_CAR       = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_listviewer);

        getActivityData();
        setupListView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu & add items to the action bar
        getMenuInflater().inflate(activityMenu[mineNotMine], menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks
        int actionId = item.getItemId();
        switch (actionId)
        {
            case R.id.addAction:
                Intent goToAdd = new Intent(Activity3ListViewer.this, Activity2Add.class);
                goToAdd.putExtra("passedActivityNo", toActivity);
                startActivity(goToAdd);
                break;

            case R.id.cancelAction:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void getActivityData()
    {
        // Get data from previous activity
        Intent arrivedAtListViewer = getIntent();
        currentActivity = arrivedAtListViewer.getIntExtra("passedActivityNo", 0);
        mineNotMine = arrivedAtListViewer.getIntExtra("passedMineNotMine", 0);
        toActivity = arrivedAtListViewer.getIntExtra("passedActivityNo", 0);
        phoneNo = arrivedAtListViewer.getStringExtra("passedPhoneNo");
        carID = arrivedAtListViewer.getStringArrayListExtra("passedCarId");
        ownerID = arrivedAtListViewer.getStringArrayListExtra("passedOwnerId");
        carAvailability = arrivedAtListViewer.getStringArrayListExtra("passedCarAvailability");
        carModel = arrivedAtListViewer.getStringArrayListExtra("passedCarModel");
        carBrand = arrivedAtListViewer.getStringArrayListExtra("passedCarBrand");
        carTransmission = arrivedAtListViewer.getStringArrayListExtra("passedCarTransmission");
        perHour = arrivedAtListViewer.getStringArrayListExtra("passedPerHour");
        perHalfDay = arrivedAtListViewer.getStringArrayListExtra("passedPerHalfDay");
        perDay = arrivedAtListViewer.getStringArrayListExtra("passedPerDay");
    }


    private void setupListView()
    {
        setTitle(activityTitle[currentActivity]);

        // Get ListView object from xml
        final ListView listView = (ListView) findViewById(R.id.listView);

        ArrayList<String> myCar = new ArrayList<>();
        myCar.add("Axia");
        myCar.add("MyVi");
        ArrayList<String> myBooking = new ArrayList<>();
        myBooking.add("Bike");
        ArrayList<String> searchResult = carID;

        // Define adapter
        ArrayAdapter<String> carAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, myCar);
        ArrayAdapter<String> bookAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, myBooking);
        ArrayAdapter<String> searchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, searchResult);


        // Assign adapter to ListView
        switch (currentActivity)
        {
            case ACTIVITY3_LISTVIEWER_BOOK:
                listView.setAdapter(bookAdapter);
                break;
            case ACTIVITY3_LISTVIEWER_CAR:
                listView.setAdapter(carAdapter);
                break;
            case ACTIVITY3_LISTVIEWER_SEARCH:
                listView.setAdapter(searchAdapter);
                break;
        }

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                // Get clicked item
                String clickedItem = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(Activity3ListViewer.this,
                        "Position:" + position + "\nItem: " + clickedItem, Toast.LENGTH_LONG)
                        .show();

                changeActivity();
            }
        });
    }


    private void changeActivity()
    {
        switch (currentActivity)
        {
            case ACTIVITY3_LISTVIEWER_BOOK:
                Intent goToViewBook = new Intent(Activity3ListViewer.this, Activity3Viewer.class);
                goToViewBook.putExtra("passedActivityNo", ACTIVITY3_VIEWER_BOOK);
                goToViewBook.putExtra("passedMineNotMine", NOT_MINE);
                startActivity(goToViewBook);
                break;

            case ACTIVITY3_LISTVIEWER_CAR:
                Intent goToViewCar = new Intent(Activity3ListViewer.this, Activity3Viewer.class);
                goToViewCar.putExtra("passedActivityNo", ACTIVITY3_VIEWER_CAR);
                goToViewCar.putExtra("passedMineNotMine", MINE);
                startActivity(goToViewCar);
                break;

            case ACTIVITY3_LISTVIEWER_SEARCH:
                Intent goToViewSearchCar = new Intent(Activity3ListViewer.this, Activity3Viewer.class);
                goToViewSearchCar.putExtra("passedActivityNo", ACTIVITY3_VIEWER_CAR);
                goToViewSearchCar.putExtra("passedMineNotMine", NOT_MINE);
                startActivity(goToViewSearchCar);
                break;
        }
    }


}
