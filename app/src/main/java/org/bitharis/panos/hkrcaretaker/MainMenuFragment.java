package org.bitharis.panos.hkrcaretaker;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Customers;
import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.EmployeeSchedule;
import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Tasks;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

/**
 * Created by panos on 12/16/2016.
 */

public class MainMenuFragment extends Fragment {




    ImageButton confBtn,scheduleBtn,taskBtn,notesBtn;


    protected FragmentCommunicator cfl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate) {

        View view = inflater.inflate(R.layout.main_menu, container, false);
        initializeViews(view);

        confBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                try {
//                   // String s =  new getScheduleByDate().execute().get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//                Date cDate = new Date();
//                String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
//
//                System.out.println(fDate);


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



    private void initializeViews(View v) {

        confBtn = (ImageButton) v.findViewById(R.id.confBtn);
        scheduleBtn = (ImageButton) v.findViewById(R.id.scheduleBtn);
        taskBtn = (ImageButton) v.findViewById(R.id.taskBtn);
        notesBtn = (ImageButton) v.findViewById(R.id.notesBtn);
    }

    private class getScheduleByDate extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String s="";
            try {


                Date cDate = new Date();
                String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
//
                s = MySingleton.getInstance(getContext()).doGetJsonString("employeeschedule/date/"+fDate);
                System.out.println("response "+s);

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

    private class getNotesByCustId extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String s="";
            try {

                s = MySingleton.getInstance(getContext()).doGetPlainText("notes/findtaskbyempid/"+MySingleton.getInstance(getContext()).employeeID);


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
