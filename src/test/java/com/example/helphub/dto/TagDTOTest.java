package com.example.helphub.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagDTOTest {

    @Test
    void testGettersAndSetters() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(1L);
        tagDTO.setName("Spring");

        assertEquals(1L, tagDTO.getId());
        assertEquals("Spring", tagDTO.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        TagDTO tagDTO1 = new TagDTO();
        tagDTO1.setId(1L);
        tagDTO1.setName("Spring");

        TagDTO tagDTO2 = new TagDTO();
        tagDTO2.setId(1L);
        tagDTO2.setName("Spring");

        TagDTO tagDTO3 = new TagDTO();
        tagDTO3.setId(2L);
        tagDTO3.setName("Hibernate");

        assertEquals(tagDTO1, tagDTO2);
        assertNotEquals(tagDTO1, tagDTO3);
        assertEquals(tagDTO1.hashCode(), tagDTO2.hashCode());
        assertNotEquals(tagDTO1.hashCode(), tagDTO3.hashCode());
    }
}
