package br.edu.infnet.classmanager.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import br.edu.infnet.classmanager.LoginActivity;
import br.edu.infnet.classmanager.R;

public class ResetarSenha extends AppCompatActivity {

    private Button Reset;
    private EditText Email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetarsenha);

        mAuth = FirebaseAuth.getInstance();



        Reset = (Button)findViewById(R.id.send_button);
        Email = (EditText)findViewById(R.id.email_send);


        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = Email.getText().toString();

                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(ResetarSenha.this, "Informe um e-mail válido", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(ResetarSenha.this, "Verifique em seu e-mail o reset de senha", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetarSenha.this,LoginActivity.class));
                            }else {
                                String erro = task.getException().getMessage();
                                Toast.makeText(ResetarSenha.this, "Error : " +erro, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }

}
