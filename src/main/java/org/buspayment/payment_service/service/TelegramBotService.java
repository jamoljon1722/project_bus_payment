package org.buspayment.payment_service.service;

import org.buspayment.payment_service.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    private final String chatId;

    public TelegramBotService(
            @Value("${telegram.bot.username}") String botUsername,
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.chat-id}") String chatId) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.chatId = chatId;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
        System.out.println("Bot ishga tushdi!");
    }

    public void sendPaymentNotification(Payment payment) {
        String message = payment.getOwnerName() + " dan 1700 so'm yechildi.";
        SendMessage sendMessage = new SendMessage(chatId, message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();
            System.out.println("Foydalanuvchidan kelgan xabar: " + userMessage);

            if (userMessage.equalsIgnoreCase("Hello")) {
                SendMessage response = new SendMessage(update.getMessage().getChatId().toString(), "Salom!");
                try {
                    execute(response);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}