package com.rpg;

public class GameLevel {
    private String name;
    private int observeTime;
    private int[][] map;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getObserveTime() {
        return observeTime;
    }

    public void setObserveTime(int observeTime) {
        this.observeTime = observeTime;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }
}
