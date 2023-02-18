package com.mangkyu.employment.interview.app.quiz.service;

import com.mangkyu.employment.interview.app.quiz.controller.GetQuizRequest;
import com.mangkyu.employment.interview.app.quiz.controller.SearchQuizListRequest;
import com.mangkyu.employment.interview.app.quiz.entity.PagingQuizzes;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.entity.QuizSearchCondition;
import com.mangkyu.employment.interview.app.quiz.entity.Quizzes;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.app.solvedquiz.repository.SolvedQuizRepository;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import com.mangkyu.employment.interview.erros.errorcode.CommonErrorCode;
import com.mangkyu.employment.interview.erros.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetQuizService {

    private final QuizRepository quizRepository;
    private final SolvedQuizRepository solvedQuizRepository;
    private final ModelMapper modelMapper;

    public Quiz getQuiz(final String resourceId) {
        return quizRepository.findByResourceId(resourceId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

    public Quizzes getUnsolvedQuizList(final Long userId, final QuizLevel quizLevel, final Set<QuizCategory> quizCategorySet) {
        final Set<Long> solvedQuizIdList = solvedQuizRepository.findAllByMember_Id(userId)
                .stream()
                .map(v -> v.getQuiz().getId())
                .collect(Collectors.toSet());

        return new Quizzes(quizRepository.customFindByIdNotInAndQuizCategoryInAndQuizLevel(solvedQuizIdList, quizCategorySet, quizLevel));
    }

    public PagingQuizzes getQuizList(final GetQuizRequest request) {
        final PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        final Page<Quiz> quizPage = quizRepository.findByQuizCategoryIsAndIsEnableTrue(request.getCategory(), pageRequest);

        return PagingQuizzes.builder()
                .quizzes(new Quizzes(quizPage.getContent()))
                .hasNext(quizPage.hasNext())
                .pageNumber(quizPage.nextOrLastPageable().getPageNumber())
                .pageSize(quizPage.nextOrLastPageable().getPageSize())
                .totalPages(quizPage.getTotalPages())
                .build();
    }

    public PagingQuizzes searchQuizList(final SearchQuizListRequest request) {
        final QuizSearchCondition condition = modelMapper.map(request, QuizSearchCondition.class);
        final PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());

        final Page<Quiz> quizPage = quizRepository.search(condition, pageRequest);

        return PagingQuizzes.builder()
                .quizzes(new Quizzes(quizPage.getContent()))
                .hasNext(quizPage.hasNext())
                .pageNumber(quizPage.nextOrLastPageable().getPageNumber())
                .pageSize(quizPage.nextOrLastPageable().getPageSize())
                .totalPages(quizPage.getTotalPages())
                .build();
    }

}
