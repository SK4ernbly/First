package my.android.notes.app.activity;



import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import my.android.notes.app.gui.MenuExpandList;
import my.android.notes.app.R;


public class MainActivity extends Activity {


    private static MenuExpandList menuExpandableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (menuExpandableList==null){
            menuExpandableList = new MenuExpandList(this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



}

