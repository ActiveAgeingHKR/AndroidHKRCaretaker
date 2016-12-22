package org.bitharis.panos.hkrcaretaker;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Employees;
import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Managers;

import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by panos on 12/18/2016.
 */

public class ConfirmRegistrationFragment extends Fragment {

    EditText fname2, lname2, phone2, uname, email, pass;
    Button confirm;
    public final static String FIELDS = "org.bitharis.panos.hkrcaretaker";
    private String sFname;
    private String sLname;
    private String sPhone;
    private String sUsername;
    private String sEmail;
    private String sPass;
    String[] params;

    protected FragmentCommunicator cfl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate) {

        //String in index [0] contains the Fragment of origin. It should never be used to fill the forms
        params = this.getArguments().getStringArray(FIELDS);
        View view = inflater.inflate(R.layout.registration_form2, container, false);

        initializeViews(view);
        //fill the registration fields automatically
        fillRegistrationFields();
        fname2.setText(sFname);
        lname2.setText(sLname);
        phone2.setText(sPhone);
        uname.setText(sUsername);
        email.setText(sEmail);
        System.out.println("Message passed " + sFname + " " + sLname + " " + sPhone + " " + sUsername + " " + sEmail);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Bad Inegineering Notice:
                 * check one last time for changes and validity in the fields and also add the password
                 * Maybe the user accidentaly changed something here
                 */

                if(pass.getText().toString().length()>=4){
                    sPass = pass.getText().toString();

                    /**
                     * Bad engineering Notice!
                     * normally an if statement should be here that checks if there is
                     * someone with the same username and email in the database
                     * will be added later if I have time
                     */

                    try {
                        String response = new postData().execute().get();
                        if(response.equalsIgnoreCase("204")){
                            Toast.makeText(getActivity(), "The registration was successful",Toast.LENGTH_LONG).show();
                        }else if(response.equalsIgnoreCase("500")){
                            Toast.makeText(getActivity(), "Error! The username or email already exist.",Toast.LENGTH_LONG).show();
                            Random random = new Random();
                            int number = random.nextInt(10);
                            try {
                                //if the username or email already exists modify the username by adding one more leter from the name
                                //Possible outOfArrayException here in case of a small first name
                                sUsername = params[1].substring(0, 4) + params[1].substring(0, 3);
                            }catch(Exception ex){
                                //screw it, append a random number after the username.
                                sUsername = params[1].substring(0, 3) + params[1].substring(0, 3)+number;
                            }
                            sEmail = params[1] + "." + params[2] + number+"@hkr.caretaker.com";
                            uname.setText(sUsername);
                            email.setText(sEmail);

                        }else{
                            Toast.makeText(getActivity(), "Oops! This is an unknown error! Too many candies..",Toast.LENGTH_LONG).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

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

    private void fillRegistrationFields() {
        sFname = params[1];
        sLname = params[2];
        sPhone = params[3];
        sUsername = params[1].substring(0, 3) + params[2].substring(0, 3);
        sEmail = params[1] + "." + params[2] + "@hkr.caretaker.com";

    }

    private void initializeViews(View v) {
        fname2 = (EditText) v.findViewById(R.id.fnameTwo);
        lname2 = (EditText) v.findViewById(R.id.lnameTwo);
        phone2 = (EditText) v.findViewById(R.id.phoneTwo);
        uname = (EditText) v.findViewById(R.id.uname);
        email = (EditText) v.findViewById(R.id.email);
        pass = (EditText) v.findViewById(R.id.pass);
        confirm = (Button) v.findViewById(R.id.confirm);


    }

    private class postData extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String s="";
            try {
                //In the future if we have more than one Managers this line must change
                Managers man = new Managers(1);

                //Setting up the employee object
                Employees e = new Employees(man,sFname,sLname,sUsername,sPass,sEmail,sPhone,false);
                Gson gson = new Gson();
                String jsonString = new String(gson.toJson(e));
                System.out.println("JsonString "+jsonString);

                //send away !!!
                 s = MySingleton.getInstance(getContext()).sendPostJsonString("employees/",jsonString);
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
}
