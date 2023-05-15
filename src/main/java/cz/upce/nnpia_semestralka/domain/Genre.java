package cz.upce.nnpia_semestralka.domain;

public enum Genre {
    AKCNI("akčni"),
    KOMEDIE("komedie"),
    DRAMA("drama"),
    HOROR("horor"),
    SCIFI("scifi");

    private final String displayValue;

    Genre(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
