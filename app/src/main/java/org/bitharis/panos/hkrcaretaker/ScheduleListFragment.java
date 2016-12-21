package org.bitharis.panos.hkrcaretaker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.EmployeeSchedule;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by panos on 12/21/2016.
 */

public class ScheduleListFragment extends ListFragment {


    private LinkedList<EmployeeSchedule> employeeSchedules;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getActivity().setTitle(R.string.schedule_title);

        employeeSchedules = new LinkedList<>();
        while(MySingleton.getInstance(getContext()).employeeSchedule.size()>0){
            this.employeeSchedules.offer(MySingleton.getInstance(getContext()).employeeSchedule.poll());
        }
        ScheduleAdapter adapter = new ScheduleAdapter(employeeSchedules);
        setListAdapter(adapter);
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
        public View getView(int position, View convertView, ViewGroup parent){
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
                    FragmentCommunicator fc = (FragmentCommunicator) getContext();
                    System.out.println("Button pressed");
                    // LOAD the new fragment
                }
            });

            return convertView;
        }
    }
}
