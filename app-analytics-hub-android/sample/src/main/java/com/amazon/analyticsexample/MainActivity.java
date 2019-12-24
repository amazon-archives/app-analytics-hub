package com.amazon.analyticsexample;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amazon.appanalyticshub.AnalyticsCollector;
import com.amazon.appanalyticshub.AppAnalyticsHub;
import com.amazon.appanalyticshub.Event;
import com.amazon.appanalyticshub.EventFactory;
import com.amazon.appanalyticshub.EventType;
import com.amazon.appanalyticshub.TimerMetric;

public class MainActivity extends AppCompatActivity {

    private TimerMetric timerMetric;
    private AppAnalyticsHub analyticsHub;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        analyticsHub = new AppAnalyticsHub();
        analyticsHub.setDefaultAnalyticsCollector(new AnalyticsCollector() {
            @Override
            public String getName() {
                return "Test Collector";
            }

            @Override
            public void recordEvent(@NonNull Event event) {
                Log.d("Trial", "recording event " + event);
            }
        });

        EventFactory eventFactory = new EventFactory("test", EventType.ENGAGEMENT);
        event = eventFactory.createEvent("TestEvent");
        timerMetric = new TimerMetric("timer", event);
        timerMetric.startTimer();
        event.addCounter("counter", 1)
                .addCounter("counter1", 2)
                .incrementCounter("counter1")
                .addData("name", "name")
                .addMetric("metric", "metric");

    }

    @Override
    protected void onResume() {
        super.onResume();
        timerMetric.stopTimer();
        timerMetric.recordTimer();
        analyticsHub.recordEvent(event);

    }
}
