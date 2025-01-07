package com.sports.favorite;

public class Football implements SportProvider{
    @Override
    public String getFavoriteSports() {
        return this.getClass().getSimpleName();
    }
}
