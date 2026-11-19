package com.example.myapplication;

public class Workout {
    private final int id;
    private final String title;
    private final int duration;
    private final int exercises;
    private final int imageId;
    private final int difficulty;
    private final String category;

    public Workout(int id, String title, int duration, int exercises,
                   int imageId, int difficulty, String category) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.exercises = exercises;
        this.imageId = imageId;
        this.difficulty = difficulty;
        this.category = category;
    }

    // 🔹 Геттеры
    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getDuration() { return duration; }
    public int getExercises() { return exercises; }
    public int getImageId() { return imageId; }
    public int getDifficulty() { return difficulty; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return title + " (" + difficulty + "★)";
    }
}