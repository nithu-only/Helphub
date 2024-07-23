//package com.example.helphub.entity;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@ExtendWith(MockitoExtension.class)
//public class QuestionTagTest {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @InjectMocks
//    private QuestionTag questionTag;
//
//    @BeforeEach
//    @Transactional
//    public void setUp() {
//        // Set up any necessary preconditions for your tests
//        questionTag = new QuestionTag();
//    }
//
//
//    @Test
//    public void testQuestionTagPersistence() {
//        Question question = new Question(); // Assuming a simple Question entity
//        question.setId(1L); // Set valid ID
//
//        Tag tag = new Tag(); // Assuming a simple Tag entity
//        tag.setId(1L); // Set valid ID
//
//        QuestionTag questionTag = new QuestionTag(); // Create a new instance of QuestionTag
//        questionTag.setQuestion(question);
//        questionTag.setTag(tag);
//
//        entityManager.persist(question);
//        entityManager.persist(tag);
//        entityManager.persist(questionTag);
//
//        // Flush to ensure the data is written to the database
//        entityManager.flush();
//
//        QuestionTag found = entityManager.find(QuestionTag.class, questionTag.getId());
//        assertNotNull(found, "QuestionTag should be persisted");
//        assertEquals(question.getId(), found.getQuestion().getId(), "Question should match");
//        assertEquals(tag.getId(), found.getTag().getId(), "Tag should match");
//    }
//    @Test
//    @Transactional
//    public void testQuestionTagNullQuestion() {
//        questionTag.setTag(new Tag()); // Assuming Tag entity
//
//        assertThrows(NullPointerException.class, () -> {
//            entityManager.persist(questionTag);
//            entityManager.flush(); // This will trigger the validation
//        }, "Should throw NullPointerException due to null Question");
//    }
//
//    @Test
//    @Transactional
//    public void testQuestionTagNullTag() {
//        questionTag.setQuestion(new Question()); // Assuming Question entity
//
//        assertThrows(NullPointerException.class, () -> {
//            entityManager.persist(questionTag);
//            entityManager.flush(); // This will trigger the validation
//        }, "Should throw NullPointerException due to null Tag");
//    }
//}



package com.example.helphub.entity;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // Ensure the Spring context is loaded
@Transactional // Run each test in a transaction
public class QuestionTagTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testQuestionTagPersistence() {
        Question question = new Question(); // Assuming a simple Question entity
        question.setId(1L); // Set valid ID

        Tag tag = new Tag(); // Assuming a simple Tag entity
        tag.setId(1L); // Set valid ID

        QuestionTag questionTag = new QuestionTag(); // Create a new instance of QuestionTag
        questionTag.setQuestion(question);
        questionTag.setTag(tag);

        entityManager.persist(question);
        entityManager.persist(tag);
        entityManager.persist(questionTag);

        // Flush to ensure the data is written to the database
        entityManager.flush();

        QuestionTag found = entityManager.find(QuestionTag.class, questionTag.getId());
        assertNotNull(found, "QuestionTag should be persisted");
        assertEquals(question.getId(), found.getQuestion().getId(), "Question should match");
        assertEquals(tag.getId(), found.getTag().getId(), "Tag should match");
    }

    @Test
    public void testQuestionTagNullQuestion() {
        QuestionTag questionTag = new QuestionTag(); // Create a new instance
        questionTag.setTag(new Tag()); // Assuming Tag entity

        assertThrows(NullPointerException.class, () -> {
            entityManager.persist(questionTag);
            entityManager.flush(); // This will trigger the validation
        }, "Should throw NullPointerException due to null Question");
    }

    @Test
    public void testQuestionTagNullTag() {
        QuestionTag questionTag = new QuestionTag(); // Create a new instance
        questionTag.setQuestion(new Question()); // Assuming Question entity

        assertThrows(NullPointerException.class, () -> {
            entityManager.persist(questionTag);
            entityManager.flush(); // This will trigger the validation
        }, "Should throw NullPointerException due to null Tag");
    }
}
