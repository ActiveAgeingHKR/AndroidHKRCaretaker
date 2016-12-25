package org.bitharis.panos.hkrcaretaker;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
                try {
                    String s =  new getScheduleByDate().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Date cDate = new Date();
                String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

                System.out.println(fDate);

            }
        });

        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Schedule Button pressed");
                getScheduleByEmpId gsi = new getScheduleByEmpId();
                try {
                    boolean result = gsi.execute().get();
                    if(!result){
                        //if the string that was returned from the query is only two sqauare brakets
                        //that measns that there is no schedule for the employee
                        Toast.makeText(getActivity(), "There is no shcedule available",Toast.LENGTH_LONG).show();

                    }else{

                        cfl.replaceFragment(new ScheduleListFragment());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        taskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Task Button pressed");
                getTaskByEmpId gte = new getTaskByEmpId();
                try{
                    String result = gte.execute().get();
                    if(result.equals("[]")){
                        Toast.makeText(getActivity(), "There are no tasks available",Toast.LENGTH_LONG).show();
                    }else{
                        System.out.println("Inside the Listener");
                        JSONArray jsonArray = new JSONArray(result);
                        System.out.println(jsonArray);
                        for(int i =0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            Gson gson = new Gson();
                            Tasks t = new Tasks();
                            t = gson.fromJson(jsonObject.toString(),Tasks.class);
                            MySingleton.getInstance(getContext()).employeeTasks.offer(t);
                        }

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

    private class getScheduleByEmpId extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean s= Boolean.valueOf(false);
            try {
                //check if we have already downloaded a schedule.
                //if yes get the last our_schId and get check if there are any changes on the server.
                //if there are changes downloaded all the schedules WHERE schId > our_scheduleId

                if(!MySingleton.getInstance(getContext()).employeeSchedule.isEmpty()){
                    System.out.println("The employeeSchedule List is NOT EMPTY");
                    EmployeeSchedule employeeSchedule = new EmployeeSchedule();
                    employeeSchedule.setSchId(MySingleton.getInstance(getContext()).employeeSchedule.getLast().getSchId());
                    int our_schId = employeeSchedule.getSchId().intValue();
                    System.out.println("GET ALL THE SCHEDULE RECORDS THAT ARE LARGER THAN "+our_schId);
                    String res  =MySingleton.getInstance(getContext()).doGetPlainText("employeeschedule/getschidlargerthan/"+our_schId+"&"+MySingleton.getInstance(getContext()).employeeID);
                    System.out.println("response "+res);
                    LinkedList<EmployeeSchedule> es = new LinkedList<>();
                    if(!res.equals("[]")){
                        es = StringParser(res);
                        MySingleton.getInstance(getContext()).employeeSchedule.addAll(es);

                    }
                    s= Boolean.TRUE;



                }else{
                    System.out.println("The employeeSchedule List is EMPTY");
                    String res = MySingleton.getInstance(getContext()).doGetPlainText("employeeschedule/getempschedulebyempId/"+MySingleton.getInstance(getContext()).employeeID);
                    MySingleton.getInstance(getContext()).employeeSchedule = StringParser(res);
                    System.out.println("response "+res);
                    if(!MySingleton.getInstance(getContext()).employeeSchedule.isEmpty()){
                        System.out.println("We have filled the employeeSchedule List");
                        s = Boolean.TRUE;
                    }
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

    private class getTaskByEmpId extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String s="";
            try {

                s = MySingleton.getInstance(getContext()).doGetPlainText("tasks/findtaskbyempid/"+MySingleton.getInstance(getContext()).employeeID);


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

    private LinkedList<EmployeeSchedule> StringParser(String string){
        System.out.println("Raw string "+string);
        string = string.replace("[","");
        System.out.println("String after first replace() "+string);
        string = string.replace("]","");
        System.out.println("String after seconds replace() "+string);
        String[] employeeScheduleRecords = string.split(", ");
        System.out.println("Number of Records received: "+employeeScheduleRecords.length);
        LinkedList<EmployeeSchedule> employeeScheduleLinkedList = new LinkedList<>();

        for(int i=0; i<employeeScheduleRecords.length; i++){
            String[] recordFields = employeeScheduleRecords[i].split(";");
            System.out.println("Lentgh of recordFields = "+recordFields.length);
            EmployeeSchedule es = new EmployeeSchedule(recordFields[0],recordFields[1],recordFields[2],
                    recordFields[3],recordFields[4],recordFields[5],recordFields[6],recordFields[7],recordFields[8]);
            employeeScheduleLinkedList.offer(es);
        }

        return employeeScheduleLinkedList;

    }
}
