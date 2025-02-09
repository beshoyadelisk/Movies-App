package com.example.movieapp.ui.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.movieapp.App;
import com.example.movieapp.R;

import com.example.movieapp.services.QuoteJobService;
import com.example.movieapp.ui.MainFragment;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Trigger;

import javax.inject.Inject;

import static com.firebase.jobdispatcher.FirebaseJobDispatcher.SCHEDULE_RESULT_SUCCESS;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().add(android.R.id.content,
                new PrefFrag()).commit();
    }

    public static class PrefFrag extends PreferenceFragment implements
            Preference.OnPreferenceChangeListener {

        private static int periodicityForDayOne = 86400;
        private static int periodicityForTwoTimesInDay = 43200;
        private static int periodicityForThreeTimesInDay = 28800;
        @Inject
        SharedPreferences sharedPreferences;
        @Inject
        FirebaseJobDispatcher dispatcher;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ((App) getActivity().getApplication()).getNetComponent().inject(this);
            addPreferencesFromResource(R.xml.setting_act);
            bindPreference(findPreference(getString(R.string.preferences_key)));
            Preference switchPreference = findPreference(getString(R.string.quote_switch_key));
            Preference listPreferenceForTime = findPreference(getString(R.string.time_key));
            switchPreference.setOnPreferenceChangeListener((preference, o) -> {
                if (preference instanceof SwitchPreference) {
                    boolean quotePref = (Boolean) o;
                    if (quotePref) {
                        preference.setSummary(getActivity().getString(R.string.enabled));
                        listPreferenceForTime.setEnabled(true);
                    } else {
                        preference.setSummary(getActivity().getString(R.string.disabled));
                        dispatcher.cancelAll();
                        listPreferenceForTime.setEnabled(false);
                    }
                }
                return true;
            });
            listPreferenceForTime.setOnPreferenceChangeListener((preference, o) -> {
                String stringValue = o.toString();
                if (preference instanceof ListPreference) {
                    ListPreference listPreferenceTime = (ListPreference) preference;
                    int prefIndex = listPreferenceTime.findIndexOfValue(stringValue);
                    if (prefIndex >= 0) {
                        String clicked = String.valueOf
                                (listPreferenceTime.getEntries()[prefIndex]);
                        if (clicked.equals(getActivity().getString(R.string.once_day))) {
                            dispatcher.cancelAll();
                            Job quoteJob = dispatcher.newJobBuilder()
                                    .setService(QuoteJobService.class)
                                    .setTag(getActivity().getString(R.string.job_tag))
                                    .setRecurring(true)
                                    .setTrigger(Trigger
                                            .executionWindow(periodicityForDayOne,
                                                    periodicityForDayOne + 30))
                                    .build();
                            dispatcher.schedule(quoteJob);
                            if (dispatcher.schedule(quoteJob) == SCHEDULE_RESULT_SUCCESS) {
                                Toast.makeText(getActivity(),
                                        getActivity().getString(R.string.msg_quote_once),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (clicked.equals(getActivity().getString(R.string.twice_day))) {
                            dispatcher.cancelAll();
                            Job quoteJob = dispatcher.newJobBuilder()
                                    .setService(QuoteJobService.class)
                                    .setTag(getActivity().getString(R.string.job_tag))
                                    .setRecurring(true)
                                    .setTrigger(Trigger
                                            .executionWindow(periodicityForTwoTimesInDay,
                                                    periodicityForTwoTimesInDay + 30))
                                    .build();
                            dispatcher.schedule(quoteJob);
                            if (dispatcher.schedule(quoteJob) == SCHEDULE_RESULT_SUCCESS) {
                                Toast.makeText(getActivity(),
                                        getActivity().getString(R.string.msg_quote_twice),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (clicked.equals(getActivity().getString(R.string.thrice_day))) {
                            dispatcher.cancelAll();
                            Job quoteJob = dispatcher.newJobBuilder()
                                    .setService(QuoteJobService.class)
                                    .setTag(getActivity().getString(R.string.job_tag))
                                    .setRecurring(true)
                                    .setTrigger(Trigger
                                            .executionWindow(periodicityForThreeTimesInDay,
                                                    periodicityForThreeTimesInDay + 30))
                                    .build();
                            dispatcher.schedule(quoteJob);
                            if (dispatcher.schedule(quoteJob) == SCHEDULE_RESULT_SUCCESS) {
                                Toast.makeText(getActivity(),
                                        getActivity().getString(R.string.msg_quote_thrice),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        preference.setSummary(listPreferenceTime.getEntries()[prefIndex]);
                    }
                }
                return true;
            });
        }

        private void bindPreference(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            onPreferenceChange(preference, PreferenceManager
                    .getDefaultSharedPreferences(preference.getContext())
                    .getString(preference.getKey(), ""));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {

            String stringValue = o.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    sharedPreferences.edit().putString(getString(R.string.preferences_key)
                            , String.valueOf(prefIndex)).apply();
                    MainFragment.changed = true;
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }
            } else {
                // For other preferences, set the summary
                // to the value's simple string representation.
                preference.setSummary(stringValue);
            }
            return false;
        }
    }
}

