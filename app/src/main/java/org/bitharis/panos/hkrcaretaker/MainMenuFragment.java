package org.bitharis.panos.hkrcaretaker;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Employees;
import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Tasks;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by panos on 12/16/2016.
 */

public class MainMenuFragment extends Fragment {

    ImageButton contactsButton,scheduleBtn,taskBtn,notesBtn;


    protected FragmentCommunicator cfl;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Startying the service
        TaskFinderService.setServiceAlarm(getContext(),true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate) {

        View view = inflater.inflate(R.layout.main_menu, container, false);
        initializeViews(view);

        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    boolean isEmployeesEmpty = new getAllEmployees().execute().get();

                    if(isEmployeesEmpty){
                        Toast.makeText(getActivity(), "There are no employees available",Toast.LENGTH_LONG).show();
                    } else {
                        Log.i("MainFragment", "EmployeesFragment started");
                        cfl.replaceFragment(new EmployeesFragment());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //prompt the user to choose a View mode (Mothly , Weekly, Daily)
                cfl.replaceFragment(new ScheduleViewModeChooserDialog());
            }
        });

        taskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Task Button pressed");
                getTaskByEmpId gte = new getTaskByEmpId();
                try{
                    boolean result = gte.execute().get();
                    if(!result){
                        Toast.makeText(getActivity(), "There are no tasks available",Toast.LENGTH_LONG).show();
                    }else{
                        cfl.replaceFragment(new TaskListFragment());
                    }
                }catch(Exception ex){
                    Toast.makeText(getActivity(), "Ooops! this almost never happens!",Toast.LENGTH_LONG).show();
                }

            }
        });

        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Notes Button pressed");
            }
        });

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("Creating the fragment");
        cfl = (FragmentCommunicator) context;

    }

    //This method is used by the TaskFinderService to get the latest tasks
    public String getLastTaskId(String taskId){
        checkUpdatedTasks cut = new checkUpdatedTasks();
        String result = null;
        try {
            result = cut.execute(taskId).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }



    private void initializeViews(View v) {

        contactsButton = (ImageButton) v.findViewById(R.id.confBtn);
        scheduleBtn = (ImageButton) v.findViewById(R.id.scheduleBtn);
        taskBtn = (ImageButton) v.findViewById(R.id.taskBtn);
        notesBtn = (ImageButton) v.findViewById(R.id.notesBtn);
    }


    private class getTaskByEmpId extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean s=Boolean.FALSE;
            try {
                if(!MySingleton.employeeTasks.isEmpty()){
                    Tasks tasks = new Tasks();
                    tasks.setTaskId(MySingleton.getInstance(getContext()).employeeTasks.getLast().getTaskId());
                    int last_id_saved_on_phone = tasks.getTaskId();
                    String result = MySingleton.getInstance(getContext()).doGetPlainText("tasks/getupdatedtasklist/"+MySingleton.getInstance(getContext()).employeeID
                            +"&"+last_id_saved_on_phone);

                    if(!result.equals("[]")){
                        JSONArray jsonArray = new JSONArray(result);
                        System.out.println(jsonArray);
                        for(int i =0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            Gson gson = new Gson();
                            Tasks t;
                            t = gson.fromJson(jsonObject.toString(),Tasks.class);
                            MySingleton.getInstance(getContext()).employeeTasks.offer(t);

                            //save the last taskId to a sharedpref file.
                            if(i==jsonArray.length()-1){

                                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString
                                        (MySingleton.getInstance(getContext()).PREF_LAST_TASK_ID,t.getTaskId().toString()).commit();
                            }
                        }

                    }
                    s = Boolean.TRUE;

                }else{
                    String result = MySingleton.getInstance(getContext()).doGetPlainText("tasks/findactivetasksbyempid/"+MySingleton.getInstance(getContext()).employeeID);
                    if(!result.equals("[]")){
                        JSONArray jsonArray = new JSONArray(result);
                        System.out.println(jsonArray);

                        for(int i =0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            Gson gson = new Gson();
                            Tasks t;
                            t = gson.fromJson(jsonObject.toString(),Tasks.class);

                            MySingleton.getInstance(getContext()).employeeTasks.offer(t);

                            if(i==jsonArray.length()-1){

                                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString
                                        (MySingleton.getInstance(getContext()).PREF_LAST_TASK_ID,t.getTaskId().toString()).commit();
                            }
                        }
                    }
                    s = Boolean.TRUE;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }

    }

    private class getAllEmployees extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            String s = "";
            boolean isEmpty = true;
            try {

                s = MySingleton.getInstance(getContext()).doGetPlainText("employees");
                if(!s.equals("[]")){
                    JSONArray jsonArray = new JSONArray(s);
                    if(jsonArray.length() > 0){
                        isEmpty = false;
                    }
                    System.out.println(jsonArray);

                    for(int i =0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Gson gson = new Gson();
                        Employees t;
                        t = gson.fromJson(jsonObject.toString(), Employees.class);

                        MySingleton.getInstance(getContext()).employees.offer(t);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return isEmpty;
        }

    }

    private class checkUpdatedTasks extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String s="";
            String taskId = params[0];
            try {
                s = MySingleton.getInstance(getContext()).doGetJsonString("tasks/getupdatedtasklist/"+MySingleton.getInstance(getContext()).employeeID+"&"+taskId);
                System.out.println(s);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    }



}
