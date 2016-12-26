package org.bitharis.panos.hkrcaretaker;


import android.content.Context;
import android.os.Bundle;
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

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.EmployeeSchedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by panos on 12/21/2016.
 */

public class ScheduleListFragment extends ListFragment {
    public static final String MODES = "org.bitharis.panos.hkrcaretaker";
    private FragmentCommunicator cfl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Your Schedule");


        String parameter = this.getArguments().getString(MODES);
        System.out.println("Parameters passed to ScheduleListFragment" + parameter);

        ScheduleAdapter adapter = null;

        if (parameter.equalsIgnoreCase("month")) {
            LinkedList<EmployeeSchedule> monthSchedules = new LinkedList<>();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = null;
            Date ceilingDate = null;
            try {
                Calendar cal = Calendar.getInstance();

                //Current day
                cal.add(Calendar.DATE, -1);
                String currentDayString = dateFormat.format(cal.getTime());
                currentDate = dateFormat.parse(currentDayString);

                //Onemonth from now
                cal.add(Calendar.MONTH, 1);
                String oneMonthFromNowDateString = dateFormat.format(cal.getTime());
                ceilingDate = dateFormat.parse(oneMonthFromNowDateString);

                for (EmployeeSchedule es : MySingleton.getInstance(getContext()).employeeSchedule) {
                    String schDate = es.getSchDate();

                    Date scheduleDate = dateFormat.parse(schDate);
                    if (scheduleDate.after(currentDate) && scheduleDate.before(ceilingDate)) {
                        monthSchedules.offer(es);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            adapter = new ScheduleAdapter(monthSchedules);
        } else if (parameter.equalsIgnoreCase("week")) {
            LinkedList<EmployeeSchedule> weekSchedules = new LinkedList<>();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = null;
            Date ceilingDate = null;
            try {
                Calendar cal = Calendar.getInstance();

                //Current day
                cal.add(Calendar.DATE, -1);
                String currentDayString = dateFormat.format(cal.getTime());
                currentDate = dateFormat.parse(currentDayString);

                //One week from now
                cal.add(Calendar.DATE, 7);
                String oneMonthFromNowDateString = dateFormat.format(cal.getTime());
                ceilingDate = dateFormat.parse(oneMonthFromNowDateString);

                for (EmployeeSchedule es : MySingleton.getInstance(getContext()).employeeSchedule) {
                    String schDate = es.getSchDate();

                    Date scheduleDate = dateFormat.parse(schDate);
                    if (scheduleDate.after(currentDate) && scheduleDate.before(ceilingDate)) {
                        weekSchedules.offer(es);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            adapter = new ScheduleAdapter(weekSchedules);

        } else if (parameter.equalsIgnoreCase("day")) {
            LinkedList<EmployeeSchedule> todaySchedule = new LinkedList<>();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = null;

            try {
                Calendar cal = Calendar.getInstance();

                //Current day
                cal.add(Calendar.DATE, 0);
                String currentDayString = dateFormat.format(cal.getTime());
                currentDate = dateFormat.parse(currentDayString);

                for (EmployeeSchedule es : MySingleton.getInstance(getContext()).employeeSchedule) {
                    String schDate = es.getSchDate();

                    Date scheduleDate = dateFormat.parse(schDate);
                    if (scheduleDate.equals(currentDate)) {
                        todaySchedule.offer(es);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            adapter = new ScheduleAdapter(todaySchedule);

        } else {
            adapter = new ScheduleAdapter(MySingleton.getInstance(getContext()).employeeSchedule);
        }

        //THIS LINE MUST CHANGE I NEED ONE FOR EACH IF-ELSE
//        ScheduleAdapter adapter = new ScheduleAdapter(MySingleton.getInstance(getContext()).employeeSchedule);
        setListAdapter(adapter);
        //enable the actionbar menu
        setHasOptionsMenu(true);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        EmployeeSchedule es = ((ScheduleAdapter) getListAdapter()).getItem(position);


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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("Creating the fragment");
        cfl = (FragmentCommunicator) context;
    }

    class ScheduleAdapter extends ArrayAdapter<EmployeeSchedule> {

        public ScheduleAdapter(LinkedList<EmployeeSchedule> schedules) {
            super(getActivity(), 0, schedules);

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_schedule_list, null);
            }

            EmployeeSchedule es = getItem(position);

            TextView customerName =
                    (TextView) convertView.findViewById(R.id.schedule_list_item_customernameTextView);
            customerName.setText(es.getCustomersCuId().getCuLastname() + " " + es.getCustomersCuId().getCuFirstname());

            TextView dateTextView =
                    (TextView) convertView.findViewById(R.id.schedule_list_item_dateTextView);
            dateTextView.setText(es.getSchDate());
            Button detailsButton = (Button) convertView.findViewById(R.id.scheduleDetailsButton);
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println("Button pressed");
                    // LOAD the new fragment
                    FragmentCommunicator cfl = (FragmentCommunicator) getContext();
                    LinkedList<EmployeeSchedule> employeeSchedules = MySingleton.getInstance(getContext()).employeeSchedule;
                    String custname = employeeSchedules.get(position).getCustomersCuId().getCuLastname();
                    custname = custname + " " + employeeSchedules.get(position).getCustomersCuId().getCuFirstname();
                    String date = employeeSchedules.get(position).getSchDate();
                    String address = employeeSchedules.get(position).getCustomersCuId().getCuAddress();
                    String from = employeeSchedules.get(position).getSchFromTime();
                    String until = employeeSchedules.get(position).getSchUntilTime();
                    //We declare what is the Fragment that will receive these information
                    String destFragment = "ScheduleDetailFragment";
                    cfl.passStrings(destFragment, custname, address, date, from, until);
                    cfl.replaceFragment(new ScheduleDetailFragment());
                }
            });

            return convertView;
        }
    }
}
