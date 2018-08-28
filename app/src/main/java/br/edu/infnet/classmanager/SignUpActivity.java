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


    private boolean validateFields(String email, String password, String confirmation) {
        //TODO: make it right!
        return true;
    }

    public void createUser(View view) {
        EditText nameField = findViewById(R.id.name_field);
        EditText emailField = findViewById(R.id.email_field);
        EditText passwordField = findViewById(R.id.password_field);

        final String email = emailField.getText().toString();
        final String name = nameField.getText().toString();
        String password = passwordField.getText().toString();

        // se validou, posso criar usuário
        // TODO: botar a ProgressBar para girar
        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // TODO: parar a ProgressBar
                        if (task.isSuccessful()){
                            FirebaseUser fbUser = mAuth.getCurrentUser();

                            //criar usuário no banco de dados
                            User user = new User(name, email);
                            userRef.child(fbUser.getUid()).setValue(user);
                            Toast.makeText(getApplicationContext(),
                                    "Cadastro efetuado com sucesso!",
                                    Toast.LENGTH_LONG).show();
                            //avançar para outra Activity (MainActivity)
                            startActivity(new Intent(getApplicationContext(),
                                    MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
