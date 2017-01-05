package org.bitharis.panos.hkrcaretaker;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Tasks;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by panos on 1/4/2017.
 */

public class TaskFinderService extends IntentService {

    private static final String TAG="TaskFinderService";
    private static final int QUERY_INTERVAL = 1000 * 20; //20 seconds
    private static Context c;
    private String taskId;


    public TaskFinderService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(TAG,"Received an Intent "+intent);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        taskId = prefs.getString(MySingleton.PREF_LAST_TASK_ID, null);
        System.out.println("TaskID "+taskId);


        //GET https://localhost:8181/MainServerREST/api/tasks/getupdatedtasklist/1&taskId
        //if(newTaskId>taskId){
        //notify user

        //TODO Fetch new TaskID from server
        String s = new MainMenuFragment().getLastTaskId(taskId);
        System.out.println(s);
        if(!s.equals("[]")){
            Resources r = getResources();
            PendingIntent pi = PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker(r.getString(R.string.new_task_title))
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle(r.getString(R.string.new_task_title))
                    .setContentText(r.getString(R.string.new_task_text))
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0,notification);
        }

    }

    public static void setServiceAlarm(Context context , boolean isOn){
        Intent i = new Intent(context, TaskFinderService.class);
        PendingIntent pi = PendingIntent.getService(context,0,i,0);
        c = context;
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);

        if(isOn){
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),QUERY_INTERVAL,pi);
        }else{
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    public static boolean isServiceAlarmOn(Context context){
        Intent i = new Intent(context,TaskFinderService.class);
        PendingIntent pi = PendingIntent.getService(context,0,i,PendingIntent.FLAG_NO_CREATE);

        return pi !=null;
    }


}
