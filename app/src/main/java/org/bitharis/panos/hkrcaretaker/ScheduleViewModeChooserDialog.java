package org.bitharis.panos.hkrcaretaker;


/**
 * Created by panos on 12/25/2016.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.EmployeeSchedule;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;


/**
 * This dialog window pops up and lets the use choose how the schedule will be presented
 * The choices are
 * 1. Daily View -> Will show only the current day's schedule
 * 2. Weekly View -> Will show only the current week's schedule
 * 3. Monthly View -> Will show only the current Month's schedule
 */
public class ScheduleViewModeChooserDialog extends DialogFragment {

    private FragmentCommunicator cfl;

    @Override
    public AlertDialog onCreateDialog(Bundle SavedInstanceState) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.choose_schedule_view);

        builder.setItems(R.array.schedule_view_modes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        System.out.println("Choice: "+i);
                        getSchedule();
                        cfl.passStrings("ScheduleViewModeChooser","month");
                        return;
                    case 1:
                        System.out.println("Choice: "+i);
                        getSchedule();
                        cfl.passStrings("ScheduleViewModeChooser","week");
                        return;

                    case 2:
                        System.out.println("Choice: "+i);
                        getSchedule();
                        cfl.passStrings("ScheduleViewModeChooser","day");
                        return;
                }
            }


        });


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("Creating the fragment");
        cfl = (FragmentCommunicator) context;
    }

    private void getSchedule(){
        try {
            //Get the schedule
            getScheduleByEmpId gsi = new getScheduleByEmpId();
            boolean result = gsi.execute().get();
            if (!result) {
                Toast.makeText(getActivity(), "There is no shcedule available", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class getScheduleByEmpId extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean s = Boolean.valueOf(false);
            try {
                //check if we have already downloaded a schedule.
                //if yes get the last our_schId and get check if there are any changes on the server.
                //if there are changes downloaded all the schedules WHERE schId > our_scheduleId

                if (!MySingleton.getInstance(getContext()).employeeSchedule.isEmpty()) {
                    EmployeeSchedule employeeSchedule = new EmployeeSchedule();
                    employeeSchedule.setSchId(MySingleton.getInstance(getContext()).employeeSchedule.getLast().getSchId());
                    int last_schId_saved_on_phone = employeeSchedule.getSchId().intValue();
                    System.out.println("GET ALL THE SCHEDULE RECORDS THAT ARE LARGER THAN " + last_schId_saved_on_phone);
                    String res = MySingleton.getInstance(getContext()).doGetPlainText("employeeschedule/getschidlargerthan/" + last_schId_saved_on_phone + "&" + MySingleton.getInstance(getContext()).employeeID);
                    System.out.println("response " + res);
                    LinkedList<EmployeeSchedule> es = new LinkedList<>();
                    if (!res.equals("[]")) {
                        es = StringParser(res);
                        MySingleton.getInstance(getContext()).employeeSchedule.addAll(es);

                    }
                    s = Boolean.TRUE;

                } else {

                    String res = MySingleton.getInstance(getContext()).doGetPlainText("employeeschedule/getempschedulebyempId/" + MySingleton.getInstance(getContext()).employeeID);
                    MySingleton.getInstance(getContext()).employeeSchedule = StringParser(res);
                    System.out.println("response " + res);
                    if (!MySingleton.getInstance(getContext()).employeeSchedule.isEmpty()) {
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

    private LinkedList<EmployeeSchedule> StringParser(String string) {
        System.out.println("Raw string " + string);
        string = string.replace("[", "");
        System.out.println("String after first replace() " + string);
        string = string.replace("]", "");
        System.out.println("String after seconds replace() " + string);
        String[] employeeScheduleRecords = string.split(", ");
        System.out.println("Number of Records received: " + employeeScheduleRecords.length);
        LinkedList<EmployeeSchedule> employeeScheduleLinkedList = new LinkedList<>();

        for (int i = 0; i < employeeScheduleRecords.length; i++) {
            String[] recordFields = employeeScheduleRecords[i].split(";");
            System.out.println("Lentgh of recordFields = " + recordFields.length);
            EmployeeSchedule es = new EmployeeSchedule(recordFields[0], recordFields[1], recordFields[2],
                    recordFields[3], recordFields[4], recordFields[5], recordFields[6], recordFields[7], recordFields[8]);
            employeeScheduleLinkedList.offer(es);
        }

        return employeeScheduleLinkedList;

    }


}
