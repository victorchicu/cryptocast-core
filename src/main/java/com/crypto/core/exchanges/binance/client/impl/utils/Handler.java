package com.crypto.core.exchanges.binance.client.impl.utils;

@FunctionalInterface
public interface Handler<T> {

  void handle(T t);
}
