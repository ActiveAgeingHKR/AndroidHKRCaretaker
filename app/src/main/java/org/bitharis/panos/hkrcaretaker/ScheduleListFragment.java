package org.bitharis.panos.hkrcaretaker;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.EmployeeSchedule;
import java.util.LinkedList;

import javax.xml.transform.sax.SAXSource;

/**
 * Created by panos on 12/21/2016.
 */

public class ScheduleListFragment extends ListFragment {


    private LinkedList<EmployeeSchedule> employeeSchedules;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

//        employeeSchedules = new LinkedList<>();
//        while(MySingleton.getInstance(getContext()).employeeSchedule.size()>0){
//            this.employeeSchedules.offer(MySingleton.getInstance(getContext()).employeeSchedule.poll());
//        }
        ScheduleAdapter adapter = new ScheduleAdapter(MySingleton.getInstance(getContext()).employeeSchedule);
        setListAdapter(adapter);
        //enable the actionbar menu
        setHasOptionsMenu(true);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        EmployeeSchedule es = ((ScheduleAdapter)getListAdapter()).getItem(position);


    }

    class ScheduleAdapter extends ArrayAdapter<EmployeeSchedule>{

        public ScheduleAdapter(LinkedList<EmployeeSchedule> schedules) {
            super(getActivity(),0,schedules);

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_schedule_list,null);
            }

            EmployeeSchedule es = getItem(position);

            TextView customerName =
                    (TextView) convertView.findViewById(R.id.schedule_list_item_customernameTextView);
            customerName.setText(es.getCustomersCuId().getCuLastname()+" "+es.getCustomersCuId().getCuFirstname());

            TextView dateTextView =
                    (TextView) convertView.findViewById(R.id.schedule_list_item_dateTextView);
            dateTextView.setText(es.getSchDate());
            Button detailsButton =(Button) convertView.findViewById(R.id.scheduleDetailsButton);
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println("Button pressed");
                    // LOAD the new fragment
                    FragmentCommunicator cfl = (FragmentCommunicator) getContext();
                    LinkedList<EmployeeSchedule> employeeSchedules = MySingleton.getInstance(getContext()).employeeSchedule;
                    String custname = employeeSchedules.get(position).getCustomersCuId().getCuLastname();
                    custname = custname +" "+employeeSchedules.get(position).getCustomersCuId().getCuFirstname();
                    String date = employeeSchedules.get(position).getSchDate();
                    String address = employeeSchedules.get(position).getCustomersCuId().getCuAddress();
                    String from = employeeSchedules.get(position).getSchFromTime();
                    String until = employeeSchedules.get(position).getSchUntilTime();
                    String fragmentOrigin = "ScheduleDetailFragment";
                    cfl.passStrings(fragmentOrigin,custname,address,date,from,until);
                    cfl.replaceFragment(new SchedueDetailFragment());
                }
            });

            return convertView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                System.out.println("Action Settings is pushed");
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                System.out.println("Action Favorite is pushed");
                return true;
            case R.id.search_item:
                getActivity().onSearchRequested();
                System.out.println("Search Button is pressed");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu,menu);
    }
}
