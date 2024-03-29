package com.yessumtorah.brandeetsapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://brandeets.herokuapp.com";
    private static final String TAG = "MAIN_ACTIVITY";
    private static final String BEARER = "Bearer ";
    private static String JWT = BEARER;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private ProgressBar progressBar;
    private RequestQueue mQueue;
    private boolean userLoggedIn = false;

    public static String getJWT() {
        return JWT;
    }

    public static void setJWT(String JWT) {
        MainActivity.JWT += JWT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        mQueue = Volley.newRequestQueue(this);

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

        findViewById(R.id.brands).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gotoBrandsActivity();
            }
        });

    }

    private void gotoBrandsActivity() {
        Intent intent = new Intent(this, BrandeetsActivity.class);
        intent.putExtra("BASE_URL", BASE_URL);
        intent.putExtra("JWT", JWT);
        intent.putExtra("userLoggedIn", userLoggedIn);

        startActivity(intent);

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

                Log.d(TAG, "email entered: " + map.get("email"));
                Log.d(TAG, "Password entered: " + map.get("password"));

                Call<Void> call = retrofitInterface.executeSignup(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Toast.makeText(MainActivity.this, "Signed Up Successfully",
                                    Toast.LENGTH_LONG).show();
                        } else if (response.code() == 401) {
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
                            setJWT(response.headers().get("Auth"));
                            setUserLoggedIn(true);
                            Log.d(TAG, JWT);
                            Toast.makeText(MainActivity.this, "Login Successful. you can now add or modify brands", Toast.LENGTH_LONG).show();
                            gotoBrandsActivity();
                        } else if (response.code() == 401) {
                            Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_LONG).show();
                        } else if (response.code() >= 500 && response.code() <= 599) {
                            Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_LONG).show();
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

    public boolean isUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }
}
