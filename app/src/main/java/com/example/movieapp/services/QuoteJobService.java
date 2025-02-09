package com.example.movieapp.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.movieapp.R;
import com.example.movieapp.ui.activity.QuoteDisplayActivity;
import com.example.movieapp.utilities.Constants;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuoteJobService extends JobService {


    private static final String TAG = QuoteJobService.class.getSimpleName();
    private static final int SHOWS_NOTIFICATION_ID = 6;
    private static final String NOTIFICATION_TITLE = "Quote for today";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<String> authorList, quoteList;

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG, "started JoB");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("quotes");
        getQuote(databaseReference);
        authorList = new ArrayList<>();
        quoteList = new ArrayList<>();
        return false;
    }


    private void getQuote(DatabaseReference databaseReference) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count ", "" + dataSnapshot.getChildrenCount());
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot : data.getChildren()) {
                        if (snapshot.getKey().equals("author")) {
                            authorList.add(snapshot.getValue() + "");
                            Log.d(TAG, " KEY-> Author-> " + snapshot.getValue());
                        }
                        if (snapshot.getKey().equals("quote")) {
                            quoteList.add(snapshot.getValue() + "");
                            Log.d(TAG, "KEY->Quote-> " + snapshot.getValue());
                        }
                    }
                }
                Log.d(TAG, "Size " + quoteList.size() + " " + authorList.size());
                showQuote(authorList, quoteList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onQuotesCancelled: ", databaseError.toException());
            }
        });

    }

    private void showQuote(List<String> authorList, List<String> quoteList) {
        if (quoteList.size() > 0) {
            int index = new Random().nextInt(quoteList.size() - 1);
            Log.d(TAG, "Show this " + quoteList.get(index) + " - of " + authorList.get(index));
            Intent intent = new Intent(getApplicationContext(), QuoteDisplayActivity.class);
            intent.putExtra(Constants.KEY_QUOTES, quoteList.get(index));
            intent.putExtra(Constants.KEY_AUTHOR, authorList.get(index));
            int requestCode = new Random().nextInt(100);
            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), requestCode,
                    intent, 0);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), "channel_Id")
                    .setContentTitle(NOTIFICATION_TITLE)
                    .setContentText(quoteList.get(index) + "\n" + " From: " + authorList.get(index))
                    .setSmallIcon(R.drawable.tv_red)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();
            NotificationManager mNotificationManager =
                    (NotificationManager) getApplicationContext()
                            .getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(SHOWS_NOTIFICATION_ID, notification);
        }
    }


    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}