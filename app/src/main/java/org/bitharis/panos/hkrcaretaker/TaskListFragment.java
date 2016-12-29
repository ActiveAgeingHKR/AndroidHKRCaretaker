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
import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Tasks;

import java.util.LinkedList;

/**
 * Created by panos on 12/21/2016.
 */

public class TaskListFragment extends ListFragment {
    private FragmentCommunicator cfl;



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
        public View getView(final int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_task_list,null);
            }

            Tasks task = getItem(position);

            TextView taskTitle =
                    (TextView) convertView.findViewById(R.id.task_list_item_taskTitleTextView);
            taskTitle.setText(task.getTaskTitle().toString());

            TextView taskDueDate =
                    (TextView) convertView.findViewById(R.id.task_list_item_dueDateTextView);
            taskDueDate.setText(task.getTaskdueDate());

            Button detailsButton =(Button) convertView.findViewById(R.id.taskDetailsButton);
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println("Button pressed");
                    // LOAD the new fragment
                    FragmentCommunicator cfl = (FragmentCommunicator) getContext();
                    LinkedList<Tasks> tasksLinkedList = MySingleton.getInstance(getContext()).employeeTasks;



                    String taskTitle = tasksLinkedList.get(position).getTaskTitle();

                    String dueDate = tasksLinkedList.get(position).getTaskdueDate();

                    String taskDetails = tasksLinkedList.get(position).getTaskContent();

                    String taskID = tasksLinkedList.get(position).getTaskId().toString();
                    System.out.println(taskID);



                    //We declare what is the Fragment that will receive these information
                    System.out.println("TESTING PARAMS Title:"+taskTitle+" \nDueDate:"+dueDate+" \nDetails"+taskDetails);
                    String destFragment = "TaskDetailFragment";
                    cfl.passStrings(destFragment, taskTitle, dueDate, taskDetails,taskID);
                    cfl.replaceFragment(new TaskDetailFragment());
                }
            });

            return convertView;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("Creating the fragment");
        cfl = (FragmentCommunicator) context;
    }
}
