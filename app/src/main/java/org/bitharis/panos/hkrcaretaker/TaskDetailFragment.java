package org.bitharis.panos.hkrcaretaker;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Tasks;
import org.w3c.dom.Text;

import java.util.Arrays;

/**
 * Created by panos on 12/22/2016.
 */

public class TaskDetailFragment extends Fragment {

    public final static String FIELDS = "org.bitharis.panos.hkrcaretaker";
    private FragmentCommunicator cfl;
    private String[] params;
    private TextView task_title_textView, due_date_textView, task_details_txt;
    private Button reminder;
    private CheckBox completed;
    private String taskID;
    private String taskTitle;
    private String taskContent;
    private String taskDueDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate) {
        View view = inflater.inflate(R.layout.task_detail, container, false);
        //String in index [0] contains the Fragment of origin. It should never be used to fill the forms

        params = getArguments().getStringArray(FIELDS);

        task_title_textView = (TextView) view.findViewById(R.id.task_title_textView);
        due_date_textView = (TextView) view.findViewById(R.id.due_date_textView);
        task_details_txt = (TextView) view.findViewById(R.id.task_details_txt);

        reminder = (Button) view.findViewById(R.id.add_reminder_btn);
        completed = (CheckBox) view.findViewById(R.id.task_ckeckbox);


        task_title_textView.setText(params[1]);
        due_date_textView.setText(params[2]);
        task_details_txt.setText(params[3]);
        taskID = params[4];
        taskTitle = params[1];
        taskDueDate = params[2];
        taskContent = params[3];


        completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                updateTaskStatus(b);
            }
        });

        return view;

    }

    private void updateTaskStatus(boolean b){
        new updateStaskStatusAsync().execute(b);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("Creating the fragment");
        cfl = (FragmentCommunicator) context;

    }

    private class updateStaskStatusAsync extends AsyncTask<Boolean, Void, Void>{

        @Override
        protected Void doInBackground(Boolean... booleen) {

            boolean status = booleen[0].booleanValue();
            Gson gson = new Gson();
            Tasks task = new Tasks(Integer.parseInt(taskID),taskTitle,taskContent,taskDueDate,status);
            String jsonString = gson.toJson(task);
            System.out.println("JSON STRING "+jsonString);
            try {
                MySingleton.getInstance(getContext()).sendPut("tasks/1",jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
