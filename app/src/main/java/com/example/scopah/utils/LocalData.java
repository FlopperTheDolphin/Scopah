package com.example.scopah.utils;

import java.util.ArrayList;

public interface LocalData {
    public void open();
    public void close();

    public ArrayList<MatchData> getAllData(boolean completed);
    public MatchData insertData(MatchData data);
    public int deleteData(long id);
}
