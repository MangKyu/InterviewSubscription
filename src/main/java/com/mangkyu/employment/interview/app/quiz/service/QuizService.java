package com.mangkyu.employment.interview.app.quiz.service;

import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequest;
import com.mangkyu.employment.interview.app.quiz.entity.QuizEntity;
import com.mangkyu.employment.interview.app.quiz.enums.QuizLevel;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.app.solvedquiz.repository.SolvedQuizRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final ModelMapper modelMapper;
    private final QuizRepository quizRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    public void addQuiz(final AddQuizRequest addQuizRequest) {
        final QuizEntity quizEntity = modelMapper.map(addQuizRequest, QuizEntity.class);
        quizRepository.save(quizEntity);
    }

    public List<QuizEntity> getUnsolvedQuizList(final Long userId, final QuizLevel quizLevel) {
        final Set<Long> solvedQuizIdList = solvedQuizRepository.findAllByUser_Id(userId)
                .stream()
                .map(v -> v.getQuiz().getId())
                .collect(Collectors.toSet());

        return solvedQuizIdList.isEmpty()
                ? quizRepository.findByQuizLevel(quizLevel)
                : quizRepository.findByIdNotInAndQuizLevel(solvedQuizIdList, quizLevel);
    }

}
