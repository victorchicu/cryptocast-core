package com.cryptostrophe.bot.picocli.commands.specific;

import com.cryptostrophe.bot.binance.model.market.SymbolPrice;
import com.cryptostrophe.bot.picocli.commands.BaseCommand;
import com.cryptostrophe.bot.services.BinanceService;
import com.cryptostrophe.bot.telegram.services.TelegramBotService;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetSymbolCommand extends BaseCommand {
    private final BinanceService binanceService;
    private final TelegramBotService telegramBotService;

    public GetSymbolCommand(BinanceService binanceService, TelegramBotService telegramBotService) {
        this.binanceService = binanceService;
        this.telegramBotService = telegramBotService;
    }

    @CommandLine.Option(names = {"help"}, help = true, description = "Display this help message.")
    private boolean usageHelpRequested;
    @CommandLine.Parameters(arity = "1..*", paramLabel = "<symbols>", description = "The trading 'symbol' or shortened name (typically in capital letters) that refer to a coin on a trading platform. For example: BTCUSDT")
    private List<String> symbols;

    @Override
    public void run() {
        if (usageHelpRequested) {
            String usageHelp = usage(this);
            //TODO: Send telegram message
        } else {
            List<SymbolPrice> symbolPrices = binanceService.getSymbolPrices(null);

            if (symbols != null) {
                symbolPrices = symbolPrices.stream()
                        .filter(symbolPrice ->
                                symbols.stream()
                                        .anyMatch(s -> s.equals(symbolPrice.getSymbol().toLowerCase().toLowerCase()))
                        )
                        .collect(Collectors.toList());
            }

            StringBuilder stringBuilder = new StringBuilder();

            for (SymbolPrice symbolPrice : symbolPrices) {
                stringBuilder.append(symbolPrice.getSymbol() + " " + symbolPrice.getPrice() + "\n");
            }

            //TODO: Send telegram message
//        telegramBotService.sendMessage(update.message().chat().id(), stringBuilder.toString());
        }
    }
}
