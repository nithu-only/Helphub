package com.example.helphub.repository;

import com.example.helphub.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    private Tag tag;

    @BeforeEach
    void setUp() {
        tag = new Tag();
        tag.setName("Spring");
        tag = tagRepository.save(tag);
    }

    @Test
    void testSaveTag() {
        Tag savedTag = tagRepository.save(tag);
        assertNotNull(savedTag);
        assertEquals(tag.getName(), savedTag.getName());
    }

    @Test
    void testFindById() {
        Optional<Tag> foundTag = tagRepository.findById(tag.getId());
        assertTrue(foundTag.isPresent());
        assertEquals(tag.getName(), foundTag.get().getName());
    }

    @Test
    void testFindAll() {
        Iterable<Tag> tags = tagRepository.findAll();
        assertNotNull(tags);
        assertTrue(tags.iterator().hasNext());
    }

    @Test
    void testDeleteTag() {
        tagRepository.delete(tag);
        Optional<Tag> foundTag = tagRepository.findById(tag.getId());
        assertFalse(foundTag.isPresent());
    }
}
