package com.crypto.core.binance.client.impl;

import com.crypto.core.binance.client.impl.utils.JsonWrapper;

@FunctionalInterface
public interface RestApiJsonParser<T> {

  T parseJson(JsonWrapper json);
}
