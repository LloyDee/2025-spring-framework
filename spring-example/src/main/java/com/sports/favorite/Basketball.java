package com.sports.favorite;

public class Basketball implements SportProvider{
    @Override
    public String getFavoriteSports() {
        return this.getClass().getSimpleName();
    }
}
