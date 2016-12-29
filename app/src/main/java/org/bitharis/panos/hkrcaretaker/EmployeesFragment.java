package org.bitharis.panos.hkrcaretaker;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Employees;
import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Tasks;

import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeesFragment extends ListFragment {

    private FragmentCommunicator cfl;
    private final String TAG = "EmployeesFragment";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "on create");
        getActivity().setTitle("Your Employees");

        employeeAdapter adapter = new employeeAdapter(MySingleton.getInstance(getContext()).employees);
        setListAdapter(adapter);
        Log.i(TAG, "on create finished");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Employees employee = ((EmployeesFragment.employeeAdapter)getListAdapter()).getItem(position);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("Creating the fragment");
        cfl = (FragmentCommunicator) context;
    }

    class employeeAdapter extends ArrayAdapter<Employees> {

        public employeeAdapter(LinkedList<Employees> employees) {
            super(getActivity(),0, employees);

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_employees, null);
            }

            Employees employee = getItem(position);

            TextView employeeFullName =
                    (TextView) convertView.findViewById(R.id.employeeFullName);
            employeeFullName.setText(employee.getEmpFirstname().toString() + " " + employee.getEmpLastname().toString());

            TextView phoneNumber =
                    (TextView) convertView.findViewById(R.id.phoneNumber);
            phoneNumber.setText(employee.getEmpPhone());

            Button callButton =(Button) convertView.findViewById(R.id.callButton);
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Employees employee = getItem(position);
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + employee.getEmpPhone()));
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }
}
