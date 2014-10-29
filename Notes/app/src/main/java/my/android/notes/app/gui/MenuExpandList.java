package my.android.notes.app.gui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import my.android.notes.app.R;
import my.android.notes.app.activity.HttpSpr;
import my.android.notes.app.activity.MainActivity;
import my.android.notes.app.activity.Weather;
import my.android.notes.app.adapters.ExpandListAdapter;
import my.android.notes.app.fragments.OperationsFragment;
import my.android.notes.app.fragments.SettingsFragment;

/**
 * Created by Olya on 10/04/14.
 */
public class MenuExpandList {

    public static final String OPERATION_TYPE = "my.note.android.money.gui.MenuExpList.operationType";

    private Activity context;

    private DrawerLayout navDrawer;

    private ExpandListAdapter listAdapter;
    private ExpandableListView expListView;

    private List<String> listGroup;
    private HashMap<String, List<String>> mapChild;

    public MenuExpandList(final Activity context) {
        this.context = context;
        expListView = (ExpandableListView) context.findViewById(R.id.expListMenu);
        navDrawer = (DrawerLayout) context.findViewById(R.id.drawer_layout);

        fillMenu();

        listAdapter = new ExpandListAdapter(context, listGroup, mapChild);

        expListView.setAdapter(listAdapter);




        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                switch (groupPosition) {
                    case 0: {

                        Fragment fragment = new OperationsFragment();
                        Bundle args = new Bundle();
                        args.putInt(OPERATION_TYPE, childPosition);
                        fragment.setArguments(args);

                        FragmentManager fragmentManager = context.getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                        break;
                    }

                    case 1: {

                        if (childPosition == 0) {

                            Fragment fragment = new SettingsFragment();
                            FragmentManager fragmentManager = context.getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                        }
                        else {

                            Intent intentSpr = new Intent(context, HttpSpr.class);
                            context.startActivity(intentSpr);


                        }
                            break;
                    }

                    case 2: {
                        if (childPosition == 0)
                            Toast.makeText(context, "Первый!", Toast.LENGTH_SHORT).show();
                        else {Toast.makeText(context, "Второй!", Toast.LENGTH_LONG).show();
                            Intent intentWeather = new Intent(context, Weather.class);
                            context.startActivity(intentWeather);
                        }
                    }



                    default:
                        break;
                }

                navDrawer.closeDrawer(Gravity.LEFT);
                return true;
            }
        });

    }

    private void fillMenu() {
        listGroup = new ArrayList<String>();
        mapChild = new HashMap<String, List<String>>();

        listGroup.add(context.getResources()
                .getString(R.string.menu_operations));
        listGroup.add(context.getResources().getString(R.string.menu_settings));
        listGroup.add(context.getResources().getString(R.string.menu3));

        List<String> menu1 = new ArrayList<String>();
        for (String child : context.getResources().getStringArray(R.array.child_menu_operations)) {
            menu1.add(child);
        }

        List<String> menu2 = new ArrayList<String>();
        for (String child : context.getResources().getStringArray(
                R.array.child_menu_settings)) {
            menu2.add(child);
        }

        List<String> menu3 = new ArrayList<String>();
        for (String child : context.getResources().getStringArray(
                R.array.menu3_childs)) {
            menu3.add(child);
        }

        mapChild.put(listGroup.get(0), menu1);
        mapChild.put(listGroup.get(1), menu2);
        mapChild.put(listGroup.get(2), menu3);
    }

}

