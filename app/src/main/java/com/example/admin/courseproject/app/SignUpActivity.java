package com.example.admin.courseproject.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.studentmanager.R;

import java.util.HashMap;

import static com.example.admin.studentmanager.app.LoginActivity.sLogins;

public class SignUpActivity extends AppCompatActivity {

    private LinearLayout mLinearLayout;
    private Button btnSignUp;
    private Button btnCancel;
    private EditText edtLogin;
    private EditText edtPassword;
    private EditText edtPasswordConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPasswordConfirm = (EditText) findViewById(R.id.edtPasswordConfirm);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (add_user(edtLogin.getText().toString(), edtPassword.getText().toString(),
                        edtPasswordConfirm.getText().toString())){
                    Intent intent = new Intent(SignUpActivity.this, GettingStartedActivity.class);
                    intent.putExtra("login", edtLogin.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUpActivity.this, "Wrong login or password", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.action_search) {
            Context context = SignUpActivity.this;
            String message = "Search clicked";
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    public boolean  add_user(String login, String password, String confirmPassword) {
        boolean checkFields = false;

        if ((login != null) && (password.equals(confirmPassword))) {
            checkFields = true;
            Toast.makeText(SignUpActivity.this, "Match password, " + login,
                    Toast.LENGTH_LONG).show();
            sLogins.put(login, password);
        }
        return checkFields;
    }
}
