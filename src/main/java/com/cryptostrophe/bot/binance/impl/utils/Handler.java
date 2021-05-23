package com.cryptostrophe.bot.binance.impl.utils;

@FunctionalInterface
public interface Handler<T> {

  void handle(T t);
}
