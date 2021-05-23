package com.cryptostrophe.bot.binance.impl;

import com.cryptostrophe.bot.binance.impl.utils.JsonWrapper;

@FunctionalInterface
public interface RestApiJsonParser<T> {

  T parseJson(JsonWrapper json);
}
