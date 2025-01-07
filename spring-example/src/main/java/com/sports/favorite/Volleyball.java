package com.sports.favorite;

public class Volleyball implements SportProvider{
    @Override
    public String getFavoriteSports() {
        return this.getClass().getSimpleName();
    }
}
