package org.bitharis.panos.hkrcaretaker;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.LinkedList;
import org.bitharis.panos.hkrcaretaker.org.bitharis.panos.entities.Notes;

/**
 * Created by panos on 12/21/2016.
 */

public class NotesListFragment extends ListFragment {


    private LinkedList<Notes> employeesNotes;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Your Notes");

        employeesNotes = new LinkedList<>();
        while(MySingleton.getInstance(getContext()).employeeNotes.size()>0){
            this.employeesNotes.offer(MySingleton.getInstance(getContext()).employeeNotes.poll());
        }
        NotesAdapter adapter = new NotesAdapter(employeesNotes);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Notes es = ((NotesAdapter)getListAdapter()).getItem(position);


    }

    class NotesAdapter extends ArrayAdapter<Notes>{

        public NotesAdapter(LinkedList<Notes> notes) {
            super(getActivity(),0,notes);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_notes_list,null);
            }

            Notes es = getItem(position);

            TextView noteTitle =
                    (TextView) convertView.findViewById(R.id.note_list_item_noteTitleTextView);
            noteTitle.setText(es.getNoteTitle());

            TextView dateTextView =
                    (TextView) convertView.findViewById(R.id.note_list_item_contentTextView);
            dateTextView.setText(es.getContent().substring(0,25));
            Button detailsButton =(Button) convertView.findViewById(R.id.noteDetailsButton);
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentCommunicator fc = (FragmentCommunicator) getContext();
                    System.out.println("Button pressed");
                    // LOAD the new fragment
                }
            });

            return convertView;
        }
    }
}
