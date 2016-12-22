package org.bitharis.panos.hkrcaretaker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.FieldChecker;


/**
 * Created by panos on 12/18/2016.
 */

public class RegistrationFragment extends Fragment {
    EditText fname,lname,phone;
    Button next;

    protected FragmentCommunicator cfl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate) {
        View view = inflater.inflate(R.layout.registration_form, container, false);
        initializeViews(view);
        final String[] params = new String[4];

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int validityFlag=0;
                FieldChecker fc = new FieldChecker();

                //This is used in MainActivity to identify the Fragment origin of the parameters
                params[0] = "ConfirmRegistrationFragment";

                validityFlag++;
                if(fc.checkNameTypeStrings(fname.getText().toString())){
                    params[1] = fname.getText().toString();
                    validityFlag++;
                }else{
                    Toast.makeText(getActivity(), "The field cannot be blank",Toast.LENGTH_LONG).show();
                }
                if(fc.checkNameTypeStrings(lname.getText().toString())){
                    params[2] = lname.getText().toString();
                    validityFlag++;
                }else{
                    Toast.makeText(getActivity(), "The field cannot be blank",Toast.LENGTH_LONG).show();
                }
                if(fc.checkPhoneTypeStrings(phone.getText().toString())){
                    params[3] = phone.getText().toString();
                    validityFlag++;
                }else{
                    Toast.makeText(getActivity(), "The field cannot be blank",Toast.LENGTH_LONG).show();
                }

                if(validityFlag==4){
                    System.out.println(params[0]+" "+params[1]+" "+params[2]);
                    validityFlag=0;
                    cfl.passStrings(params);
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

    private void initializeViews(View v){
        fname = (EditText) v.findViewById(R.id.fname);
        lname = (EditText) v.findViewById(R.id.lname);
        phone = (EditText) v.findViewById(R.id.phone);
        next = (Button) v.findViewById(R.id.Next);
    }
}
