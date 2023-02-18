package com.mangkyu.employment.interview.app.category.service;

import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.enums.common.EnumMapperKey;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.enums.factory.EnumMapperFactory;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GetQuizCategoryServiceTest {

    @InjectMocks
    private GetQuizCategoryService quizService;

    @Mock
    private QuizRepository quizRepository;
    @Mock
    private EnumMapperFactory enumMapperFactory;

    @Test
     void getCategoryList() {
        // given
        final EnumMapperValue enumMapperValue1 = EnumMapperValue.builder()
                .expose(true)
                .build();

        final EnumMapperValue enumMapperValue2 = EnumMapperValue.builder()
                .expose(false)
                .build();

        doReturn(List.of(enumMapperValue1, enumMapperValue2))
                .when(enumMapperFactory)
                .get(EnumMapperKey.QUIZ_CATEGORY);

        // when
        final List<EnumMapperValue> result = quizService.getQuizCategoryList();

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void getCount() {
        // given
        final long count = 15;
        final QuizCategory category = QuizCategory.JAVA;

        doReturn(count)
                .when(quizRepository)
                .countByQuizCategoryAndIsEnableTrue(category);

        // when
        long result = quizService.getCount(category);

        // then
        assertThat(result).isEqualTo(count);
    }
}