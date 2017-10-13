package dbsproject.iiumcarrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class Activity3Viewer extends AppCompatActivity
{
    // Car belongings (his/hers or others)
    public int mineNotMine;
    private int[] mineNotMineMenu = {R.menu.menu_none, R.menu.menu_edit};
    public static final int NOT_MINE    = 0;
    public static final int MINE        = 1;

    // Current activity data
    public int currentActivity;
    private String[] activityTitle = {"View booking", "View car"};
    private int[] activityImage = {R.drawable.key, R.drawable.truck};
    public static final int ACTIVITY3_VIEWER_BOOK   = 0;
    public static final int ACTIVITY3_VIEWER_CAR    = 1;

    // Next activity data
    public int toActivity;
    public static final int ACTIVITY2_EDIT_CAR  = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_viewer);

        getActivityData();
        getDatabaseData();
        setupUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu & add items to the action bar
        getMenuInflater().inflate(mineNotMineMenu[mineNotMine], menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks
        int actionId = item.getItemId();
        switch (actionId)
        {
            case R.id.editAction:
                toActivity = ACTIVITY2_EDIT_CAR;
                Intent goToEdit = new Intent(this, Activity2Edit.class);
                goToEdit.putExtra("passedActivityNo", ACTIVITY2_EDIT_CAR);
                startActivity(goToEdit);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // Get data from previous activity
    private void getActivityData()
    {
        // Get data from previous activity
        Intent arrivedAtViewer = getIntent();
        currentActivity = arrivedAtViewer.getIntExtra("passedActivityNo", 0);
        mineNotMine = arrivedAtViewer.getIntExtra("passedMineNotMine", 0);
    }

    // Get data from database
    private void getDatabaseData()
    {
        switch (currentActivity)
        {
            case ACTIVITY3_VIEWER_BOOK:
                // TODO READ cardetails from database
                break;

            case ACTIVITY3_VIEWER_CAR:
                // TODO READ bookingdetails from database
                break;
        }
    }

    // Setup user interface based on activity
    private void setupUI()
    {
        setTitle(activityTitle[currentActivity]);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(activityImage[currentActivity]);
    }

    public void onDeleteButtonClick(View view)
    {
        // TODO PROMPT FOR CONFIRMATION
        // TODO DELETE car, cardetails, booking and bookingdetails
    }
}
