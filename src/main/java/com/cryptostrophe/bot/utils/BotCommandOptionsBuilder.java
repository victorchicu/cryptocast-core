package com.cryptostrophe.bot.utils;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class BotCommandOptionsBuilder {
    public static Options defaultOptions() {
        Options options = new Options();

        options.addOption(
                Option.builder("t")
                        .desc("24hr rolling window mini-ticker statistics for all symbols that changed.\n" +
                                "These are NOT the statistics of the UTC day, but a 24hr rolling window for the previous 24hrs.\n"
                        )
                        .longOpt("track")
                        .hasArgs()
                        .argName("symbol1, symbol2, ...")
                        .optionalArg(false)
                        .build()
        );

        options.addOption(
                Option.builder("l")
                        .desc("Latest price for a symbol or symbols\n" +
                                "If the symbol is not sent, prices for all symbols will be returned."
                        )
                        .longOpt("list")
                        .hasArgs()
                        .argName("symbol1, symbol2, ...")
                        .optionalArg(true)
                        .build()
        );

        options.addOption(
                Option.builder("s")
                        .desc("Stop all tracked subscriptions")
                        .longOpt("stop")
                        .build()
        );

        return options;
    }
}
