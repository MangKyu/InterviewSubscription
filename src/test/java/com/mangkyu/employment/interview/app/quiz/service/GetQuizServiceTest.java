package com.mangkyu.employment.interview.app.quiz.service;

import com.mangkyu.employment.interview.app.quiz.controller.*;
import com.mangkyu.employment.interview.app.quiz.entity.PagingQuizzes;
import com.mangkyu.employment.interview.app.quiz.entity.Quizzes;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import com.mangkyu.employment.interview.app.solvedquiz.repository.SolvedQuizRepository;
import com.mangkyu.employment.interview.config.modelmapper.ModelMapperConfig;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import com.mangkyu.employment.interview.erros.errorcode.CommonErrorCode;
import com.mangkyu.employment.interview.erros.exception.RestApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetQuizServiceTest {

    @InjectMocks
    private GetQuizService quizService;

    @Mock
    private QuizRepository quizRepository;
    @Mock
    private SolvedQuizRepository solvedQuizRepository;
    @Mock
    private EnumMapperFactory enumMapperFactory;
    @Spy
    private ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

    private final Long userId = -1L;
    private final QuizLevel quizLevel = QuizLevel.NEW;

    @Test
    public void searchQuizList() {
        // given
        final int page = 0;
        final int size = 20;

        final List<com.mangkyu.employment.interview.app.quiz.entity.Quiz> quizList = quizList();
        final SearchQuizListRequest request = SearchQuizListRequest.builder()
                .query("query")
                .categories(new HashSet<>(Arrays.asList(QuizCategory.CULTURE, QuizCategory.JAVA)))
                .levels(new HashSet<>(Arrays.asList(QuizLevel.NEW, QuizLevel.SENIOR)))
                .page(0)
                .size(20)
                .build();

        final PageImpl<com.mangkyu.employment.interview.app.quiz.entity.Quiz> quizPage = new PageImpl<>(quizList(), PageRequest.of(page, size), quizList.size());
        doReturn(quizPage)
                .when(quizRepository)
                .search(any(QuizSearchCondition.class), any(PageRequest.class));

        // when
        final GetQuizResponseHolder result = quizService.searchQuizList(request);

        // then
        assertThat(result.getQuizList().size()).isEqualTo(quizList.size());
    }

    @Test
    public void findQuizEntityFail_NotExists() {
        // given
        final com.mangkyu.employment.interview.app.quiz.entity.Quiz quiz = quiz(-1L);

        doReturn(Optional.empty())
                .when(quizRepository)
                .findByResourceId(quiz.getResourceId());

        // when
        final RestApiException result = assertThrows(
                RestApiException.class,
                () -> quizService.getQuiz(quiz.getResourceId()));

        // then
        assertThat(result.getErrorCode()).isEqualTo(CommonErrorCode.RESOURCE_NOT_FOUND);
    }

    @Test
    public void findQuizEntitySuccess() {
        // given
        final com.mangkyu.employment.interview.app.quiz.entity.Quiz quiz = quiz(-1L);

        doReturn(Optional.of(quiz))
                .when(quizRepository)
                .findByResourceId(quiz.getResourceId());

        // when
        final com.mangkyu.employment.interview.app.quiz.entity.Quiz result = quizService.getQuiz(quiz.getResourceId());

        // then
        assertThat(result.getResourceId()).isEqualTo(quiz.getResourceId());
        assertThat(result.getTitle()).isEqualTo(quiz.getTitle());
        assertThat(result.getCreatedAt()).isEqualTo(quiz.getCreatedAt());
    }

    @Test
    public void getCursorQuizList_Success() {
        // given
        final int page = 0;
        final int size = 20;

        final GetQuizRequest request = GetQuizRequest.builder()
                .category(QuizCategory.JAVA)
                .page(page)
                .size(size)
                .build();
        final List<com.mangkyu.employment.interview.app.quiz.entity.Quiz> quizList = quizList();
        final Pageable pageable = PageRequest.of(page, size);

        final PageImpl<com.mangkyu.employment.interview.app.quiz.entity.Quiz> quizPage = new PageImpl<>(quizList(), pageable, quizList.size());
        doReturn(quizPage)
                .when(quizRepository)
                .findByQuizCategoryIsAndIsEnableTrue(any(QuizCategory.class), any(PageRequest.class));

        // when
        PagingQuizzes result = quizService.getQuizList(request);

        // then
        assertThat(result.isHasNext()).isFalse();
        assertThat(result.getPageNumber()).isEqualTo(page);
        assertThat(result.getPageSize()).isEqualTo(size);
    }

    @Test
    public void getUnsolvedQuizSuccess_SolvedQuizEmpty() {
        // given
        final List<SolvedQuiz> solvedQuizList = solvedQuizList();
        final Set<Long> solvedQuizIdList = solvedQuizList.stream()
                .map(v -> v.getQuiz().getId())
                .collect(Collectors.toSet());
        final List<com.mangkyu.employment.interview.app.quiz.entity.Quiz> unsolvedQuizList = Collections.singletonList(quiz(4L));

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);

        doReturn(solvedQuizList)
                .when(solvedQuizRepository)
                .findAllByMember_Id(userId);

        doReturn(unsolvedQuizList)
                .when(quizRepository)
                .customFindByIdNotInAndQuizCategoryInAndQuizLevel(solvedQuizIdList, quizCategorySet, quizLevel);

        // when
        final Quizzes quizzes = quizService.getUnsolvedQuizList(userId, quizLevel, quizCategorySet);

        // then
        assertThat(quizzes.getQuizList().size()).isEqualTo(unsolvedQuizList.size());
    }

    @Test
    public void getUnsolvedQuizSuccess_SolvedQuizNotEmpty() {
        // given
        final List<SolvedQuiz> solvedQuizList = Collections.emptyList();
        final List<com.mangkyu.employment.interview.app.quiz.entity.Quiz> unsolvedQuizList = Collections.singletonList(quiz(4L));

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);

        doReturn(solvedQuizList)
                .when(solvedQuizRepository)
                .findAllByMember_Id(userId);

        doReturn(unsolvedQuizList)
                .when(quizRepository)
                .customFindByIdNotInAndQuizCategoryInAndQuizLevel(Collections.emptySet(), quizCategorySet, quizLevel);

        // when
        final Quizzes result = quizService.getUnsolvedQuizList(userId, quizLevel, quizCategorySet);

        // then
        assertThat(result.getQuizList().size()).isEqualTo(unsolvedQuizList.size());
    }


    private List<com.mangkyu.employment.interview.app.quiz.entity.Quiz> quizList() {
        final List<com.mangkyu.employment.interview.app.quiz.entity.Quiz> unsolvedQuizList = new ArrayList<>();

        unsolvedQuizList.add(quiz(1L));
        unsolvedQuizList.add(quiz(2L));
        unsolvedQuizList.add(quiz(3L));
        unsolvedQuizList.add(quiz(4L));

        return unsolvedQuizList;
    }

    private List<SolvedQuiz> solvedQuizList() {
        return Arrays.asList(
                solvedQuiz(1L),
                solvedQuiz(2L),
                solvedQuiz(3L)
        );
    }

    private SolvedQuiz solvedQuiz(final long id) {
        final com.mangkyu.employment.interview.app.quiz.entity.Quiz quiz = quiz(id);

        return SolvedQuiz.builder()
                .quiz(quiz)
                .build();
    }

    private com.mangkyu.employment.interview.app.quiz.entity.Quiz quiz(final long id) {
        final com.mangkyu.employment.interview.app.quiz.entity.Quiz quiz = com.mangkyu.employment.interview.app.quiz.entity.Quiz.builder()
                .title("quiz")
                .quizLevel(Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .quizCategory(QuizCategory.JAVA)
                .quizLevel(Arrays.asList(QuizLevel.NEW, QuizLevel.JUNIOR, QuizLevel.SENIOR))
                .build();

        ReflectionTestUtils.setField(quiz, "id", id);
        ReflectionTestUtils.setField(quiz, "resourceId", UUID.randomUUID().toString());
        ReflectionTestUtils.setField(quiz, "createdAt", LocalDateTime.now());
        return quiz;
    }

}