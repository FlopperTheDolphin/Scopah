package com.example.scopah.utils;

import java.util.ArrayList;

public interface LocalData {
    public void open();
    public void close();

    public ArrayList<MatchData> getAllData();
    public MatchData insertData(MatchData data);
}
