package com.example.helphub.service;

import com.example.helphub.dto.TagDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.Tag;
import com.example.helphub.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagServiceTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("Spring");

        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("Hibernate");

        when(tagRepository.findAll()).thenReturn(Arrays.asList(tag1, tag2));

        List<TagDTO> tags = tagService.findAll();

        assertEquals(2, tags.size());
        assertEquals("Spring", tags.get(0).getName());
        assertEquals("Hibernate", tags.get(1).getName());

        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Spring");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        TagDTO tagDTO = tagService.findById(1L);

        assertNotNull(tagDTO);
        assertEquals("Spring", tagDTO.getName());

        verify(tagRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_Failure() {
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            tagService.findById(1L);
        });

        verify(tagRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Spring");

        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Spring");

        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        TagDTO savedTagDTO = tagService.save(tagDTO);

        assertNotNull(savedTagDTO);
        assertEquals("Spring", savedTagDTO.getName());

        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    void testUpdate_Success() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Spring");

        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Spring Boot");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(tagRepository.save(tag)).thenReturn(tag);

        TagDTO updatedTagDTO = tagService.update(1L, tagDTO);

        assertNotNull(updatedTagDTO);
        assertEquals("Spring Boot", updatedTagDTO.getName());

        verify(tagRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void testUpdate_Failure() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("Spring Boot");

        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            tagService.update(1L, tagDTO);
        });

        verify(tagRepository, times(1)).findById(1L);
    }

    @Test
    void testDelete_Success() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Spring");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        tagService.delete(1L);

        verify(tagRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).delete(tag);
    }

    @Test
    void testDelete_Failure() {
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            tagService.delete(1L);
        });

        verify(tagRepository, times(1)).findById(1L);
    }
}
