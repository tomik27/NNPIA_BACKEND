package cz.upce.nnpia_semestralka.domain;

public enum TypeOfPerson {
    DIRECTOR("D"),
    ACTOR("ACTOR");

    private final String displayValue;

    TypeOfPerson(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}


