package com.example.taxfileapp;

import android.content.Context;

import androidx.room.Room;

import java.util.List;
import java.util.function.Consumer;

public class UserService {
    private static UserService instance;
    private final UserDAO userDAO;

    private UserService(Context context) {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "userdb").build();
        userDAO = db.userDAO();
    }

    public static synchronized UserService getInstance(Context context) {
        if(instance == null) {
            instance = new UserService(context);
        }
        return instance;
    }

    public interface OperationCallback {
        void onOperationCompleted();
        void onError(Exception e);
    }

    public void insertUser(User user, OperationCallback callback) {
        new Thread(() -> {
            try {
                userDAO.insert(user);
                if(callback != null) {
                    callback.onOperationCompleted();
                }
            } catch (Exception e) {
                if(callback != null) {
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void updateUser(User user, OperationCallback callback) {
        new Thread(() -> {
            try {
                userDAO.update(user);
                if(callback != null) {
                    callback.onOperationCompleted();
                }
            } catch (Exception e) {
                if(callback != null) {
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void deleteUser(User user, OperationCallback callback) {
        new Thread(() -> {
            try {
                userDAO.delete(user);
                if(callback != null) {
                    callback.onOperationCompleted();
                }
            } catch (Exception e) {
                if(callback != null) {
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void fetchAllUsers(Consumer<List<User>> onResult) {
        new Thread(() -> {
            try {
                List<User> users = userDAO.getAll();
                if(onResult != null) {
                    onResult.accept(users);
                }
            } catch (Exception e) {
                // Handle error here
            }
        }).start();
    }

    // create me findUserByFullName method here as same as all above
}
