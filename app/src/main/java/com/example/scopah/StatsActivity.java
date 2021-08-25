package com.example.scopah;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.scopah.utils.LocalData;
import com.example.scopah.utils.LocalDataDB;
import com.example.scopah.utils.MatchData;

import java.util.ArrayList;

public class StatsActivity extends ListActivity {
    private ArrayAdapter<MatchData> mAdapter;
    private ArrayList<MatchData> mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mModel = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, R.layout.activity_stats);
        ListView mListView = getListView();
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // do nothing
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocalData dao = new LocalDataDB();
        dao.open();

        mModel.clear();
        mModel.addAll(dao.getAllData());

        dao.close();

        mAdapter.notifyDataSetChanged();
    }
}