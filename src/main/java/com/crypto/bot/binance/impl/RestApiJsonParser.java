package com.crypto.bot.binance.impl;

import com.crypto.bot.binance.impl.utils.JsonWrapper;

@FunctionalInterface
public interface RestApiJsonParser<T> {

  T parseJson(JsonWrapper json);
}
