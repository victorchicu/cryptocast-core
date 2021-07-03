package com.crypto.bot.binance.client;

import com.crypto.bot.binance.client.exceptions.BinanceApiException;

/**
 * The error handler for the subscription.
 */
@FunctionalInterface
public interface SubscriptionErrorHandler {

  void onError(BinanceApiException exception);
}
