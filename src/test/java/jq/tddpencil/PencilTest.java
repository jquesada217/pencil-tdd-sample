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
        String text1 = "This text";
        String text2 = " should append";
        String expectedText = text1 + text2;

        // Act
        pencil.write(text1);
        pencil.write(text2);
        String actualText = pencil.getText();

        // Assert
        assertThat(actualText, is(expectedText));
    }

}