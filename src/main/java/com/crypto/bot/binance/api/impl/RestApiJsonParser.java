package com.crypto.bot.binance.api.impl;

import com.crypto.bot.binance.api.impl.utils.JsonWrapper;

@FunctionalInterface
public interface RestApiJsonParser<T> {

  T parseJson(JsonWrapper json);
}
