package com.example.taxfileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomerDetails extends AppCompatActivity {

    private UserService userService;
    private User user;

    private EditText phoneEditText, statusEditText;

    private TextView phoneTextView;
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        userService = UserService.getInstance(this);

        phoneEditText = findViewById(R.id.phoneEditText);
        statusEditText = findViewById(R.id.statusEditText);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        TextView fullNameTextView = findViewById(R.id.fullNameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        TextView cityTextView = findViewById(R.id.cityTextView);
        TextView companyTextView = findViewById(R.id.companyTextView);
        statusTextView = findViewById(R.id.statusTextView);

        fullNameTextView.setText(user.fullName);
        phoneTextView.setText(user.phone);
        cityTextView.setText(user.city);
        companyTextView.setText(user.companyName);
        statusTextView.setText(user.processStatus);


    }

    public void updateData(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(user != null) {
                    if(!phoneEditText.getText().toString().equals("")){
                        user.phone = phoneEditText.getText().toString();
                    }
                    if(!statusEditText.getText().toString().equals("")){
                        user.processStatus = statusEditText.getText().toString();
                    }

                    userService.updateUser(user, new UserService.OperationCallback() {
                        @Override
                        public void onOperationCompleted() {
                            runOnUiThread(() -> {
                                if(!phoneEditText.getText().toString().equals("")){
                                    phoneTextView.setText(phoneEditText.getText().toString());
                                }
                                if(!statusEditText.getText().toString().equals("")){
                                    statusTextView.setText(statusEditText.getText().toString());
                                }
                                clearEditTexts();
                                // Intent intent = new Intent(CustomerDetails.this, MainActivity.class);
                                // startActivity(intent);
                            });

                        }

                        @Override
                        public void onError(Exception e) {
                            // handle error
                            Log.d("MainActivity", "Error Updating...");
                        }
                    });
                }
            }
        }).start();
    }

    private void clearEditTexts() {
        runOnUiThread(() -> {
            phoneEditText.setText("");
            statusEditText.setText("");
            user = null;
        });
    }
}