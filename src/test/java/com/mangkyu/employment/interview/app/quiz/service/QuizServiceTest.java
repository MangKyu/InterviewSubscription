package com.mangkyu.employment.interview.app.quiz.service;

import com.mangkyu.employment.interview.app.quiz.constants.QuizConstants;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequest;
import com.mangkyu.employment.interview.app.quiz.entity.QuizEntity;
import com.mangkyu.employment.interview.app.quiz.enums.QuizCategory;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuizEntity;
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
        verify(quizRepository, times(1)).save(any(QuizEntity.class));
    }

    @Test
    public void getUnsolvedQuizSuccess_SolvedQuizEmpty() {
        // given
        final List<SolvedQuizEntity> solvedQuizEntityList = solvedQuizEntityList();
        final Set<Long> solvedQuizIdList = solvedQuizEntityList.stream()
                .map(v -> v.getQuiz().getId())
                .collect(Collectors.toSet());
        final List<QuizEntity> unsolvedQuizEntityList = Collections.singletonList(quizEntity(4L));

        doReturn(solvedQuizEntityList).when(solvedQuizRepository).findAllByUser_Id(userId);
        doReturn(unsolvedQuizEntityList).when(quizRepository).findByIdNotInAndQuizLevel(solvedQuizIdList, quizLevel);

        // when
        final List<QuizEntity> result = quizService.getUnsolvedQuizList(userId, quizLevel);

        // then
        assertThat(result.size()).isEqualTo(unsolvedQuizEntityList.size());
    }

    @Test
    public void getUnsolvedQuizSuccess_SolvedQuizNotEmpty() {
        // given
        final List<SolvedQuizEntity> solvedQuizEntityList = Collections.emptyList();
        final List<QuizEntity> unsolvedQuizEntityList = Collections.singletonList(quizEntity(4L));

        doReturn(solvedQuizEntityList).when(solvedQuizRepository).findAllByUser_Id(userId);
        doReturn(unsolvedQuizEntityList).when(quizRepository).findByQuizLevel(quizLevel);

        // when
        final List<QuizEntity> result = quizService.getUnsolvedQuizList(userId, quizLevel);

        // then
        assertThat(result.size()).isEqualTo(unsolvedQuizEntityList.size());
    }

    @Test
    public void getRandomQuizListUnderLimitSuccess_Under3() {
        // given
        final List<QuizEntity> unsolvedQuizEntityList = Collections.singletonList(quizEntity(4L));

        // when
        final List<QuizEntity> result = quizService.getRandomQuizListUnderLimit(unsolvedQuizEntityList);

        // then
        assertThat(result.size()).isEqualTo(unsolvedQuizEntityList.size());
    }

    @Test
    public void getRandomQuizListUnderLimitSuccess_Over3() {
        // given
        final List<QuizEntity> unsolvedQuizEntityList = new ArrayList<>();
        unsolvedQuizEntityList.add(quizEntity(1L));
        unsolvedQuizEntityList.add(quizEntity(2L));
        unsolvedQuizEntityList.add(quizEntity(3L));
        unsolvedQuizEntityList.add(quizEntity(4L));

        // when
        final List<QuizEntity> result = quizService.getRandomQuizListUnderLimit(unsolvedQuizEntityList);

        // then
        assertThat(result.size()).isEqualTo(QuizConstants.MAXIMUM_QUIZ_SIZE);
    }

    private List<SolvedQuizEntity> solvedQuizEntityList() {
        return Arrays.asList(
                solvedQuizEntity(1L),
                solvedQuizEntity(2L),
                solvedQuizEntity(3L)
        );
    }

    private SolvedQuizEntity solvedQuizEntity(final long id) {
        final QuizEntity quizEntity = quizEntity(id);

        return SolvedQuizEntity.builder()
                .quiz(quizEntity)
                .build();
    }

    private QuizEntity quizEntity(final long id) {
        final QuizEntity quizEntity = QuizEntity.builder().build();
        ReflectionTestUtils.setField(quizEntity, "id", id);
        return quizEntity;
    }

}