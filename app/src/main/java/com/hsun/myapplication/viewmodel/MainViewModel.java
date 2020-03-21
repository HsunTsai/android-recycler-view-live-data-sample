package com.hsun.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hsun.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<User>> observableUserList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<User>> getUsers() {
        return observableUserList;
    }

    public void addUser(String userName) {
        List<User> userList = observableUserList.getValue();
        assert userList != null;
        userList.add(new User(userName));
        for (User user : userList) user.setTotalUser(userList.size());
        observableUserList.postValue(userList);
    }

    public void removeUser() {
        List<User> userList = observableUserList.getValue();
        assert userList != null;
        if (userList.size() > 0) {
            userList.remove(0);
            for (User user : userList) user.setTotalUser(userList.size());
            observableUserList.postValue(userList);
        }
    }
}
