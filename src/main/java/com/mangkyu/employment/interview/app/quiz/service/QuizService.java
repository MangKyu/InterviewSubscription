package com.mangkyu.employment.interview.app.quiz.service;

import com.mangkyu.employment.interview.app.common.erros.errorcode.CommonErrorCode;
import com.mangkyu.employment.interview.app.common.erros.exception.QuizException;
import com.mangkyu.employment.interview.app.quiz.converter.QuizDtoConverter;
import com.mangkyu.employment.interview.app.quiz.dto.*;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.app.solvedquiz.repository.SolvedQuizRepository;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

    private final QuizRepository quizRepository;
    private final SolvedQuizRepository solvedQuizRepository;
    private final ModelMapper modelMapper;
    private final EnumMapperFactory enumMapperFactory;

    @Transactional
    public void addQuiz(final AddQuizRequest addQuizRequest) {
        final Quiz quiz = modelMapper.map(addQuizRequest, Quiz.class);
        quizRepository.save(quiz);
    }

    public Quiz findQuiz(final String resourceId) throws QuizException {
        return quizRepository.findByResourceId(resourceId)
                .orElseThrow(() -> new QuizException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

    public GetQuizResponse getQuiz(final String resourceId) throws QuizException {
        final Quiz quiz = findQuiz(resourceId);
        return QuizDtoConverter.convert(quiz, enumMapperFactory.getElement(EnumMapperKey.QUIZ_CATEGORY, quiz.getQuizCategory()));
    }

    public GetQuizResponseHolder getQuizList(final GetQuizRequest getQuizRequest) {
        final PageRequest pageRequest = PageRequest.of(getQuizRequest.getPage(), getQuizRequest.getSize());
        final Page<Quiz> quizPage = quizRepository.findByQuizCategoryIs(getQuizRequest.getCategory(), pageRequest);

        final List<GetQuizResponse> quizResponseList = quizPage.getContent().stream()
                .map(QuizDtoConverter::convert)
                .collect(Collectors.toList());

        return GetQuizResponseHolder.builder()
                .quizList(quizResponseList)
                .category(enumMapperFactory.getElement(EnumMapperKey.QUIZ_CATEGORY, getQuizRequest.getCategory()))
                .hasNext(quizPage.hasNext())
                .page(quizPage.nextOrLastPageable().getPageNumber())
                .size(quizPage.nextOrLastPageable().getPageSize())
                .totalPages(quizPage.getTotalPages())
                .build();
    }

    public List<Quiz> getUnsolvedQuizList(final Long userId, final QuizLevel quizLevel, final Set<QuizCategory> quizCategorySet) {
        final Set<Long> solvedQuizIdList = solvedQuizRepository.findAllByUser_Id(userId)
                .stream()
                .map(v -> v.getQuiz().getId())
                .collect(Collectors.toSet());

        return quizRepository.customFindByIdNotInAndQuizCategoryInAndQuizLevel(solvedQuizIdList, quizCategorySet, quizLevel);
    }

    public List<Quiz> getRandomQuizListUnderLimit(final List<Quiz> quizList, final Integer quizSize) {
        return quizList.size() <= quizSize
                ? quizList
                : createRandomQuizListUnderLimit(quizList, quizSize);
    }

    private List<Quiz> createRandomQuizListUnderLimit(final List<Quiz> quizList, final Integer quizSize) {
        final Random rand = new Random();
        final List<Quiz> randomQuizList = new ArrayList<>();

        for (int i = 0; i < quizSize; i++) {
            final int randomIndex = rand.nextInt(quizList.size());
            final Quiz quiz = quizList.get(randomIndex);
            randomQuizList.add(quiz);
            quizList.remove(quiz);
        }

        return randomQuizList;
    }

    public List<QuizCategoryResponse> getQuizCategoryList() {
        final List<EnumMapperValue> enumMapperValueList = enumMapperFactory.get(EnumMapperKey.QUIZ_CATEGORY);
        return enumMapperValueList.stream()
                .map(this::convertToQuizCategoryResponse)
                .collect(Collectors.toList());
    }

    private QuizCategoryResponse convertToQuizCategoryResponse(final EnumMapperValue enumMapperValue) {
        return QuizCategoryResponse.builder()
                .count(quizRepository.countByQuizCategory(QuizCategory.valueOf(enumMapperValue.name())))
                .code(enumMapperValue.getCode())
                .title(enumMapperValue.getTitle())
                .desc(enumMapperValue.getDesc())
                .build();
    }

}
