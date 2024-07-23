package com.example.helphub.service;

import com.example.helphub.dto.VoteDTO;
import com.example.helphub.exception.ResourceNotFoundException;
import com.example.helphub.entity.Question;
import com.example.helphub.entity.User;
import com.example.helphub.entity.Vote;
import com.example.helphub.repository.QuestionRepository;
import com.example.helphub.repository.UserRepository;
import com.example.helphub.repository.VoteRepository;
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

class VoteServiceTest {

    @InjectMocks
    private VoteService voteService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionRepository questionRepository;

    private Vote vote;
    private VoteDTO voteDTO;
    private User user;
    private Question question;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        question = new Question();
        question.setId(1L);

        vote = new Vote();
        vote.setId(1L);
        vote.setUpvote(true);
        vote.setUser(user);
        vote.setQuestion(question);

        voteDTO = new VoteDTO();
        voteDTO.setUpvote(true);
        voteDTO.setUserId(1L);
        voteDTO.setQuestionId(1L);
    }

    @Test
    void testFindAll() {
        when(voteRepository.findAll()).thenReturn(Arrays.asList(vote));

        List<VoteDTO> result = voteService.findAll();
        assertEquals(1, result.size());
        assertEquals(voteDTO.isUpvote(), result.get(0).isUpvote());

        verify(voteRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        when(voteRepository.findById(1L)).thenReturn(Optional.of(vote));

        VoteDTO result = voteService.findById(1L);
        assertEquals(voteDTO.isUpvote(), result.isUpvote());

        verify(voteRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_Failure() {
        when(voteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> voteService.findById(1L));

        verify(voteRepository, times(1)).findById(1L);
    }

    @Test
    void testSave_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(voteRepository.save(any(Vote.class))).thenReturn(vote);

        VoteDTO result = voteService.save(voteDTO);
        assertEquals(voteDTO.isUpvote(), result.isUpvote());

        verify(userRepository, times(1)).findById(1L);
        verify(questionRepository, times(1)).findById(1L);
        verify(voteRepository, times(1)).save(any(Vote.class));
    }

    @Test
    void testSave_Failure_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> voteService.save(voteDTO));

        verify(userRepository, times(1)).findById(1L);
        verify(questionRepository, times(0)).findById(anyLong());
        verify(voteRepository, times(0)).save(any(Vote.class));
    }

    @Test
    void testSave_Failure_QuestionNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> voteService.save(voteDTO));

        verify(userRepository, times(1)).findById(1L);
        verify(questionRepository, times(1)).findById(1L);
        verify(voteRepository, times(0)).save(any(Vote.class));
    }

    @Test
    void testUpdate_Success() {
        when(voteRepository.findById(1L)).thenReturn(Optional.of(vote));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(voteRepository.save(any(Vote.class))).thenReturn(vote);

        VoteDTO result = voteService.update(1L, voteDTO);
        assertEquals(voteDTO.isUpvote(), result.isUpvote());

        verify(voteRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(questionRepository, times(1)).findById(1L);
        verify(voteRepository, times(1)).save(any(Vote.class));
    }

    @Test
    void testUpdate_Failure_VoteNotFound() {
        when(voteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> voteService.update(1L, voteDTO));

        verify(voteRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).findById(anyLong());
        verify(questionRepository, times(0)).findById(anyLong());
        verify(voteRepository, times(0)).save(any(Vote.class));
    }

    @Test
    void testDelete_Success() {
        when(voteRepository.findById(1L)).thenReturn(Optional.of(vote));

        voteService.delete(1L);

        verify(voteRepository, times(1)).findById(1L);
        verify(voteRepository, times(1)).delete(vote);
    }

    @Test
    void testDelete_Failure() {
        when(voteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> voteService.delete(1L));

        verify(voteRepository, times(1)).findById(1L);
        verify(voteRepository, times(0)).delete(any(Vote.class));
    }
}
