package com.sports.favorite;

public class Overseer {
    private final SportProvider sportProvider;

    public Overseer(SportProvider sportProvider) {
        this.sportProvider = sportProvider;
    }
    public String getFavoriteSport() {
        return sportProvider.getFavoriteSports();
    }
}
