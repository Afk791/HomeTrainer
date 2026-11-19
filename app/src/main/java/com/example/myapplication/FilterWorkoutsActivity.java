package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FilterWorkoutsActivity extends AppCompatActivity
        implements WorkoutAdapter.OnWorkoutClickListener {

    private TextView titleText;
    private TextView emptyText;
    private RecyclerView recyclerView;
    private ImageButton btnBack;

    private WorkoutAdapter adapter;
    private List<Workout> allWorkouts;
    private List<Workout> filteredWorkouts;

    private String filterDifficulty;
    private String filterTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity);

        initViews();
        loadData();
        setupAdapter();
        applyFilter();
        setupListeners();
    }

    private void initViews() {
        titleText = findViewById(R.id.titleText);
        emptyText = findViewById(R.id.emptyText);
        recyclerView = findViewById(R.id.recyclerView);
        btnBack = findViewById(R.id.btnBack);

        filterDifficulty = getIntent().getStringExtra("difficulty");
        filterTitle = getIntent().getStringExtra("title");

        if (filterTitle != null && !filterTitle.isEmpty()) {
            titleText.setText(filterTitle);
        }
    }

    private void loadData() {
        allWorkouts = getWorkoutData();
        filteredWorkouts = new ArrayList<>();
    }

    private void setupAdapter() {
        adapter = new WorkoutAdapter(filteredWorkouts, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void applyFilter() {
        int difficultyLevel = getDifficultyLevel(filterDifficulty);

        filteredWorkouts.clear();
        for (Workout workout : allWorkouts) {
            if (workout.getDifficulty() == difficultyLevel) {
                filteredWorkouts.add(workout);
            }
        }

        adapter.updateData(filteredWorkouts);
        updateEmptyState();
    }

    private int getDifficultyLevel(String difficulty) {
        if (difficulty == null) return 1;
        switch (difficulty) {
            case "beginner": return 1;
            case "intermediate": return 2;
            case "advanced": return 3;
            default: return 1;
        }
    }

    private void updateEmptyState() {
        boolean isEmpty = filteredWorkouts.isEmpty();
        recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        emptyText.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

    private void setupListeners() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v ->
                    startActivity(new Intent(this, SecondActivity.class)));
        }
    }

    @Override
    public void onWorkoutClick(Workout workout) {
        openWorkoutDetail(workout);
    }

    private void openWorkoutDetail(Workout workout) {
        Intent intent = new Intent(this, WorkoutsActivity.class);
        intent.putExtra("id", workout.getId());
        intent.putExtra("title", workout.getTitle());
        intent.putExtra("duration", workout.getDuration());
        intent.putExtra("exercises", workout.getExercises());
        intent.putExtra("image_id", workout.getImageId());
        intent.putExtra("difficulty", workout.getDifficulty());
        intent.putExtra("category", workout.getCategory());

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private List<Workout> getWorkoutData() {
        List<Workout> list = new ArrayList<>();

        list.add(new Workout(1, "Пресс Новичок", 15, 16, R.drawable.press1, 1, "press"));
        list.add(new Workout(2, "Пресс Продолжающий", 24, 21, R.drawable.press2, 2, "press"));
        list.add(new Workout(3, "Пресс Продвинутый", 27, 21, R.drawable.press3, 3, "press"));

        list.add(new Workout(4, "Руки Новичок", 15, 16, R.drawable.hands1, 1, "hands"));
        list.add(new Workout(5, "Руки Продолжающий", 24, 21, R.drawable.hands2, 2, "hands"));
        list.add(new Workout(6, "Руки Продвинутый", 27, 21, R.drawable.hands3, 3, "hands"));

        list.add(new Workout(7, "Грудь Новичок", 15, 16, R.drawable.breast1, 1, "breast"));
        list.add(new Workout(8, "Грудь Продолжающий", 24, 21, R.drawable.breast2, 2, "breast"));
        list.add(new Workout(9, "Грудь Продвинутый", 27, 21, R.drawable.breast3, 3, "breast"));

        list.add(new Workout(10, "Ноги Новичок", 22, 23, R.drawable.legs1, 1, "legs"));
        list.add(new Workout(11, "Ноги Продолжающий", 30, 36, R.drawable.legs2, 2, "legs"));
        list.add(new Workout(12, "Ноги Продвинутый", 39, 43, R.drawable.legs3, 3, "legs"));

        list.add(new Workout(13, "Спина Новичок", 14, 17, R.drawable.back1, 1, "back"));
        list.add(new Workout(14, "Спина Продолжающий", 18, 17, R.drawable.back2, 2, "back"));
        list.add(new Workout(15, "Спина Продвинутый", 20, 17, R.drawable.back3, 3, "back"));

        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) adapter.updateData(null);
    }
}