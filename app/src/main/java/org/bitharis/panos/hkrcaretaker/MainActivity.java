package org.bitharis.panos.hkrcaretaker;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentCommunicator {


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
        if (f instanceof MainMenuFragment) {
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

        } else if(f instanceof ScheduleListFragment){
            ScheduleListFragment slf = (ScheduleListFragment) f;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frag_container, slf);
            ft.addToBackStack(null);
            ft.commit();
        }else if(f instanceof TaskListFragment){
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
        ConfirmRegistrationFragment confirmRegistrationFragment = new ConfirmRegistrationFragment();
        Bundle args = new Bundle();

        args.putStringArray(confirmRegistrationFragment.FIELDS,params);
        confirmRegistrationFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container, confirmRegistrationFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
