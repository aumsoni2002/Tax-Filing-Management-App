package com.example.taxfileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private User selectedUser = null;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userService = UserService.getInstance(this);
        recyclerView = findViewById(R.id.recycler_view);

        adapter = new RecyclerAdapter(new ArrayList<>());
        adapter.setOnUserClickListener(user -> {
            selectedUser = user;
//            runOnUiThread(() -> {
//            });

            // Create an Intent to start a new Activity
            Intent intent = new Intent(this, CustomerDetails.class);

            // Put the user object as an extra with the Intent
            intent.putExtra("user", user);

            // Start the new Activity
            startActivity(intent);

            // finish();
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setupItemTouchHelper();

        // insertData();
        fetchData();
    }

    @Override
    protected void onResume() {
        super.onResume();
         fetchData();
        Log.d("MainActivity", "FetchData Called");
    }

    public void fetchData() {
        userService.fetchAllUsers(users -> runOnUiThread(() -> adapter.setData(users)));
    }

    public void insertData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(
                        "Raj Parmar",
                        "9734037463",
                        "Indore",
                        "IBM",
                        "AWAITED");

                userService.insertUser(user, new UserService.OperationCallback() {
                    @Override
                    public void onOperationCompleted() {}

                    @Override
                    public void onError(Exception e) {}
                });
            }
        }).start();
    }

    /*private void setupItemTouchHelper() {
        // Setup ItemTouchHelper for swipe to delete
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // We don't want move functionality
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition(); // Get swiped item position
                User userToDelete = adapter.getUserAtPosition(position); // Get the user to delete

                userService.deleteUser(userToDelete, new UserService.OperationCallback() {
                    @Override
                    public void onOperationCompleted() {
                        runOnUiThread(() -> {
                            adapter.removeUserAtPosition(position);
                        });
                    }
                    @Override
                    public void onError(Exception e) {
                        // handle error
                    }
                });
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }*/

    private void setupItemTouchHelper() {
        // Setup ItemTouchHelper for swipe to delete
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // We don't want move functionality
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition(); // Get swiped item position
                User userToDelete = adapter.getUserAtPosition(position); // Get the user to delete

                // Show a confirmation dialog
                showDeleteConfirmationDialog(userToDelete, position);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void showDeleteConfirmationDialog(User userToDelete, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Yes, delete the item
                        deleteUserAndRefresh(userToDelete, position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked No, dismiss the dialog
                        adapter.notifyItemChanged(position); // Refresh the item in case it was partially swiped
                        dialog.dismiss();
                    }
                });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteUserAndRefresh(User userToDelete, int position) {
        userService.deleteUser(userToDelete, new UserService.OperationCallback() {
            @Override
            public void onOperationCompleted() {
                runOnUiThread(() -> {
                    adapter.removeUserAtPosition(position);
                });
            }

            @Override
            public void onError(Exception e) {
                // handle error
            }
        });
    }
}