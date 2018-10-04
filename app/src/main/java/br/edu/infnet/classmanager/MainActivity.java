package br.edu.infnet.classmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.edu.infnet.classmanager.models.QuestionCard;
import br.edu.infnet.classmanager.utils.Constants;

public class MainActivity extends AppCompatActivity
        implements OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener{


    ViewPager viewPager;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    DatabaseReference panicRef;

    FirebaseAuth mAuth;
    String username;

    private final int REQUEST_ANSWER = 17;
    private final int PAGER_START_POSITION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        Toolbar toolbar = findViewById(R.id.main_toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_opening, R.string.drawer_closing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        QuestionPanicPagerAdapter adapter = new QuestionPanicPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(PAGER_START_POSITION);

        mAuth = FirebaseAuth.getInstance();

        final TextView panicCounter = findViewById(R.id.panicCounterShow);
        panicRef = FirebaseDatabase.getInstance().getReference("PanicCounter");
        panicRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer counter = dataSnapshot.getValue(Integer.class);
                panicCounter.setText("Alunos em pânico " + counter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setNavigationHeader();
    }

    public void askQuestion(View view){
        if (mAuth.getCurrentUser() != null){
            Intent intent = new Intent(this, QuestionComposerActivity.class);
            startActivity(intent);
        } else {
            Snackbar.make(findViewById(R.id.main_drawer_layout),
                    R.string.not_authenticated_user_question,
                    Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCardClick(DataSnapshot dataSnapshot) {
        Intent intent;
        QuestionCard questionCard = dataSnapshot.getValue(QuestionCard.class);
        if (questionCard.isAnswered()){
            intent = new Intent(this,
                    ShowQuestionActivity.class);

        } else {
            intent = new Intent(this,
                    QuestionFeedbackActivity.class);
        }

        //Serializable ou Parcelable
        intent.putExtra(Constants.QUESTIONCARD_KEY, questionCard);
        intent.putExtra(Constants.QUESTION_FB_KEY, dataSnapshot.getKey());

        startActivity(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (drawerLayout.isDrawerOpen(Gravity.START)){
            drawerLayout.closeDrawer(Gravity.START);
        }
        switch (item.getItemId()){
            case R.id.menu_signin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.menu_signup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.menu_profile:
                Toast.makeText(this, "Perfil não implementado ainda", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_attendance:
                Toast.makeText(this, "Presenças não implementado ainda", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_logout:
                mAuth.signOut();
                setNavigationHeader();
                break;
            default:
                break;
        }

        return true;
    }

    private void setNavigationHeader(){
        navigationView.getMenu().clear();
        final TextView textView = findViewById(R.id.auth_username);
        if(mAuth.getCurrentUser() == null){
            navigationView.inflateMenu(R.menu.drawer_menu);
            if (textView != null){
                textView.setText(R.string.unauth_username);
            }
        } else {
            navigationView.inflateMenu(R.menu.authenticated_drawer_menu);
            if (textView != null && textView.getText().toString().equals(getString(R.string.unauth_username))){
                DatabaseReference usernameRef = FirebaseDatabase.getInstance().
                        getReference(Constants.USERS_ENDPOINT).
                        child(mAuth.getCurrentUser().getUid()).child("name");
                usernameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                textView.setText(String.valueOf(dataSnapshot.getValue()));
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                textView.setText("Problema ao recuperar nome de usuário");
                            }
                        });
            }
        }
    }
}
