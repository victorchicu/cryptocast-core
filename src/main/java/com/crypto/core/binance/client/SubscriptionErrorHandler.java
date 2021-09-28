package com.crypto.core.binance.client;

import com.crypto.core.binance.client.exceptions.BinanceApiException;

/**
 * The error handler for the subscription.
 */
@FunctionalInterface
public interface SubscriptionErrorHandler {

  void onError(BinanceApiException exception);
}
