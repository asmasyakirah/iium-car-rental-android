package dbsproject.iiumcarrental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Activity4Search extends AppCompatActivity
{
    // UI
    EditText inputPhoneNo;
    EditText inputPassword;
    EditText inputFirstName;
    EditText inputLastName;

    // Variables
    String phoneNo;
    String password;
    String firstName;
    String lastName;

    // Car belongings (his/hers or others)
    public int mineNotMine;
    public static final int NOT_MINE    = 0;
    public static final int MINE        = 1;

    // Current activity data
    public static final int ACTIVITY3_LISTVIEWER_SEARCH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4_search_form);

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

    private void setupUI()
    {
    }

    public void onConfirmButtonClick(View view)
    {
        // TODO CREATE bookingdetails or cardetails
        // TODO return data and finish();
        Intent goToViewSearchList = new Intent(this, Activity3ListViewer.class);
        goToViewSearchList.putExtra("passedActivityNo", ACTIVITY3_LISTVIEWER_SEARCH);
        goToViewSearchList.putExtra("passedMineNotMine", NOT_MINE);
        startActivity(goToViewSearchList);
    }
}
