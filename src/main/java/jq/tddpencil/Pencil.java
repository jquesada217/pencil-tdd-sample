package jq.tddpencil;

public class Pencil {

    // Private state
    private String text;
    private int durability;
    private final int MAX_DURABILITY;

    // Constructors
    public Pencil() {
        text = "";
        MAX_DURABILITY = Integer.MAX_VALUE;
        durability = MAX_DURABILITY;
    }

    public Pencil(int maxDurability) {
        text = "";
        MAX_DURABILITY = maxDurability;
        durability = MAX_DURABILITY;
    }

    // Property accessors
    public String getText() {
        return text;
    }

    public int getDurability() {
        return durability;
    }

    // Instance methods
    public void write(String textToWrite) {
        StringBuilder sb = new StringBuilder(text);
        char[] appendableChars = textToWrite.toCharArray();
        for (int i = 0, j = appendableChars.length; i < j; i++) {
            if (!Character.isWhitespace(appendableChars[i])) {
                if (Character.isUpperCase(appendableChars[i]) && durability > 1) {
                    durability -= 2;
                } else if (Character.isLowerCase(appendableChars[i]) && durability > 0) {
                    durability -= 1;
                } else {
                    appendableChars[i] = ' ';
                }
            }
        }

        sb.append(appendableChars);
        text = sb.toString();
    }

    public void writeNewNote(String textToWrite) {
        text = "";
        write(textToWrite);
    }

    public void useExistingNote(String note) {
        text = note;
    }
}
