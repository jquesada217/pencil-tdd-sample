package jq.tddpencil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pencil {

    // Private state
    private int durability;
    private int length;
    private int eraser;
    private final int MAX_DURABILITY;
    private final int MAX_LENGTH;


    // Constructors
    public Pencil() {
        this(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public Pencil(int maxDurability) {
        this(maxDurability, Integer.MAX_VALUE);
    }

    public Pencil(int maxDurability, int maxLength) {
        this(maxDurability, maxLength, Integer.MAX_VALUE);
    }

    public Pencil(int maxDurability, int maxLength, int eraserLife) {
        MAX_DURABILITY = maxDurability;
        durability = MAX_DURABILITY;
        MAX_LENGTH = maxLength;
        length = MAX_LENGTH;
        eraser = eraserLife;
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
        StringBuilder sb = new StringBuilder(note);
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
        StringBuilder sb = new StringBuilder(note);
        String textToEraseStr = textToErase.toString();
        int lastIndex = sb.lastIndexOf(textToEraseStr);
        if (lastIndex > -1) {
            for (int i = lastIndex + textToEraseStr.length() - 1; i >= lastIndex; i--) {
                if (!Character.isWhitespace(sb.charAt(i)) && eraser > 0) {
                    sb.setCharAt(i, ' ');
                    eraser -= 1;
                }
            }
        }

        return sb.toString();
    }

    public String edit(CharSequence note, CharSequence textToOverwrite) {
        StringBuilder sb = new StringBuilder(note);
        String textToOverwriteStr = textToOverwrite.toString();

        // find a spot that was erased where new text can be inserted
        Pattern whitespacePattern = Pattern.compile("\\s{2,}");
        Matcher matcher = whitespacePattern.matcher(sb);
        if (matcher.find()) {
            int replacementStartIndex = 1 + matcher.start();
            int contentLength = sb.length();

            for (int i = 0, j = textToOverwriteStr.length(); i < j; i++) {
                boolean isOverwriting = replacementStartIndex + i < contentLength;
                char nextChar = textToOverwriteStr.charAt(i);
                int cost = getCharacterCost(nextChar);

                if (isOverwriting) { // overwrite characters mid-string
                    char currentChar = sb.charAt(replacementStartIndex + i);
                    if (Character.isWhitespace(currentChar)) {
                        if (durability >= cost) {
                            durability -= cost;
                            sb.setCharAt(replacementStartIndex + i, nextChar);
                        }
                    } else if (currentChar != nextChar) {
                        sb.setCharAt(replacementStartIndex + i, '@');
                    }
                } else { // append if the replacement text extends beyond the rest of the string
                    if (durability >= cost) {
                        durability -= cost;
                        sb.append(nextChar);
                    } else {
                        sb.append(' ');
                    }
                }
            }
        }

        return sb.toString();
    }

    private int getCharacterCost(char characterToWrite) {
        if (Character.isWhitespace(characterToWrite)) {
            return 0;
        } else if (Character.isUpperCase(characterToWrite)) {
            return 2;
        } else {
            return 1;
        }
    }
}
