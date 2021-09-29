package com.crypto.core.watchlist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class WatchlistDto {
    private final String id;
    private final String coinName;

    @JsonCreator
    public WatchlistDto(String id, String coinName) {
        this.id = id;
        this.coinName = coinName;
    }

    public String getId() {
        return id;
    }

    public String getCoinName() {
        return coinName;
    }
}
