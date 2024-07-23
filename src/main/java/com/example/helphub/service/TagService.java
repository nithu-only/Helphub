//package com.example.helphub.service;
//
//import com.example.helphub.dto.TagDTO;
//import com.example.helphub.responsedtos.TagResponseDTO;
//import com.example.helphub.exception.ResourceNotFoundException;
//import com.example.helphub.entity.Tag;
//import com.example.helphub.repository.TagRepository;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class TagService {
//
//    @Autowired
//    private TagRepository tagRepository;
//
//    public List<TagResponseDTO> findAll() {
//        return tagRepository.findAll().stream()
//                .map(this::convertToResponseDTO)
//                .collect(Collectors.toList());
//    }
//
//    public TagResponseDTO findById(Long id) {
//        Tag tag = tagRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + id));
//        return convertToResponseDTO(tag);
//    }
//
//    public TagResponseDTO save(TagDTO tagDTO) {
//        Tag tag = new Tag();
//        BeanUtils.copyProperties(tagDTO, tag);
//
//        Tag savedTag = tagRepository.save(tag);
//        return convertToResponseDTO(savedTag);
//    }
//
//    public TagResponseDTO update(Long id, TagDTO tagDTO) {
//        Tag tag = tagRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + id));
//
//        BeanUtils.copyProperties(tagDTO, tag, "id");
//        Tag updatedTag = tagRepository.save(tag);
//        return convertToResponseDTO(updatedTag);
//    }
//
//    public void delete(Long id) {
//        Tag tag = tagRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + id));
//        tagRepository.delete(tag);
//    }
//
//    private TagResponseDTO convertToResponseDTO(Tag tag) {
//        TagResponseDTO tagResponseDTO = new TagResponseDTO();
//        BeanUtils.copyProperties(tag, tagResponseDTO);
//        return tagResponseDTO;
//    }
//}



package com.example.helphub.service;

import com.example.helphub.dto.TagDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.Tag;
import com.example.helphub.repository.TagRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<TagDTO> findAll() {
        return tagRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TagDTO findById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + id));
        return convertToDTO(tag);
    }

    public TagDTO save(TagDTO tagDTO) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagDTO, tag);

        Tag savedTag = tagRepository.save(tag);
        return convertToDTO(savedTag);
    }

    public TagDTO update(Long id, TagDTO tagDTO) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + id));

        BeanUtils.copyProperties(tagDTO, tag, "id");
        Tag updatedTag = tagRepository.save(tag);
        return convertToDTO(updatedTag);
    }

    public void delete(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + id));
        tagRepository.delete(tag);
    }

    private TagDTO convertToDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        BeanUtils.copyProperties(tag, tagDTO);
        return tagDTO;
    }
}
