package com.example.scopah.utils;

import android.content.Intent;

public class PlayerData {
    private String name;
    private String color;
    private Integer score;

    public PlayerData(String name, String color, Integer score) {
        this.name = name;
        this.color = color;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Integer getScore() {
        return score;
    }
}
