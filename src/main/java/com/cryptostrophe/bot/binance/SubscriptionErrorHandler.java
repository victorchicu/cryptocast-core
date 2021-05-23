package com.cryptostrophe.bot.binance;

import com.cryptostrophe.bot.binance.exception.BinanceApiException;

/**
 * The error handler for the subscription.
 */
@FunctionalInterface
public interface SubscriptionErrorHandler {

  void onError(BinanceApiException exception);
}
