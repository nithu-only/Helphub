package com.example.helphub.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    @Test
    void testGettersAndSetters() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Spring");

        assertEquals(1L, tag.getId());
        assertEquals("Spring", tag.getName());
    }


    @Test
    void testEqualsAndHashCode() {
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("Spring");

        Tag tag2 = new Tag();
        tag2.setId(1L);
        tag2.setName("Spring");

        Tag tag3 = new Tag();
        tag3.setId(2L);
        tag3.setName("Hibernate");

        assertEquals(tag1, tag2);
        assertNotEquals(tag1, tag3);
        assertEquals(tag1.hashCode(), tag2.hashCode());
        assertNotEquals(tag1.hashCode(), tag3.hashCode());
    }

    @Test
    void testToString() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("nithin");

        String expected = "Tag(id=1, name=nithin)";
        assertEquals(expected, tag.toString());
    }
}
