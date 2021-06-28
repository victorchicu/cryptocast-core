package com.crypto.bot.picocli.commands.specific;

import com.crypto.bot.binance.model.market.SymbolPrice;
import com.crypto.bot.telegram.services.TelegramBotService;
import com.crypto.bot.picocli.commands.BaseCommand;
import com.crypto.bot.services.BinanceService;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;
import java.util.stream.Collectors;

@Component
@CommandLine.Command(
        name = "list",
        description = "Latest price for a set of symbols or omit symbol param to return all prices"
)
public class GetSymbolCommand extends BaseCommand {
    private final BinanceService binanceService;
    private final TelegramBotService telegramBotService;

    public GetSymbolCommand(BinanceService binanceService, TelegramBotService telegramBotService) {
        this.binanceService = binanceService;
        this.telegramBotService = telegramBotService;
    }

    @CommandLine.ParentCommand
    public BotCommand botCommand;

    @CommandLine.Parameters(
            arity = "1..*",
            paramLabel = "<symbols>",
            description = "The trading 'symbol' or shortened name (typically in capital letters) that refer to a coin on a trading platform. For example: BTCUSDT"
    )
    public List<String> symbols;

    @Override
    public void run() {
        Update update = botCommand.getUpdate();
        if (usageHelpRequested) {
            String usageHelp = usage(this);
            telegramBotService.sendMessage(update.message().chat().id(), usageHelp);
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

            telegramBotService.sendMessage(update.message().chat().id(), stringBuilder.toString());
        }
    }
}
