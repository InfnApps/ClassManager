package br.edu.infnet.classmanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PanicFragment extends Fragment {


    Button panicButton;
    Button peaceButton;

    public PanicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_panic, container, false);

        panicButton = rootView.findViewById(R.id.panic_button);

        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView counter = getActivity().findViewById(R.id.counter);
                counter.setText("" + (Integer.parseInt(counter.getText().toString()) + 1));
            }
        });



        return rootView;
    }

    public void incrementCounter(View buuton){

    }

}
