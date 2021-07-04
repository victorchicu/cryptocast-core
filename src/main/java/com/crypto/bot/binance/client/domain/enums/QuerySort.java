package com.crypto.bot.binance.client.domain.enums;

public enum QuerySort {

  ASC("asc"),
  DESC("desc");

  private final String code;

  QuerySort(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}