package org.bitharis.panos.hkrcaretaker;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements FragmentCommunicator {



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);


        if (findViewById(R.id.frag_container) != null) {

            if (savedInstanceState != null) {
                return;
            }


            LoginFragment lgf = new LoginFragment();
            lgf.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.frag_container, lgf).commit();
        }


    }

    @Override
    public void replaceFragment(Fragment f) {

        //gets a fragment and checks what kind of Fragment subclass is instance of

            if (f instanceof MainMenuFragment ) {

                MainMenuFragment mmf = (MainMenuFragment) f;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frag_container, mmf);
                ft.addToBackStack(null);
                ft.commit();
            } else if (f instanceof RegistrationFragment) {
                RegistrationFragment rf = (RegistrationFragment) f;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frag_container, rf);
                ft.addToBackStack(null);
                ft.commit();

            } else if (f instanceof ScheduleListFragment) {
                ScheduleListFragment slf = (ScheduleListFragment) f;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frag_container, slf);
                ft.addToBackStack(null);
                ft.commit();
            } else if (f instanceof TaskListFragment) {
                TaskListFragment tlf = (TaskListFragment) f;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frag_container, tlf);
                ft.addToBackStack(null);
                ft.commit();
            }
    }

    @Override
    public void passStrings(String... params) {

        System.out.println("The first argument in params " + params[0]);
        if(params[0].equalsIgnoreCase("ConfirmRegistrationFragment")){
            ConfirmRegistrationFragment confirmRegistrationFragment = new ConfirmRegistrationFragment();
            Bundle args = new Bundle();
            args.putStringArray(confirmRegistrationFragment.FIELDS, params);
            confirmRegistrationFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frag_container, confirmRegistrationFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(params[0].equalsIgnoreCase("ScheduleDetailFragment")){
            SchedueDetailFragment schedueDetailFragment = new SchedueDetailFragment();
            Bundle args = new Bundle();
            args.putStringArray(SchedueDetailFragment.FIELDS, params);
            schedueDetailFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frag_container, schedueDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }



}
