package com.crypto.cli.binance.client.impl;

import com.crypto.cli.binance.client.impl.utils.JsonWrapper;

@FunctionalInterface
public interface RestApiJsonParser<T> {

  T parseJson(JsonWrapper json);
}
