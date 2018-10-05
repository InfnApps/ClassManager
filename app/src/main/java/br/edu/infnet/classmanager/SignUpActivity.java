package br.edu.infnet.classmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import br.edu.infnet.classmanager.utils.Constants;
import br.edu.infnet.classmanager.utils.Utils;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SignUpActivity extends AppCompatActivity implements MoodleAuthTask.OnTaskCompleteListener{

    FirebaseAuth mAuth;
    DatabaseReference userRef;

    Button signUpButton;
    private final String VALID_EMAIL_SUFFIX = "infnet.edu.br";
    private final int PASSWORD_MINIMUM_LENGHT = 6;
    private final String MOODLE_VALIDATION_WORD = "token";
    private String email;
    private String password;
    private String moodlePassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();


        userRef = FirebaseDatabase.getInstance().
                getReference(Constants.USERS_ENDPOINT);
    }


    private boolean validateFields(String email, String password) {

        if (!email.endsWith(VALID_EMAIL_SUFFIX)) {
            return false;
        }

        if (password.length() < PASSWORD_MINIMUM_LENGHT) {
            return false;
        }

        return true;
    }

    public void createUser(View view) {
        // Pegar os campos
        EditText field = findViewById(R.id.email_field);
        email = field.getText().toString();

        field = findViewById(R.id.password_field);
        password = field.getText().toString();

        field = findViewById(R.id.moodle_password_field);
        moodlePassword = field.getText().toString();

        if (validateFields(email, password)) {
            //aqui pode demorar
            progressBar.setVisibility(View.VISIBLE);
            MoodleAuthTask task = new MoodleAuthTask();
            task.setOnTaskCompleteListener(this);
            task.execute(email, password, moodlePassword);
        }
    }


    @Override
    public void onTaskComplete(boolean success) {
        if (success) {
            mAuth.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                FirebaseUser fbUser = mAuth.getCurrentUser();

                                String name = Utils.getNameFromInstitutionalMail(email);
                                //criar usuário no banco de dados
                                User user = new User(name, email);
                                userRef.child(fbUser.getUid()).setValue(user);
                                Toast.makeText(getApplicationContext(),
                                        "Voce foi Cadastrado com Sucesso",
                                        Toast.LENGTH_LONG).show();
                                //avançar para outra Activity (MainActivity)
                                /*Intent intent = new Intent(getApplicationContext(),
                                        MainActivity.class);
                                intent.setFlags(FLAG_ACTIVITY_CLEAR_TASK|FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);*/
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),
                    "FALHOU",
                    Toast.LENGTH_LONG).show();
        }
    }

     /*public void createUser(View view) {
        EditText nameField = findViewById(R.id.name_field);
        EditText emailField = findViewById(R.id.email_field);
        EditText passwordField = findViewById(R.id.password_field);

        final String email = emailField.getText().toString();
        final String name = nameField.getText().toString();
        String password = passwordField.getText().toString();

        // se validou, posso criar usuário
        // TODO: botar a ProgressBar para girar

    }*/
}
