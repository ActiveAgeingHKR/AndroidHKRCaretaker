package org.bitharis.panos.hkrcaretaker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by panos on 12/22/2016.
 */


public class SchedueDetailFragment extends Fragment{

    private FragmentCommunicator cfl;
    private  String [] params;
    public final static String FIELDS = "org.bitharis.panos.hkrcaretaker";
    private TextView custName,address,schdate,from,until;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate) {
        View view = inflater.inflate(R.layout.schedule_detail, container, false);

        System.out.println("FIELDS--->"+FIELDS);
        //String in index [0] contains the Fragment of origin. It should never be used to fill the forms
        params = this.getArguments().getStringArray(FIELDS);
        custName = (TextView)view.findViewById(R.id.custname_txt);
        address = (TextView) view.findViewById(R.id.custadd_txt);
        schdate = (TextView) view.findViewById(R.id.schdate_txt);
        from = (TextView) view.findViewById(R.id.schfrom_txt);
        until = (TextView) view.findViewById(R.id.schuntil_txt);

        custName.setText(params[1]);
        address.setText(params[2]);
        schdate.setText(params[3]);
        from.setText(params[4]);
        until.setText(params[5]);


        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("Creating the fragment");
        cfl = (FragmentCommunicator) context;

    }
}
