package com.yessumtorah.brandeetsapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "";
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handleLoginDialog();
            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handleSignUpDialog();
            }
        });
    }

    private void handleSignUpDialog() {
        View view = getLayoutInflater().inflate(R.layout.signup_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).show();

        Button signUpBtn = view.findViewById(R.id.signup);
        final EditText emailEdit = view.findViewById(R.id.emailEdit);
        final EditText pwdEdit = view.findViewById(R.id.pwdEdit);

        signUpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("email", emailEdit.getText().toString());
                map.put("password", pwdEdit.getText().toString());

                Call<Void> call = retrofitInterface.executeSignup(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Toast.makeText(MainActivity.this, "Signed Up Successfully",
                                    Toast.LENGTH_LONG).show();
                        } else if (response.code() == 400) {
                            Toast.makeText(MainActivity.this, "Already Registered",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }

    private void handleLoginDialog() {

        View view = getLayoutInflater().inflate(R.layout.login_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view).show();

        Button loginBtn = view.findViewById(R.id.login);
        final EditText emailEdit = view.findViewById(R.id.emailEdit);
        final EditText pwdEdit = view.findViewById(R.id.pwdEdit);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();

                map.put("email", emailEdit.getText().toString());
                map.put("password", pwdEdit.getText().toString());

                Call<LoginInstance> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<LoginInstance>() {
                    @Override
                    public void onResponse(Call<LoginInstance> call, Response<LoginInstance> response) {
                        if (response.code() == 200) {

                            LoginInstance result = response.body();
                            String JWT = response.headers().get("Auth");
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginInstance> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
}
