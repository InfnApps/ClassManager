package br.edu.infnet.classmanager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class PanicFragment extends Fragment {


    DatabaseReference panicRef;
    Button panicButton;
    boolean inPanic = false;
    TextView counterText;

    public PanicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_panic, container, false);

        panicButton = rootView.findViewById(R.id.panic_button);

        counterText = rootView.findViewById(R.id.counter);

        panicRef = FirebaseDatabase.getInstance().getReference("PanicCounter");

        panicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // sincronizar contador entre os clientes
                Integer counter = dataSnapshot.getValue(Integer.class);
                counterText.setText(String.valueOf(counter));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TextView counter = getActivity().findViewById(R.id.counter);
                //counter.setText("" + (Integer.parseInt(counter.getText().toString()) + 1));
                Button toggleButton = (Button) view;
                inPanic = !inPanic;
                if (inPanic){
                    toggleButton.setText("De boa");
                } else{
                    toggleButton.setText("Pânico!");
                }



                panicRef.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        Integer counter = mutableData.getValue(Integer.class);
                        if (counter == null){
                            return Transaction.success(mutableData);
                        }
                        //TODO: add and remove user id based on panic status; count ids.
                        if (inPanic){
                            counter = counter + 1;
                        } else {
                            counter = counter - 1;
                        }

                        mutableData.setValue(counter);

                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                        if (databaseError != null) {
                            Toast.makeText(getContext(),
                                    databaseError.getMessage(),
                                    Toast.LENGTH_LONG);
                        }
//                        }else {
//                            Integer counter = dataSnapshot.getValue(Integer.class);
//                            counterText.setText(String.valueOf(counter));
//                        }

                    }
                });



            }
        });



        return rootView;
    }

}
