package org.bitharis.panos.hkrcaretaker;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


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
            }  else if (f instanceof EmployeesFragment) {
                EmployeesFragment ef = (EmployeesFragment) f;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frag_container, ef);
                ft.addToBackStack(null);
                ft.commit();
            }
            else if (f instanceof ScheduleViewModeChooserDialog) {
                System.out.println("Creating the ScheduleViewModeDialog Fragment");
                ScheduleViewModeChooserDialog tlf = (ScheduleViewModeChooserDialog) f;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                tlf.show(ft, "TAG");

            }
    }

    @Override
    public void passStrings(String... params) {

        //Here we check which Fragment should receive these params
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
            ScheduleDetailFragment scheduleDetailFragment = new ScheduleDetailFragment();
            Bundle args = new Bundle();
            args.putStringArray(ScheduleDetailFragment.FIELDS, params);
            scheduleDetailFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frag_container, scheduleDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(params[0].equalsIgnoreCase("ScheduleViewModeChooser")){
            ScheduleListFragment scheduleListFragment = new ScheduleListFragment();
            Bundle args = new Bundle();
            args.putString(ScheduleListFragment.MODES,params[1]);
            scheduleListFragment.setArguments(args);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frag_container,scheduleListFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if(params[0].equalsIgnoreCase("TaskDetailFragment")){
            TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
            Bundle args = new Bundle();
            args.putStringArray(TaskDetailFragment.FIELDS,params);
            taskDetailFragment.setArguments(args);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frag_container,taskDetailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }



}
