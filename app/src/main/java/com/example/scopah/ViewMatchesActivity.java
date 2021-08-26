package com.example.scopah;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.scopah.utils.AppContext;
import com.example.scopah.utils.LocalData;
import com.example.scopah.utils.LocalDataDB;
import com.example.scopah.utils.MatchData;

import java.util.ArrayList;

public class ViewMatchesActivity extends ListActivity {
    private ArrayAdapter<MatchData> mAdapter;
    private ArrayList<MatchData> mModel;

    boolean completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        completed = intent.getBooleanExtra("completed", true);

        mModel = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, R.layout.activity_view_matches, mModel);
        ListView mListView = getListView();
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AppContext.getAppContext(), LeaderboardActivity.class);

                MatchData data = mModel.get(position);
                intent.putExtra("names", data.getNames());
                intent.putExtra("colors", data.getColors());
                intent.putExtra("scores", data.getScores());
                intent.putExtra("completed", data.isCompleted());
                intent.putExtra("id", data.getId());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocalData dao = new LocalDataDB();
        dao.open();

        mModel.clear();
        mModel.addAll(dao.getAllData(completed));

        dao.close();

        mAdapter.notifyDataSetChanged();
    }
}