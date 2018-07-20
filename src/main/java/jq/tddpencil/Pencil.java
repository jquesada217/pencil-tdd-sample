package jq.tddpencil;

public class Pencil {

    // Private state
    private String text;

    // Constructors
    public Pencil() {
        text = "";
    }

    // Property accessors
    public String getText() {
        return text;
    }

    // Instance methods
    public void write(String textToWrite) {
        StringBuilder sb = new StringBuilder(text);
        sb.append(textToWrite);
        text = sb.toString();
    }
}
