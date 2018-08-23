package br.edu.infnet.classmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.edu.infnet.classmanager.models.User;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        userRef = FirebaseDatabase.getInstance().getReference("Users");

    }


    private boolean validateFields(String email,  String password, String confirmation){
        //TODO: make it right!
        return true;
    }

    public void createUser(View view){
        EditText emailField = findViewById(R.id.email_field);
        EditText passwordField = findViewById(R.id.password_field);
        EditText passwordConfirmationField = findViewById(R.id.password_confirmation_field);

        final String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String passWordConfirmation = passwordConfirmationField.getText().toString();

        // se validou, posso criar usuário
        if (validateFields(email, password, passWordConfirmation)){

            mAuth.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser fbUser =  mAuth.getCurrentUser();
                                //id criado pelo Firebase para gerenciar a conta do usuário
                                String userKey = fbUser.getUid();

                                userRef.child(userKey).setValue(new User(email));

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                //TODO: remove from stack
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Erro ao criar usuário", Toast.LENGTH_LONG);
                            }
                        }
                    });
        } else{
            Toast.makeText(this, "Erro de validação", Toast.LENGTH_LONG);
        }
    }

}
