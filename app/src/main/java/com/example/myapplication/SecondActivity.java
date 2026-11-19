package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {

    private List<TextView> dayViews;
    private List<Date> weekDates;

    private RadioGroup radioGroup;
    private HorizontalScrollView scrollButton;
    private LinearLayout pressWorkouts, handsWorkouts, breastWorkouts, legsWorkouts, backWorkouts;

    private LinearLayout linearPress_1, linearPress_2, linearPress_3;
    private LinearLayout linearHands_1, linearHands_2, linearHands_3;
    private LinearLayout linearBreats_1, linearBreats_2, linearBreats_3;
    private LinearLayout linearLegs_1, linearLegs_2, linearLegs_3;
    private LinearLayout linearBack_1, linearBack_2, linearBack_3;

    private MaterialButton btnBeginner, btnIntermediate, btnAdvanced;
    private List<Workout> allWorkouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        initViews();
        loadCurrentWeek();
        initRadioGroup();
        initFilterButtons();
        setWorkoutClicks();
        loadWorkoutData();
    }

    private void initViews() {
        dayViews = new ArrayList<>();
        dayViews.add(findViewById(R.id.day1));
        dayViews.add(findViewById(R.id.day2));
        dayViews.add(findViewById(R.id.day3));
        dayViews.add(findViewById(R.id.day4));
        dayViews.add(findViewById(R.id.day5));
        dayViews.add(findViewById(R.id.day6));
        dayViews.add(findViewById(R.id.day7));
        weekDates = new ArrayList<>();

        radioGroup = findViewById(R.id.workoutRadioGroup);
        scrollButton = findViewById(R.id.scrollButton);

        pressWorkouts = findViewById(R.id.pressWorkouts);
        handsWorkouts = findViewById(R.id.handsWorkouts);
        breastWorkouts = findViewById(R.id.breatsWorkouts);
        legsWorkouts = findViewById(R.id.legsWorkouts);
        backWorkouts = findViewById(R.id.backWorkouts);

        linearPress_1 = findViewById(R.id.linearPress_1);
        linearPress_2 = findViewById(R.id.linearPress_2);
        linearPress_3 = findViewById(R.id.linearPress_3);
        linearHands_1 = findViewById(R.id.linearHands_1);
        linearHands_2 = findViewById(R.id.linearHands_2);
        linearHands_3 = findViewById(R.id.linearHands_3);
        linearBreats_1 = findViewById(R.id.linearBreats_1);
        linearBreats_2 = findViewById(R.id.linearBreats_2);
        linearBreats_3 = findViewById(R.id.linearBreats_3);
        linearLegs_1 = findViewById(R.id.linearLegs_1);
        linearLegs_2 = findViewById(R.id.linearLegs_2);
        linearLegs_3 = findViewById(R.id.linearLegs_3);
        linearBack_1 = findViewById(R.id.linearBack_1);
        linearBack_2 = findViewById(R.id.linearBack_2);
        linearBack_3 = findViewById(R.id.linearBack_3);

        btnBeginner = findViewById(R.id.btnBeginner);
        btnIntermediate = findViewById(R.id.btnIntermediate);
        btnAdvanced = findViewById(R.id.btnAdvanced);

        allWorkouts = new ArrayList<>();
    }

    private void loadCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        weekDates = getWeekDates(calendar);
        updateDaysDisplay();
    }

    private List<Date> getWeekDates(Calendar calendar) {
        List<Date> dates = new ArrayList<>();
        Calendar tempCalendar = (Calendar) calendar.clone();
        tempCalendar.setFirstDayOfWeek(Calendar.MONDAY);

        int dayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);
        int daysToSubtract = (dayOfWeek == Calendar.SUNDAY) ? 6 : dayOfWeek - Calendar.MONDAY;
        tempCalendar.add(Calendar.DAY_OF_MONTH, -daysToSubtract);

        for (int i = 0; i < 7; i++) {
            dates.add(tempCalendar.getTime());
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    private void updateDaysDisplay() {
        Calendar today = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());

        for (int i = 0; i < weekDates.size(); i++) {
            Date date = weekDates.get(i);
            TextView textView = dayViews.get(i);
            Calendar dayCalendar = Calendar.getInstance();
            dayCalendar.setTime(date);

            textView.setText(dayFormat.format(date));

            boolean isToday = dayCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    dayCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                    dayCalendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);

            if (isToday) {
                textView.setBackgroundResource(R.drawable.circle_active);
                textView.setTextColor(Color.WHITE);
            } else if (date.before(today.getTime())) {
                textView.setBackgroundResource(R.drawable.circle_inactive);
                textView.setTextColor(Color.BLACK);
            } else {
                textView.setBackgroundResource(R.drawable.circle_future);
                textView.setTextColor(Color.GRAY);
            }
        }
    }

    private void initRadioGroup() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.buttonPress) scrollToSection(pressWorkouts);
            else if (checkedId == R.id.buttonHands) scrollToSection(handsWorkouts);
            else if (checkedId == R.id.buttonBreast) scrollToSection(breastWorkouts);
            else if (checkedId == R.id.buttonLegs) scrollToSection(legsWorkouts);
            else if (checkedId == R.id.buttonBack) scrollToSection(backWorkouts);
        });
    }

    private void scrollToSection(LinearLayout section) {
        if (section != null) {
            scrollButton.smoothScrollTo(section.getLeft(), 0);
        }
    }

    private void initFilterButtons() {
        btnBeginner.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, FilterWorkoutsActivity.class);
            intent.putExtra("difficulty", "beginner");
            intent.putExtra("title", "Новичок");
            startActivity(intent);
        });

        btnIntermediate.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, FilterWorkoutsActivity.class);
            intent.putExtra("difficulty", "intermediate");
            intent.putExtra("title", "Продолжающий");
            startActivity(intent);
        });

        btnAdvanced.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, FilterWorkoutsActivity.class);
            intent.putExtra("difficulty", "advanced");
            intent.putExtra("title", "Продвинутый");
            startActivity(intent);
        });
    }

    private void loadWorkoutData() {
        allWorkouts = getWorkoutData();
    }

    private List<Workout> getWorkoutData() {
        List<Workout> list = new ArrayList<>();

        list.add(new Workout(1, "Пресс Новичок", 15, 16, R.drawable.press1, 1, "press"));
        list.add(new Workout(2, "Пресс Продолжающий", 24, 21, R.drawable.press2, 2, "press"));
        list.add(new Workout(3, "Пресс Продвинутый", 27, 21, R.drawable.press3, 3, "press"));

        list.add(new Workout(4, "Руки Новичок", 16, 19, R.drawable.hands1, 1, "hands"));
        list.add(new Workout(5, "Руки Продолжающий", 20, 18, R.drawable.hands2, 2, "hands"));
        list.add(new Workout(6, "Руки Продвинутый", 30, 28, R.drawable.hands3, 3, "hands"));

        list.add(new Workout(7, "Грудь Новичок", 9, 11, R.drawable.breast1, 1, "breast"));
        list.add(new Workout(8, "Грудь Продолжающий", 12, 14, R.drawable.breast2, 2, "breast"));
        list.add(new Workout(9, "Грудь Продвинутый", 17, 16, R.drawable.breast3, 3, "breast"));

        list.add(new Workout(10, "Ноги Новичок", 22, 23, R.drawable.legs1, 1, "legs"));
        list.add(new Workout(11, "Ноги Продолжающий", 30, 36, R.drawable.legs2, 2, "legs"));
        list.add(new Workout(12, "Ноги Продвинутый", 39, 43, R.drawable.legs3, 3, "legs"));

        list.add(new Workout(13, "Спина Новичок", 14, 17, R.drawable.back1, 1, "back"));
        list.add(new Workout(14, "Спина Продолжающий", 18, 17, R.drawable.back2, 2, "back"));
        list.add(new Workout(15, "Спина Продвинутый", 20, 17, R.drawable.back3, 3, "back"));

        return list;
    }

    private void setWorkoutClicks() {
        setClick(linearPress_1, 1); setClick(linearPress_2, 2); setClick(linearPress_3, 3);
        setClick(linearHands_1, 4); setClick(linearHands_2, 5); setClick(linearHands_3, 6);
        setClick(linearBreats_1, 7); setClick(linearBreats_2, 8); setClick(linearBreats_3, 9);
        setClick(linearLegs_1, 10); setClick(linearLegs_2, 11); setClick(linearLegs_3, 12);
        setClick(linearBack_1, 13); setClick(linearBack_2, 14); setClick(linearBack_3, 15);
    }

    private void setClick(LinearLayout layout, int id) {
        if (layout != null) {
            layout.setOnClickListener(v -> {
                Workout workout = getWorkoutById(id);
                if (workout != null) openWorkoutDetail(workout);
            });
        }
    }

    private Workout getWorkoutById(int id) {
        for (Workout workout : allWorkouts) {
            if (workout.getId() == id) return workout;
        }
        return null;
    }

    private void openWorkoutDetail(Workout workout) {
        Intent intent = new Intent(this, WorkoutsActivity.class);
        intent.putExtra("id", workout.getId());
        intent.putExtra("title", workout.getTitle());
        intent.putExtra("duration", workout.getDuration());
        intent.putExtra("exercises", workout.getExercises());
        intent.putExtra("image_id", workout.getImageId());
        intent.putExtra("difficulty", workout.getDifficulty());
        startActivity(intent);
    }
}