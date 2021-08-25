package com.example.scopah.utils;

import java.util.ArrayList;

public class MatchData {
    private ArrayList<PlayerData> players;

    private boolean completed;
    private long id;

    public MatchData(ArrayList<PlayerData> players, boolean completed, long id) {
        this.players = players;
        this.completed = completed;
        this.id = id;
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
}
