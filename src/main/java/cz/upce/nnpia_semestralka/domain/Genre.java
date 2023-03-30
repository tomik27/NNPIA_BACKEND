package cz.upce.nnpia_semestralka.domain;

public enum Genre {
    HORROR("HORROR"),
    COMEDY("COMEDY"),
    KRIMI("KRIMI");

    private final String displayValue;

    Genre(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
