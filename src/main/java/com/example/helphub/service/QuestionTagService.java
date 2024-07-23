//package com.example.helphub.service;
//
//import com.example.helphub.dto.QuestionTagDTO;
//import com.example.helphub.exception.ResourceNotFoundException;
//import com.example.helphub.entity.Question;
//import com.example.helphub.entity.QuestionTag;
//import com.example.helphub.entity.Tag;
//import com.example.helphub.repository.QuestionRepository;
//import com.example.helphub.repository.QuestionTagRepository;
//import com.example.helphub.repository.TagRepository;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class QuestionTagService {
//
//    @Autowired
//    private QuestionTagRepository questionTagRepository;
//
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    @Autowired
//    private TagRepository tagRepository;
//
//    public List<QuestionTagDTO> findAll() {
//        return questionTagRepository.findAll().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    public QuestionTagDTO findById(Long id) {
//        QuestionTag questionTag = questionTagRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("QuestionTag not found with id " + id));
//        return convertToDTO(questionTag);
//    }
//
//    public QuestionTagDTO save(QuestionTagDTO questionTagDTO) {
//        Question question = questionRepository.findById(questionTagDTO.getQuestionId())
//                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionTagDTO.getQuestionId()));
//        Tag tag = tagRepository.findById(questionTagDTO.getTagId())
//                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + questionTagDTO.getTagId()));
//
//        QuestionTag questionTag = new QuestionTag();
//        BeanUtils.copyProperties(questionTagDTO, questionTag);
//        questionTag.setQuestion(question);
//        questionTag.setTag(tag);
//
//        QuestionTag savedQuestionTag = questionTagRepository.save(questionTag);
//        return convertToDTO(savedQuestionTag);
//    }
//
//    public QuestionTagDTO update(Long id, QuestionTagDTO questionTagDTO) {
//        QuestionTag questionTag = questionTagRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("QuestionTag not found with id " + id));
//
//        Question question = questionRepository.findById(questionTagDTO.getQuestionId())
//                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionTagDTO.getQuestionId()));
//        Tag tag = tagRepository.findById(questionTagDTO.getTagId())
//                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + questionTagDTO.getTagId()));
//
//        BeanUtils.copyProperties(questionTagDTO, questionTag, "id");
//        questionTag.setQuestion(question);
//        questionTag.setTag(tag);
//
//        QuestionTag updatedQuestionTag = questionTagRepository.save(questionTag);
//        return convertToDTO(updatedQuestionTag);
//    }
//
//    public void delete(Long id) {
//        QuestionTag questionTag = questionTagRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("QuestionTag not found with id " + id));
//        questionTagRepository.delete(questionTag);
//    }
//
//    private QuestionTagDTO convertToDTO(QuestionTag questionTag) {
//        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
//        BeanUtils.copyProperties(questionTag, questionTagDTO);
//        questionTagDTO.setQuestionId(questionTag.getQuestion().getId());
//        questionTagDTO.setTagId(questionTag.getTag().getId());
//        return questionTagDTO;
//    }
//}


package com.example.helphub.service;

import com.example.helphub.dto.QuestionTagDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.Question;
import com.example.helphub.entity.QuestionTag;
import com.example.helphub.entity.Tag;
import com.example.helphub.repository.QuestionRepository;
import com.example.helphub.repository.QuestionTagRepository;
import com.example.helphub.repository.TagRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionTagService {

    @Autowired
    private QuestionTagRepository questionTagRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<QuestionTagDTO> findAll() {
        return questionTagRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public QuestionTagDTO findById(Long id) {
        QuestionTag questionTag = questionTagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionTag not found with id " + id));
        return convertToDTO(questionTag);
    }

    public QuestionTagDTO save(QuestionTagDTO questionTagDTO) {
        Question question = questionRepository.findById(questionTagDTO.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionTagDTO.getQuestionId()));
        Tag tag = tagRepository.findById(questionTagDTO.getTagId())
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + questionTagDTO.getTagId()));

        QuestionTag questionTag = new QuestionTag();
        BeanUtils.copyProperties(questionTagDTO, questionTag);
        questionTag.setQuestion(question);
        questionTag.setTag(tag);

        QuestionTag savedQuestionTag = questionTagRepository.save(questionTag);
        return convertToDTO(savedQuestionTag);
    }

    public QuestionTagDTO update(Long id, QuestionTagDTO questionTagDTO) {
        QuestionTag questionTag = questionTagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionTag not found with id " + id));

        Question question = questionRepository.findById(questionTagDTO.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionTagDTO.getQuestionId()));
        Tag tag = tagRepository.findById(questionTagDTO.getTagId())
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + questionTagDTO.getTagId()));

        BeanUtils.copyProperties(questionTagDTO, questionTag, "id");
        questionTag.setQuestion(question);
        questionTag.setTag(tag);

        QuestionTag updatedQuestionTag = questionTagRepository.save(questionTag);
        return convertToDTO(updatedQuestionTag);
    }

    public void delete(Long id) {
        QuestionTag questionTag = questionTagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionTag not found with id " + id));
        questionTagRepository.delete(questionTag);
    }

    private QuestionTagDTO convertToDTO(QuestionTag questionTag) {
        QuestionTagDTO questionTagDTO = new QuestionTagDTO();
        BeanUtils.copyProperties(questionTag, questionTagDTO);
        questionTagDTO.setQuestionId(questionTag.getQuestion().getId());
        questionTagDTO.setTagId(questionTag.getTag().getId());
        return questionTagDTO;
    }
}
