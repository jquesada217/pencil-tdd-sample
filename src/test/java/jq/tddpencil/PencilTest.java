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
        String presetText = "This text";
        String appendText = " should append";
        String expectedText = presetText + appendText;

        // Act
        pencil.useExistingNote(presetText);
        pencil.write(appendText);
        String actualText = pencil.getText();

        // Assert
        assertThat(actualText, is(expectedText));
    }

}