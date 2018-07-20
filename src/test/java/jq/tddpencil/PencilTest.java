package jq.tddpencil;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class PencilTest {

    @Test
    public void skeletonTest() {
        Pencil pencil = new Pencil();

        assertThat(pencil, is(not(nullValue())));
    }
}