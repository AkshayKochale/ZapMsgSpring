package com.zap.main.util;

public enum ClientType {
    BOT("Bot"),
    IN_APP("In-App"),
    EMAIL("Email"),
    WHATSAPP("WhatsApp");

    private final String description;

    ClientType(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
}

