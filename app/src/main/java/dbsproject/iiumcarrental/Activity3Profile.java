package dbsproject.iiumcarrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Activity3Profile extends AppCompatActivity
{

    // Profile belongings (his/hers or others)
    public int mineNotMine;
    private int[] mineNotMineMenu = {R.menu.menu_none, R.menu.menu_edit};
    public static final int NOT_MINE    = 0;
    public static final int MINE        = 1;

    // Current activity data
    private int[] activityTitle = {R.string.view_profile, R.string.view_my_profile};

    // Next activity data
    public int toActivity;
    public static final int ACTIVITY2_EDIT_PROFILE  = 0;

    // Class variables
    public int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_profile);

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
                toActivity = ACTIVITY2_EDIT_PROFILE;
                Intent goToEdit = new Intent(this, Activity2Edit.class);
                goToEdit.putExtra("passedActivityNo", ACTIVITY2_EDIT_PROFILE);
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
        mineNotMine = arrivedAtViewer.getIntExtra("passedMineNotMine", 0);
    }

    // Get data from database
    private void getDatabaseData()
    {
        // TODO READ userdetails WHERE userID='' from database
    }

    // Setup user interface based on activity
    private void setupUI()
    {
        setTitle(activityTitle[mineNotMine]);
    }
}
