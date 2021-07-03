package com.crypto.bot.binance.client.impl;

import com.crypto.bot.binance.client.impl.utils.JsonWrapper;

@FunctionalInterface
public interface RestApiJsonParser<T> {

  T parseJson(JsonWrapper json);
}
