package com.crypto.bot.picocli.services.impl;

import com.crypto.bot.picocli.commands.specific.BotCommand;
import com.crypto.bot.picocli.services.PicoCliService;
import com.crypto.bot.telegram.services.TelegramBotService;
import com.pengrad.telegrambot.model.Update;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import picocli.CommandLine;
import picocli.spring.PicocliSpringFactory;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PicoCliServiceImpl implements PicoCliService {
    private static final String SPLIT_CHARACTER = " ";
    private static final String BOT_GROUP_SUFFIX = "@cryptostrophebot";

    private final ApplicationContext context;
    private final TelegramBotService telegramBotService;

    public PicoCliServiceImpl(ApplicationContext context, TelegramBotService telegramBotService) {
        this.context = context;
        this.telegramBotService = telegramBotService;
    }

    @Override
    public int execute(String command, Update... updates) {
        Update update = updates.length > 0 ? updates[0] : null;
        String[] args = toArgs(command.toLowerCase());
        CommandLine commandLine = new CommandLine(new BotCommand(update, telegramBotService), new PicocliSpringFactory(context));
        commandLine.setExecutionStrategy(new CommandLine.RunLast());
        return commandLine.execute(args);
    }

    @Override
    public CommandLine.ParseResult parse(String command) {
        String[] args = toArgs(command);
        CommandLine commandLine = new CommandLine(new BotCommand(null, null), new PicocliSpringFactory(context));
        commandLine.setExecutionStrategy(new CommandLine.RunLast());
        commandLine.getCommandSpec().parser().collectErrors(true);
        return commandLine.parseArgs(args);
    }

    private String[] toArgs(String command) {
        command = command.replaceFirst("/", "");
        command = command.replace(BOT_GROUP_SUFFIX, "");
        String[] args = command.split(SPLIT_CHARACTER);
        if (command.startsWith("bot")) {
            Stream<String> stream = Arrays.stream(command.split(SPLIT_CHARACTER));
            args = stream.skip(1).collect(Collectors.toList()).toArray(new String[0]);
        }
        return args;
    }
}
