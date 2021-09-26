package com.crypto.core.exchanges.binance.client.impl;

import com.crypto.core.exchanges.binance.client.impl.utils.JsonWrapper;

@FunctionalInterface
public interface RestApiJsonParser<T> {

  T parseJson(JsonWrapper json);
}
