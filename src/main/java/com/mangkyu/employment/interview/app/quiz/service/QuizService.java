package com.mangkyu.employment.interview.app.quiz.service;

import com.mangkyu.employment.interview.app.quiz.constants.QuizConstants;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequest;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.app.solvedquiz.repository.SolvedQuizRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;
    private final QuizRepository quizRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    @Transactional
    public void addQuiz(final AddQuizRequest addQuizRequest) {
        final Quiz quiz = modelMapper.map(addQuizRequest, Quiz.class);
        quizRepository.save(quiz);
    }

    public List<Quiz> getUnsolvedQuizList(final Long userId, final QuizLevel quizLevel) {
        final Set<Long> solvedQuizIdList = solvedQuizRepository.findAllByUser_Id(userId)
                .stream()
                .map(v -> v.getQuiz().getId())
                .collect(Collectors.toSet());

        return solvedQuizIdList.isEmpty()
                ? quizRepository.findByQuizLevel(quizLevel)
                : quizRepository.findByIdNotInAndQuizLevel(solvedQuizIdList, quizLevel);
    }

    public List<Quiz> getRandomQuizListUnderLimit(final List<Quiz> quizList) {
        return quizList.size() <= QuizConstants.MAXIMUM_QUIZ_SIZE
                ? quizList
                : createRandomQuizListUnderLimit(quizList);
    }

    private List<Quiz> createRandomQuizListUnderLimit(final List<Quiz> quizList) {
        final Random rand = new Random();
        final List<Quiz> randomQuizList = new ArrayList<>();

        for (int i = 0; i < QuizConstants.MAXIMUM_QUIZ_SIZE; i++) {
            final int randomIndex = rand.nextInt(quizList.size());
            final Quiz quiz = quizList.get(randomIndex);
            randomQuizList.add(quiz);
        }

        return randomQuizList;
    }

}
