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
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


/**
 * Created by panos on 12/16/2016.
 */

public class LoginFragment extends Fragment {

    protected EditText mUsername;
    protected EditText mPassword;
    protected Button login;
    protected Button register;
    protected FragmentCommunicator cfl;
    protected String sUsername,sPassword;
    protected CheckBox savechkbox;
    private final String filename = "savedCredentials.ser";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate) {
        View view = inflater.inflate(R.layout.login, container, false);

        mUsername = (EditText) view.findViewById(R.id.username_text);
        mPassword = (EditText) view.findViewById(R.id.password_text);
        savechkbox = (CheckBox) view.findViewById(R.id.save_checkbox);

        File credentials = getContext().getFileStreamPath(filename);
        //Check if there is a file with saved credentials inside the device from a previous login
        if(credentials.exists()){
            System.out.println("FOUND THE FILE");
            try {
                //load the credentials from the file and asssigne the values to the corresponding variables sUsername and sPassword
                FileReader fir = new FileReader(getContext().getFileStreamPath(filename));
                BufferedReader br = new BufferedReader(fir);
                sUsername = br.readLine();
                sPassword = br.readLine();
                br.close();
                fir.close();

                //Auto-fill the EditText views with the credentials
                mUsername.setText(sUsername);
                mPassword.setText(sPassword);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("FILE NOT FOUND");
        }

        login = (Button) view.findViewById(R.id.log_button);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getData gd = new getData();
                postData pd = new postData();
                try {
                    //Read the credentials from the EditText views (Thei can be already filled by the previous if-else statement)
                    if(mPassword.getText().toString().length()>0 &&
                            mUsername.getText().toString().length()>0){
                        sUsername = mUsername.getText().toString();
                        sPassword = mPassword.getText().toString();

                    }

                    //Send username and password for authorization
                    String responseCode = pd.execute(sUsername+"-"+sPassword).get();


                    if(responseCode.equalsIgnoreCase("200")){
                        //the authentication was successfull
                        System.out.println("Authentication successful!");

                        //set the employeeID  in Singleton class
                        String employee_string = gd.execute(sUsername).get();
                        MySingleton.getInstance(getContext()).employeeID = employee_string;
                        System.out.println(employee_string);

                        if(savechkbox.isChecked()){
                            System.out.println("Saving credentials in file...");
                            //if the checkbox is checked save the username and pass to a file

                            File savedCredetialsFile = new File(getContext().getFilesDir(),filename);
                            try{
                                FileWriter fir = new FileWriter(savedCredetialsFile);
                                //write the username and append a newline
                                System.out.println("Writting username: "+sUsername +" and password: "+sPassword+" to file.");
                                fir.write((sUsername+"\n"));
                                fir.write((sPassword+"\n"));
                                fir.flush();
                                fir.close();

                            }catch(Exception ex){
                                System.out.println(ex.getStackTrace());
                            }

                            System.out.println(getContext().getFilesDir().toString());
                        }


                        //change to main menu fragment
                        cfl.replaceFragment(new MainMenuFragment());
                    }else if(responseCode.equalsIgnoreCase("403")){
                        //The authentication was unsuccessful
                        System.out.println("Wrong Username or Password");
                        Toast.makeText(getActivity(), "Invalid username or password or this is an unregistered account.",Toast.LENGTH_LONG).show();
                    }else{
                        System.out.println("Unknown authentication error");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        register = (Button)  view.findViewById(R.id.reg_button);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                cfl.replaceFragment(new RegistrationFragment());
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

    private class postData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String s="";
            try {

                String sUsernameAndPass = strings[0];

                System.out.println("Parameter sent "+sUsernameAndPass);

                //send away !!!
                s = MySingleton.getInstance(getContext()).sendPost("employees/login/",sUsernameAndPass);
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

    private class getData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String s="";
            try {

                String sUsername = strings[0];

                System.out.println("Parameter sent "+sUsername);
                s = MySingleton.getInstance(getContext()).doGetPlainText("employees/idbyusername/"+sUsername);
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
