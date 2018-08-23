package br.edu.infnet.classmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    //
    public void signIn(View view){
        //TODO: sign in code
        EditText field = findViewById(R.id.email_field);
        String email = field.getText().toString();

        field = findViewById(R.id.password_field);
        String password = field.getText().toString();
    }


    public void goToSignUp(View view){
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void skipToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        //TODO: remove from stack
        startActivity(intent);
    }
}
