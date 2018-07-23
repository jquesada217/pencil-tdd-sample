package jq.tddpencil;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class PencilTest {

    @Test
    public void write_writesNewText() {
        // Arrange
        Pencil pencil = new Pencil();
        String text = "foo bar";

        // Act
        String actualText = pencil.write(text);

        // Assert
        assertThat(actualText, is(text));
    }

    @Test
    public void write_appendsToWrittenText() {
        // Arrange
        Pencil pencil = new Pencil();
        String presetText = "She sells sea shells";
        String appendText = " down by the sea shore";
        String expectedText = presetText + appendText;

        // Act
        String actualText = pencil.write(presetText, appendText);

        // Assert
        assertThat(actualText, is(expectedText));
    }

    @Test
    public void write_degradesDurability() {
        // Arrange
        int initialDurability = 20;
        String text = "E pluribus unum";
        Pencil pencil = new Pencil(initialDurability);

        // Act
        pencil.write(text);
        int remainingDurability = pencil.getDurability();

        // Assert
        assertThat(remainingDurability, is(lessThan(initialDurability)));
    }

    @Test
    public void write_insertsSpacesAfterDurabilityExpended() {
        // Arrange
        int initialDurability = 1;
        String text = "e pluribus unum";
        Pencil pencil = new Pencil(initialDurability);

        // Act
        String actualText = pencil.write(text);
        int remainingDurability = pencil.getDurability();

        // Assert
        assertThat(remainingDurability, is(lessThanOrEqualTo(0)));
        assertThat(actualText, is("e              "));
    }

    @Test
    public void write_degradesDurabilityFasterForCapitals() {
        // Arrange
        int initialDurability = 10;
        String upperText = "TODO";
        String lowerText = upperText.toLowerCase();
        Pencil upperPencil = new Pencil(initialDurability);
        Pencil lowerPencil = new Pencil(initialDurability);

        // Act
        String upperActualText = upperPencil.write(upperText);
        String lowerActualText = lowerPencil.write(lowerText);
        int upperRemainingDurability = upperPencil.getDurability();
        int lowerRemainingDurability = lowerPencil.getDurability();

        // Assert
        assertThat(upperRemainingDurability, is(2));
        assertThat(upperActualText, is(upperText));

        assertThat(lowerRemainingDurability, is(6));
        assertThat(lowerActualText, is(lowerText));
    }

    @Test
    public void write_consumesNoDurabilityForWhitespaceCharacters() {
        // Arrange
        int initialDurability = 1;
        String text = "\t \n\n \r\n   ";
        Pencil pencil = new Pencil(initialDurability);

        // Act
        pencil.write(text);
        int remainingDurability = pencil.getDurability();

        // Assert
        assertThat(remainingDurability, is(initialDurability));
    }

    @Test
    public void sharpen_restoresDurabilityIfAble() {
        // Arrange
        int initialDurability = 10;
        int initialPencilLength = 2;
        String text = "1234567890";
        Pencil pencil = new Pencil(initialDurability, initialPencilLength);

        // Act
        pencil.write(text);
        int preSharpenedDurability = pencil.getDurability();
        pencil.sharpen();
        int sharpenedPencilLength = pencil.getLength();
        int sharpenedDurability = pencil.getDurability();

        // Assert
        assertThat(preSharpenedDurability, is(0));
        assertThat(sharpenedDurability, is(initialDurability));
        assertThat(sharpenedPencilLength, is(lessThan(initialPencilLength)));
    }

    @Test
    public void sharpen_doesNotRestoreIfPencilTooShort() {
        // Arrange
        int initialDurability = 10;
        int initialPencilLength = 0;
        String text = "1234567890";
        Pencil pencil = new Pencil(initialDurability, initialPencilLength);

        // Act
        pencil.write(text);
        int preSharpenedDurability = pencil.getDurability();
        pencil.sharpen();
        int sharpenedPencilLength = pencil.getLength();
        int sharpenedDurability = pencil.getDurability();

        // Assert
        assertThat(preSharpenedDurability, is(0));
        assertThat(sharpenedDurability, is(0));
        assertThat(sharpenedPencilLength, is(initialPencilLength));
    }

    @Test
    public void erase_replacesTextWithSpaces() {
        // Arrange
        String text = "1234567890";
        String erase = "789";
        String expectedRemainingText = "123456   0";
        Pencil pencil = new Pencil();

        // Act
        String remaingText = pencil.erase(text, erase);

        // Assert
        assertThat(remaingText, is(expectedRemainingText));
    }

    @Test
    public void erase_doesPartialEraseWithLowEraser() {
        // Arrange
        String text = "Buffalo Bill";
        String erase = "Bill";
        String expectedRemainingText = "Buffalo B   ";
        int initialEraserLife = 3;
        Pencil pencil = new Pencil(10, 10, initialEraserLife);

        // Act
        String remainingText = pencil.erase(text, erase);

        // Assert
        assertThat(remainingText, is(expectedRemainingText));
    }

    @Test
    public void edit_canWriteOverErasedText() {
        // Arrange
        String text = "An       a day keeps the doctor away";
        String insert = "onion";
        String expectedEditedText = "An onion a day keeps the doctor away";
        Pencil pencil = new Pencil();

        // Act
        String editedText = pencil.edit(text, insert);

        // Assert
        assertThat(editedText, is(expectedEditedText));
    }

    @Test
    public void edit_canOverwriteAndAppendAtEndOfString() {
        // Arrange
        String text = "An apple a day keeps the doctor     ";
        String insert = "at bay";
        String expectedEditedText = "An apple a day keeps the doctor at bay";
        Pencil pencil = new Pencil();

        // Act
        String editedText = pencil.edit(text, insert);

        // Assert
        assertThat(editedText, is(expectedEditedText));
    }
}