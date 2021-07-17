package com.crypto.cli.binance.client.impl.utils;

@FunctionalInterface
public interface Handler<T> {

  void handle(T t);
}
