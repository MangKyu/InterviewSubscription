package com.mangkyu.employment.interview.app.quiz.service;

import com.mangkyu.employment.interview.app.quiz.dto.*;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @Mock
    private EnumMapperFactory enumMapperFactory;
    @Spy
    private ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

    private final Long userId = -1L;
    private final QuizLevel quizLevel = QuizLevel.NEW;
    private final int quizSize = 3;

    @Test
    public void searchQuizList() {
        // given
        final int page = 0;
        final int size = 20;

        final List<Quiz> quizList = quizList();
        final SearchQuizListRequest request = SearchQuizListRequest.builder()
                .query("query")
                .categories(new HashSet<>(Arrays.asList(QuizCategory.CULTURE, QuizCategory.JAVA)))
                .levels(new HashSet<>(Arrays.asList(QuizLevel.NEW, QuizLevel.SENIOR)))
                .page(0)
                .size(20)
                .build();
        final Pageable pageable = PageRequest.of(page, size);

        final PageImpl<Quiz> quizPage = new PageImpl<>(quizList(), pageable, quizList.size());
        doReturn(quizPage).when(quizRepository).search(any(QuizSearchCondition.class), any(PageRequest.class));

        // when
        final GetQuizResponseHolder result = quizService.searchQuizList(request);

        // then
        assertThat(result.getQuizList().size()).isEqualTo(quizList.size());
    }

    @Test
    public void findQuizEntityFail_NotExists() {
        // given
        final Quiz quiz = quiz(-1L);

        doReturn(Optional.empty()).when(quizRepository).findByResourceId(quiz.getResourceId());

        // when
        final RestApiException result = assertThrows(RestApiException.class, () -> quizService.findQuiz(quiz.getResourceId()));

        // then
        assertThat(result.getErrorCode()).isEqualTo(CommonErrorCode.RESOURCE_NOT_FOUND);
    }

    @Test
    public void findQuizEntitySuccess() throws RestApiException {
        // given
        final long id = -1L;
        final Quiz quiz = quiz(id);

        doReturn(Optional.of(quiz)).when(quizRepository).findByResourceId(quiz.getResourceId());

        // when
        final Quiz result = quizService.findQuiz(quiz.getResourceId());

        // then
        assertThat(result.getResourceId()).isEqualTo(quiz.getResourceId());
        assertThat(result.getTitle()).isEqualTo(quiz.getTitle());
        assertThat(result.getCreatedAt()).isEqualTo(quiz.getCreatedAt());
    }

    @Test
    public void getQuizFail_NotExists() {
        // given
        final Quiz quiz = quiz(-1L);

        doReturn(Optional.empty()).when(quizRepository).findByResourceId(quiz.getResourceId());

        // when
        final RestApiException result = assertThrows(RestApiException.class, () -> quizService.getQuiz(quiz.getResourceId()));

        // then
        assertThat(result.getErrorCode()).isEqualTo(CommonErrorCode.RESOURCE_NOT_FOUND);
    }

    @Test
    public void getQuizSuccess() throws RestApiException {
        // given
        final long id = -1L;
        final Quiz quiz = quiz(id);

        doReturn(Optional.of(quiz)).when(quizRepository).findByResourceId(quiz.getResourceId());

        // when
        final GetQuizResponse result = quizService.getQuiz(quiz.getResourceId());

        // then
        assertThat(result.getResourceId()).isEqualTo(quiz.getResourceId());
        assertThat(result.getTitle()).isEqualTo(quiz.getTitle());
        assertThat(result.getCategory()).isEqualTo(quiz.getQuizCategory().getTitle());
        assertThat(result.getQuizLevelList().size()).isEqualTo(quiz.getQuizLevel().size());
        assertThat(result.getCreatedAt()).isEqualTo(Timestamp.valueOf(quiz.getCreatedAt()).getTime());
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
        final List<Quiz> quizList = quizList();
        final Pageable pageable = PageRequest.of(page, size);

        final PageImpl<Quiz> quizPage = new PageImpl<>(quizList(), pageable, quizList.size());
        doReturn(quizPage).when(quizRepository).findByQuizCategoryIsAndIsEnableTrue(any(QuizCategory.class), any(PageRequest.class));

        // when
        final GetQuizResponseHolder result = quizService.getQuizList(request);

        // then
        assertThat(result.isHasNext()).isFalse();
        assertThat(result.getPage()).isEqualTo(page);
        assertThat(result.getSize()).isEqualTo(size);
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

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);

        doReturn(solvedQuizList).when(solvedQuizRepository).findAllByUser_Id(userId);
        doReturn(unsolvedQuizList).when(quizRepository).customFindByIdNotInAndQuizCategoryInAndQuizLevel(solvedQuizIdList, quizCategorySet, quizLevel);

        // when
        final List<Quiz> result = quizService.getUnsolvedQuizList(userId, quizLevel, quizCategorySet);

        // then
        assertThat(result.size()).isEqualTo(unsolvedQuizList.size());
    }

    @Test
    public void getUnsolvedQuizSuccess_SolvedQuizNotEmpty() {
        // given
        final List<SolvedQuiz> solvedQuizList = Collections.emptyList();
        final List<Quiz> unsolvedQuizList = Collections.singletonList(quiz(4L));

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);

        doReturn(solvedQuizList).when(solvedQuizRepository).findAllByUser_Id(userId);
        doReturn(unsolvedQuizList).when(quizRepository).customFindByIdNotInAndQuizCategoryInAndQuizLevel(Collections.emptySet(), quizCategorySet, quizLevel);

        // when
        final List<Quiz> result = quizService.getUnsolvedQuizList(userId, quizLevel, quizCategorySet);

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
        final List<Quiz> unsolvedQuizList = quizList();

        // when
        final List<Quiz> result = quizService.getRandomQuizListUnderLimit(unsolvedQuizList, quizSize);

        // then
        assertThat(result.size()).isEqualTo(quizSize);
    }

    @Test
    public void getQuizCategoryResponseList() {
        // given
        final long count = 15;
        final QuizCategory quizCategory = QuizCategory.JAVA;

        final EnumMapperValue enumMapperValue = EnumMapperValue.builder()
                .code(quizCategory.name())
                .title(quizCategory.getTitle())
                .desc(quizCategory.getDesc())
                .expose(true)
                .build();

        doReturn(Collections.singletonList(enumMapperValue)).when(enumMapperFactory).get(EnumMapperKey.QUIZ_CATEGORY);
        doReturn(count).when(quizRepository).countByQuizCategoryAndIsEnableTrue(quizCategory);

        // when
        final List<QuizCategoryResponse> result = quizService.getQuizCategoryList();

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getCount()).isEqualTo(count);
    }

    private List<Quiz> quizList() {
        final List<Quiz> unsolvedQuizList = new ArrayList<>();

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
        final Quiz quiz = quiz(id);

        return SolvedQuiz.builder()
                .quiz(quiz)
                .build();
    }

    private Quiz quiz(final long id) {
        final Quiz quiz = Quiz.builder()
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