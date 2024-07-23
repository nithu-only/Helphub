package com.example.helphub.repository;

import com.example.helphub.entity.Question;
import com.example.helphub.entity.QuestionTag;
import com.example.helphub.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class QuestionTagRepositoryTest {

    @Autowired
    private QuestionTagRepository questionTagRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagRepository tagRepository;

    private Question question;
    private Tag tag;

    @BeforeEach
    void setUp() {
        question = new Question();
        question.setTitle("Sample Question");
        question.setContent("This is a sample question.");
        question = questionRepository.save(question);

        tag = new Tag();
        tag.setName("Sample Tag");
        tag = tagRepository.save(tag);
    }

    @Test
    void whenSaveQuestionTag_thenFindById() {
        QuestionTag questionTag = new QuestionTag();
        questionTag.setQuestion(question);
        questionTag.setTag(tag);
        questionTag = questionTagRepository.save(questionTag);

        Optional<QuestionTag> found = questionTagRepository.findById(questionTag.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getQuestion().getId()).isEqualTo(question.getId());
        assertThat(found.get().getTag().getId()).isEqualTo(tag.getId());
    }

    @Test
    void whenFindByQuestionId_thenReturnQuestionTags() {
        QuestionTag questionTag = new QuestionTag();
        questionTag.setQuestion(question);
        questionTag.setTag(tag);
        questionTag = questionTagRepository.save(questionTag);

        List<QuestionTag> found = questionTagRepository.findByQuestionId(question.getId());
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getTag().getId()).isEqualTo(tag.getId());
    }

    @Test
    void whenFindByTagId_thenReturnQuestionTags() {
        QuestionTag questionTag = new QuestionTag();
        questionTag.setQuestion(question);
        questionTag.setTag(tag);
        questionTag = questionTagRepository.save(questionTag);

        List<QuestionTag> found = questionTagRepository.findByTagId(tag.getId());
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getQuestion().getId()).isEqualTo(question.getId());
    }

    @Test
    void whenDeleteQuestionTag_thenNotFound() {
        QuestionTag questionTag = new QuestionTag();
        questionTag.setQuestion(question);
        questionTag.setTag(tag);
        questionTag = questionTagRepository.save(questionTag);

        questionTagRepository.deleteById(questionTag.getId());

        Optional<QuestionTag> found = questionTagRepository.findById(questionTag.getId());
        assertThat(found).isNotPresent();
    }
}
