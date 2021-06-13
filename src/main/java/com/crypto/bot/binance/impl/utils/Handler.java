package com.crypto.bot.binance.impl.utils;

@FunctionalInterface
public interface Handler<T> {

  void handle(T t);
}
