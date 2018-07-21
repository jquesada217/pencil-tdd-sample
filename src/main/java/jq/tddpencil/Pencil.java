package jq.tddpencil;

public class Pencil {

    // Private state
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
        MAX_DURABILITY = maxDurability;
        durability = MAX_DURABILITY;
        MAX_LENGTH = maxLength;
        length = MAX_LENGTH;
    }

    // Property accessors
    public int getDurability() {
        return durability;
    }

    public int getLength() {
        return length;
    }

    // Instance methods
    public String write(CharSequence note, CharSequence textToWrite) {
        StringBuffer sb = new StringBuffer(note);
        char[] newChars = textToWrite.toString().toCharArray();
        for (int i = 0, j = newChars.length; i < j; i++) {
            if (!Character.isWhitespace(newChars[i])) {
                if (Character.isUpperCase(newChars[i]) && durability > 1) {
                    durability -= 2;
                } else if (durability > 0) {
                    durability -= 1;
                } else {
                    newChars[i] = ' ';
                }
            }
        }

        return sb.append(newChars).toString();
    }

    public String write(CharSequence textToWrite) {
        return write("", textToWrite);
    }

    public void sharpen() {
        if (length > 0) {
            length -= 1;
            durability = MAX_DURABILITY;
        } else {
            System.err.println("Your pencil has been reduced to a stub.  You should get a new one");
        }
    }

    public String erase(CharSequence textToErase) {
        return erase("", textToErase);
    }

    public String erase(CharSequence note, CharSequence textToErase) {
        return "";
    }
}
