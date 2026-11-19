package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private final List<Workout> workouts;
    private final OnWorkoutClickListener listener;

    public interface OnWorkoutClickListener {
        void onWorkoutClick(Workout workout);
    }

    public WorkoutAdapter(List<Workout> workouts, OnWorkoutClickListener listener) {
        this.workouts = workouts != null ? new ArrayList<>(workouts) : new ArrayList<>();
        this.listener = listener;
    }

    public void updateData(List<Workout> newWorkouts) {
        workouts.clear();
        if (newWorkouts != null) {
            workouts.addAll(newWorkouts);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workout_card, parent, false);
        return new WorkoutViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        holder.bind(workouts.get(position));
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        private final ImageView workoutImage;
        private final TextView workoutTitle;
        private final TextView workoutInfo;
        private final LinearLayout difficultyContainer;
        private final OnWorkoutClickListener listener;

        WorkoutViewHolder(@NonNull View itemView, OnWorkoutClickListener listener) {
            super(itemView);
            this.listener = listener;
            workoutImage = itemView.findViewById(R.id.workoutImage);
            workoutTitle = itemView.findViewById(R.id.workoutTitle);
            workoutInfo = itemView.findViewById(R.id.workoutInfo);
            difficultyContainer = itemView.findViewById(R.id.difficultyContainer);
        }

        void bind(Workout workout) {
            workoutTitle.setText(workout.getTitle());
            workoutInfo.setText(workout.getDuration() + " мин. • " +
                    workout.getExercises() + " упражнений");

            if (workout.getImageId() != 0) {
                workoutImage.setImageResource(workout.getImageId());
            }

            difficultyContainer.removeAllViews();
            for (int i = 0; i < 3; i++) {
                ImageView iv = new ImageView(difficultyContainer.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
                if (i > 0) params.setMarginStart(4);

                iv.setImageResource(R.mipmap.ic_lighting_round);
                iv.setVisibility(i < workout.getDifficulty() ? View.VISIBLE : View.INVISIBLE);
                iv.setLayoutParams(params);
                difficultyContainer.addView(iv);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onWorkoutClick(workout);
                }
            });
        }
    }
}