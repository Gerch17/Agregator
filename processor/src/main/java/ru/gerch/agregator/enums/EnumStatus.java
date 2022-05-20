package ru.gerch.agregator.enums;

public enum EnumStatus {
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    REVIEW("REVIEW");

    private final String status;


    EnumStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return this.status;
    }

    public static EnumStatus fromString(String text) {
        for (EnumStatus b : EnumStatus.values()) {
            if (b.status.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
