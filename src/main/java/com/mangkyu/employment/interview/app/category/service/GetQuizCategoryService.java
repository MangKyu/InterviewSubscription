package com.mangkyu.employment.interview.app.category.service;

import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetQuizCategoryService {

    private final EnumMapperFactory enumMapperFactory;
    private final QuizRepository quizRepository;

    public List<EnumMapperValue> getQuizCategoryList() {
        return enumMapperFactory.get(EnumMapperKey.QUIZ_CATEGORY)
                .stream()
                .filter(EnumMapperValue::isExpose)
                .collect(Collectors.toList());
    }

    public long getCount(final QuizCategory category) {
        return quizRepository.countByQuizCategoryAndIsEnableTrue(category);
    }
}
