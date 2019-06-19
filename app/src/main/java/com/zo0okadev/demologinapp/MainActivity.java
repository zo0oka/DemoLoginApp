package com.zo0okadev.demologinapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.email_field)
    TextInputEditText emailField;
    @BindView(R.id.passwrod_field)
    TextInputEditText passwordField;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.textview_login)
    TextView loginTextView;
    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        appViewModel.getLoginResponse().observe(this, loginResponse -> loginTextView.setText(loginResponse.toString()));

        appViewModel.getError().observe(this, s -> Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show());

    }

    @OnClick(R.id.login_button)
    public void submitLogin() {
        appViewModel.loginUser(emailField.getEditableText().toString(),
                passwordField.getEditableText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
