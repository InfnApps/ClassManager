package br.edu.infnet.classmanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.edu.infnet.classmanager.models.QuestionCard;
import br.edu.infnet.classmanager.utils.Constants;

public class QuestionComposerActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_composer);

        mAuth = FirebaseAuth.getInstance();
    }

    public void saveQuestion(View view){
        //pega referências para as views na interface
        final EditText questionField = findViewById(R.id.question_field);
        final CheckBox box = findViewById(R.id.anonymous_box);
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference(Constants.USERS_ENDPOINT)
                .child(currentUser.getUid())
                .child("name");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String askerName = (String) dataSnapshot.getValue();

                // pega os valores necessários para construir uma QuestionCard a partir das views
                String questionBody = questionField.getText().toString();
                boolean isAnonym = box.isChecked();

                if (isAnonym){
                    askerName = getString(R.string.anonymous_name);
                }

                //User user = dataSnapshot.getValue(User.class);
                final QuestionCard questionCard = new QuestionCard(questionBody,
                        askerName,
                        currentUser.getUid(),
                        isAnonym);

                DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference(Constants.ACTIVE_QUESTIONS_ENDPOINT);
                dbReference = dbReference.push();
                dbReference.setValue(questionCard);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // encerra a Activity removendo ela da stack, para que o backButton não reabra ela
        finish();
    }
}
