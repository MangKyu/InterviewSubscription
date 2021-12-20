package com.mangkyu.employment.interview.app.quiz.service;

import com.mangkyu.employment.interview.app.quiz.constants.QuizConstants;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequest;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.enums.QuizCategory;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import com.mangkyu.employment.interview.app.solvedquiz.repository.SolvedQuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @InjectMocks
    private QuizService quizService;

    @Mock
    private QuizRepository quizRepository;
    @Mock
    private SolvedQuizRepository solvedQuizRepository;
    @Spy
    private ModelMapper modelMapper;

    private final Long userId = -1L;
    private final QuizLevel quizLevel = QuizLevel.NEW;
    private final int quizSize = 3;

    @BeforeEach
    public void init() {
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
    }

    @Test
    public void addQuiz_Success() {
        // given
        final AddQuizRequest request = AddQuizRequest.builder()
                .title("quiz")
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();

        // when
        quizService.addQuiz(request);

        // then

        // verify
        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    @Test
    public void getUnsolvedQuizSuccess_SolvedQuizEmpty() {
        // given
        final List<SolvedQuiz> solvedQuizList = solvedQuizList();
        final Set<Long> solvedQuizIdList = solvedQuizList.stream()
                .map(v -> v.getQuiz().getId())
                .collect(Collectors.toSet());
        final List<Quiz> unsolvedQuizList = Collections.singletonList(quiz(4L));

        doReturn(solvedQuizList).when(solvedQuizRepository).findAllByUser_Id(userId);
        doReturn(unsolvedQuizList).when(quizRepository).findByIdNotInAndQuizLevel(solvedQuizIdList, quizLevel);

        // when
        final List<Quiz> result = quizService.getUnsolvedQuizList(userId, quizLevel);

        // then
        assertThat(result.size()).isEqualTo(unsolvedQuizList.size());
    }

    @Test
    public void getUnsolvedQuizSuccess_SolvedQuizNotEmpty() {
        // given
        final List<SolvedQuiz> solvedQuizList = Collections.emptyList();
        final List<Quiz> unsolvedQuizList = Collections.singletonList(quiz(4L));

        doReturn(solvedQuizList).when(solvedQuizRepository).findAllByUser_Id(userId);
        doReturn(unsolvedQuizList).when(quizRepository).findByQuizLevel(quizLevel);

        // when
        final List<Quiz> result = quizService.getUnsolvedQuizList(userId, quizLevel);

        // then
        assertThat(result.size()).isEqualTo(unsolvedQuizList.size());
    }

    @Test
    public void getRandomQuizListUnderLimitSuccess_UnderLimit() {
        // given
        final List<Quiz> unsolvedQuizList = Collections.singletonList(quiz(4L));

        // when
        final List<Quiz> result = quizService.getRandomQuizListUnderLimit(unsolvedQuizList, quizSize);

        // then
        assertThat(result.size()).isEqualTo(unsolvedQuizList.size());
    }

    @Test
    public void getRandomQuizListUnderLimitSuccess_OverLimit() {
        // given
        final List<Quiz> unsolvedQuizList = new ArrayList<>();
        unsolvedQuizList.add(quiz(1L));
        unsolvedQuizList.add(quiz(2L));
        unsolvedQuizList.add(quiz(3L));
        unsolvedQuizList.add(quiz(4L));

        // when
        final List<Quiz> result = quizService.getRandomQuizListUnderLimit(unsolvedQuizList, quizSize);

        // then
        assertThat(result.size()).isEqualTo(quizSize);
    }

    private List<SolvedQuiz> solvedQuizList() {
        return Arrays.asList(
                solvedQuiz(1L),
                solvedQuiz(2L),
                solvedQuiz(3L)
        );
    }

    private SolvedQuiz solvedQuiz(final long id) {
        final Quiz quiz = quiz(id);

        return SolvedQuiz.builder()
                .quiz(quiz)
                .build();
    }

    private Quiz quiz(final long id) {
        final Quiz quiz = Quiz.builder().build();
        ReflectionTestUtils.setField(quiz, "id", id);
        return quiz;
    }

}