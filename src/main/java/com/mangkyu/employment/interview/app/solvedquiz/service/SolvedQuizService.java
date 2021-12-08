package com.mangkyu.employment.interview.app.solvedquiz.service;

import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.solvedquiz.entity.SolvedQuiz;
import com.mangkyu.employment.interview.app.solvedquiz.repository.SolvedQuizRepository;
import com.mangkyu.employment.interview.app.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolvedQuizService {

    private final SolvedQuizRepository solvedQuizRepository;

    @Transactional
    public void addSolvedQuizList(final User user, final List<Quiz> quizList) {
        final List<SolvedQuiz> solvedQuizList = quizList.stream()
                .map(quiz -> createSolvedQuizEntity(user, quiz))
                .collect(Collectors.toList());

        solvedQuizRepository.saveAll(solvedQuizList);
    }

    private SolvedQuiz createSolvedQuizEntity(final User user, final Quiz quiz) {
        return SolvedQuiz.builder()
                .user(user)
                .quiz(quiz).build();
    }

}
