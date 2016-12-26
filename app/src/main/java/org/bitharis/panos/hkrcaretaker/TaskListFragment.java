package org.bitharis.panos.hkrcaretaker;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.EmployeeSchedule;
import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Tasks;

import java.util.LinkedList;

/**
 * Created by panos on 12/21/2016.
 */

public class TaskListFragment extends ListFragment {



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        getActivity().setTitle("Your Tasks");

        taskAdapter adapter = new taskAdapter(MySingleton.getInstance(getContext()).employeeTasks);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Tasks task = ((taskAdapter)getListAdapter()).getItem(position);


    }

    class taskAdapter extends ArrayAdapter<Tasks>{

        public taskAdapter(LinkedList<Tasks> tasks) {
            super(getActivity(),0,tasks);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_task_list,null);
            }

            Tasks task = getItem(position);

            TextView taskTitle =
                    (TextView) convertView.findViewById(R.id.task_list_item_taskTitleTextView);
            taskTitle.setText(task.getTaskTitle().toString());

            TextView taskDueDate =
                    (TextView) convertView.findViewById(R.id.task_list_item_dueDateTextView);
            taskDueDate.setText(task.getTaskdueDate().toString());
            Button detailsButton =(Button) convertView.findViewById(R.id.taskDetailsButton);
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
