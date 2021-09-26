package com.crypto.core.exchanges.binance.client;

import com.crypto.core.exchanges.binance.client.exceptions.BinanceApiException;

/**
 * The error handler for the subscription.
 */
@FunctionalInterface
public interface SubscriptionErrorHandler {

  void onError(BinanceApiException exception);
}
