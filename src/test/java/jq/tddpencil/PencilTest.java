package jq.tddpencil;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class PencilTest {

    @Test
    public void writeNewNote_writesNewText() {
        // Arrange
        Pencil pencil = new Pencil();
        String text = "foo bar";

        // Act
        pencil.writeNewNote(text);
        String actualText = pencil.getText();

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
        pencil.useExistingNote(presetText);
        pencil.write(appendText);
        String actualText = pencil.getText();

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
        pencil.writeNewNote(text);
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
        pencil.writeNewNote(text);
        int remainingDurability = pencil.getDurability();
        String actualText = pencil.getText();

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
        upperPencil.writeNewNote(upperText);
        lowerPencil.writeNewNote(lowerText);
        int upperRemainingDurability = upperPencil.getDurability();
        int lowerRemainingDurability = lowerPencil.getDurability();
        String upperActualText = upperPencil.getText();
        String lowerActualText = lowerPencil.getText();

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
        pencil.writeNewNote(text);
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
        pencil.writeNewNote(text);
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
        pencil.writeNewNote(text);
        int preSharpenedDurability = pencil.getDurability();
        pencil.sharpen();
        int sharpenedPencilLength = pencil.getLength();
        int sharpenedDurability = pencil.getDurability();

        // Assert
        assertThat(preSharpenedDurability, is(0));
        assertThat(sharpenedDurability, is(0));
        assertThat(sharpenedPencilLength, is(initialPencilLength));
    }

}