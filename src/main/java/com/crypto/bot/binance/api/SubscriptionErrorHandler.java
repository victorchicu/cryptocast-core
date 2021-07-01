package com.crypto.bot.binance.api;

import com.crypto.bot.binance.api.exceptions.BinanceApiException;

/**
 * The error handler for the subscription.
 */
@FunctionalInterface
public interface SubscriptionErrorHandler {

  void onError(BinanceApiException exception);
}
