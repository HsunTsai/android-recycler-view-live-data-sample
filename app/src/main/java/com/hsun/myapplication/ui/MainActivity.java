package com.hsun.myapplication.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hsun.myapplication.R;
import com.hsun.myapplication.databinding.ActivityMainBinding;
import com.hsun.myapplication.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private UserAdapter userAdapter;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }

    private void init() {
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        subscribeToModel(viewModel);

        /* Create and set the adapter for the RecyclerView */
        userAdapter = new UserAdapter();
        /* Remove animation when adapter data change */
        ((SimpleItemAnimator) binding.userList.getItemAnimator()).setSupportsChangeAnimations(false);
        binding.userList.setAdapter(userAdapter);

        FloatingActionButton fabAdd = binding.getRoot().findViewById(R.id.fab_add),
                fabRemove = binding.getRoot().findViewById(R.id.fab_remove);

        fabAdd.setOnClickListener((view) -> {
            viewModel.addUser("User " + count++);
        });

        fabRemove.setOnClickListener((view) -> {
            viewModel.removeUser();
        });
    }

    private void subscribeToModel(final MainViewModel model) {
        /* Observe users */
        model.getUsers().observe(this, users -> {
            userAdapter.setUserList(users);
        });
    }
}
