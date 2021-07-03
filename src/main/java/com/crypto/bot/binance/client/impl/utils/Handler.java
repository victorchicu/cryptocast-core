package com.crypto.bot.binance.client.impl.utils;

@FunctionalInterface
public interface Handler<T> {

  void handle(T t);
}
