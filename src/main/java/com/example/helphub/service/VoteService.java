package com.example.helphub.service;

import com.example.helphub.dto.VoteDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.Question;
import com.example.helphub.entity.User;
import com.example.helphub.entity.Vote;
import com.example.helphub.repository.QuestionRepository;
import com.example.helphub.repository.UserRepository;
import com.example.helphub.repository.VoteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<VoteDTO> findAll() {
        return voteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VoteDTO findById(Long id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vote not found with id " + id));
        return convertToDTO(vote);
    }

    public VoteDTO save(VoteDTO voteDTO) {
        User user = userRepository.findById(voteDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + voteDTO.getUserId()));
        Question question = questionRepository.findById(voteDTO.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + voteDTO.getQuestionId()));

        Vote vote = new Vote();
        BeanUtils.copyProperties(voteDTO, vote);
        vote.setUser(user);
        vote.setQuestion(question);

        Vote savedVote = voteRepository.save(vote);
        return convertToDTO(savedVote);
    }

    public VoteDTO update(Long id, VoteDTO voteDTO) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vote not found with id " + id));

        User user = userRepository.findById(voteDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + voteDTO.getUserId()));
        Question question = questionRepository.findById(voteDTO.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + voteDTO.getQuestionId()));

        BeanUtils.copyProperties(voteDTO, vote, "id");
        vote.setUser(user);
        vote.setQuestion(question);

        Vote updatedVote = voteRepository.save(vote);
        return convertToDTO(updatedVote);
    }

    public void delete(Long id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vote not found with id " + id));
        voteRepository.delete(vote);
    }

    private VoteDTO convertToDTO(Vote vote) {
        VoteDTO voteDTO = new VoteDTO();
        BeanUtils.copyProperties(vote, voteDTO);
        voteDTO.setUserId(vote.getUser().getId());
        voteDTO.setQuestionId(vote.getQuestion().getId());
        return voteDTO;
    }
}