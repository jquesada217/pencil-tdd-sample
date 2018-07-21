package jq.tddpencil;

public class Pencil {

    // Private state
    private String text;
    private int durability;
    private int length;
    private final int MAX_LENGTH;
    private final int MAX_DURABILITY;

    // Constructors
    public Pencil() {
        this(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public Pencil(int maxDurability) {
        this(maxDurability, Integer.MAX_VALUE);
    }

    public Pencil(int maxDurability, int maxLength) {
        text = "";
        MAX_DURABILITY = maxDurability;
        durability = MAX_DURABILITY;
        MAX_LENGTH = maxLength;
        length = MAX_LENGTH;
    }

    // Property accessors
    public String getText() {
        return text;
    }

    public int getDurability() {
        return durability;
    }

    public int getLength() {
        return length;
    }

    // Instance methods
    public void write(String textToWrite) {
        StringBuilder sb = new StringBuilder(text);
        char[] appendableChars = textToWrite.toCharArray();
        for (int i = 0, j = appendableChars.length; i < j; i++) {
            if (!Character.isWhitespace(appendableChars[i])) {
                if (Character.isUpperCase(appendableChars[i]) && durability > 1) {
                    durability -= 2;
                } else if (durability > 0) {
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

    public void sharpen() {
        if (length > 0) {
            length -= 1;
            durability = MAX_DURABILITY;
        } else {
            System.err.println("Your pencil has been reduced to a stub.  You should get a new one");
        }
    }

    public void erase(String text) {
        
    }
}
