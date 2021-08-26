package com.example.scopah.utils;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MatchData {
    private ArrayList<PlayerData> players;

    private boolean completed;
    private long id;

    private DateFormat format;
    private static final String DATE_FORMAT = "E dd MMMM yyyy HH:mm:ss";

    public MatchData(ArrayList<PlayerData> players, boolean completed, long id) {
        this.players = players;
        this.completed = completed;
        this.id = id;

        format = new SimpleDateFormat(DATE_FORMAT);
    }

    public MatchData(ArrayList<String> names,
              ArrayList<String> colors,
              ArrayList<Integer> scores, boolean completed, long id) {
        players = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            PlayerData p = new PlayerData(names.get(i), colors.get(i), scores.get(i));
            players.add(p);
        }

        this.completed = completed;
        this.id = id;

        format = new SimpleDateFormat(DATE_FORMAT);
    }

    public boolean isCompleted() {
        return completed;
    }

    public long getId() {
        return id;
    }

    public ArrayList<PlayerData> getPlayers() {
        return players;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();

        for (PlayerData p : players)
            names.add(p.getName());

        return names;
    }

    public ArrayList<String> getColors() {
        ArrayList<String> colors = new ArrayList<>();

        for (PlayerData p : players)
            colors.add(p.getColor());

        return colors;
    }

    public ArrayList<Integer> getScores() {
        ArrayList<Integer> scores = new ArrayList<>();

        for (PlayerData p : players)
            scores.add(p.getScore());

        return scores;
    }

    @Override
    public String toString() {
        return format.format(id);
    }
}
