package com.example.helphub.controller;

import com.example.helphub.dto.TagDTO;
import com.example.helphub.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<TagDTO> getAllTags() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public TagDTO getTagById(@PathVariable Long id) {
        return tagService.findById(id);
    }

    @PostMapping
    public TagDTO createTag(@Valid @RequestBody TagDTO tagDTO) {
        return tagService.save(tagDTO);
    }

    @PutMapping("/{id}")
    public TagDTO updateTag(@PathVariable Long id, @Valid @RequestBody TagDTO tagDTO) {
        return tagService.update(id, tagDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.delete(id);
    }
}
