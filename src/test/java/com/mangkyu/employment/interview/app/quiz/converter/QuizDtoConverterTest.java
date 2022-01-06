package com.mangkyu.employment.interview.app.quiz.converter;

import com.mangkyu.employment.interview.app.answer.dto.AddAnswerRequest;
import com.mangkyu.employment.interview.app.answer.dto.GetAnswerResponse;
import com.mangkyu.employment.interview.app.answer.entity.Answer;
import com.mangkyu.employment.interview.app.quiz.dto.GetQuizResponse;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.enums.common.EnumMapperType;
import com.mangkyu.employment.interview.enums.common.EnumMapperValue;
import com.mangkyu.employment.interview.testutils.EntityCreationUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class QuizDtoConverterTest {

    @Test
    public void convertAddAnswerRequestToAnswer() {
        // given
        final AddAnswerRequest addAnswerRequest = AddAnswerRequest.builder()
                .quizResourceId(UUID.randomUUID().toString())
                .description("desc")
                .build();
        final Quiz quiz = EntityCreationUtils.quiz();

        // when
        final Answer result = QuizDtoConverter.convert(addAnswerRequest, quiz);

        // then
        assertThat(result.getQuiz()).isEqualTo(quiz);
        assertThat(result.getDescription()).isEqualTo(addAnswerRequest.getDescription());
    }

    @Test
    public void convertAnswerToGetAnswerResponse() {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();
        final Answer answer = EntityCreationUtils.answer(quiz);

        // when
        final GetAnswerResponse result = QuizDtoConverter.convert(answer);

        // then
        assertThat(result.getResourceId()).isEqualTo(answer.getResourceId());
        assertThat(result.getQuizResourceId()).isEqualTo(answer.getQuiz().getResourceId());
        assertThat(result.getCreatedAt()).isEqualTo(Timestamp.valueOf(answer.getCreatedAt()).getTime());
        assertThat(result.getDescription()).isEqualTo(answer.getDescription());
    }

    @Test
    public void convertQuizToGetQuizResponseWithCategory_AnswerExists() {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();
        final Answer answer = EntityCreationUtils.answer(quiz);
        quiz.setAnswer(answer);

        // when
        final GetQuizResponse result = QuizDtoConverter.convert(quiz, enumMapperValue(quiz.getQuizCategory()));

        // then
        assertThat(result.getResourceId()).isEqualTo(quiz.getResourceId());
        assertThat(result.getTitle()).isEqualTo(quiz.getTitle());
        assertThat(result.getQuizLevelList().size()).isEqualTo(quiz.getQuizLevel().size());
        assertThat(result.getCreatedAt()).isEqualTo(Timestamp.valueOf(quiz.getCreatedAt()).getTime());
        assertThat(result.getCategory().getCode()).isEqualTo(enumMapperValue(quiz.getQuizCategory()).getCode());
        assertThat(result.getAnswerResourceId()).isEqualTo(answer.getResourceId());
    }

    @Test
    public void convertQuizToGetQuizResponseWithCategory_NotAnswerExists() {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();

        // when
        final GetQuizResponse result = QuizDtoConverter.convert(quiz, enumMapperValue(quiz.getQuizCategory()));

        // then
        assertThat(result.getResourceId()).isEqualTo(quiz.getResourceId());
        assertThat(result.getTitle()).isEqualTo(quiz.getTitle());
        assertThat(result.getQuizLevelList().size()).isEqualTo(quiz.getQuizLevel().size());
        assertThat(result.getCreatedAt()).isEqualTo(Timestamp.valueOf(quiz.getCreatedAt()).getTime());
        assertThat(result.getCategory().getCode()).isEqualTo(enumMapperValue(quiz.getQuizCategory()).getCode());
        assertThat(result.getAnswerResourceId()).isEqualTo(StringUtils.EMPTY);
    }

    @Test
    public void convertQuizToGetQuizResponse_AnswerExists() {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();
        final Answer answer = EntityCreationUtils.answer(quiz);
        quiz.setAnswer(answer);

        // when
        final GetQuizResponse result = QuizDtoConverter.convert(quiz);

        // then
        assertThat(result.getResourceId()).isEqualTo(quiz.getResourceId());
        assertThat(result.getTitle()).isEqualTo(quiz.getTitle());
        assertThat(result.getQuizLevelList().size()).isEqualTo(quiz.getQuizLevel().size());
        assertThat(result.getCreatedAt()).isEqualTo(Timestamp.valueOf(quiz.getCreatedAt()).getTime());
        assertThat(result.getCategory()).isNull();
        assertThat(result.getAnswerResourceId()).isEqualTo(answer.getResourceId());
    }

    @Test
    public void convertQuizToGetQuizResponse_AnswerNotExists() {
        // given
        final Quiz quiz = EntityCreationUtils.quiz();

        // when
        final GetQuizResponse result = QuizDtoConverter.convert(quiz);

        // then
        assertThat(result.getResourceId()).isEqualTo(quiz.getResourceId());
        assertThat(result.getTitle()).isEqualTo(quiz.getTitle());
        assertThat(result.getQuizLevelList().size()).isEqualTo(quiz.getQuizLevel().size());
        assertThat(result.getCreatedAt()).isEqualTo(Timestamp.valueOf(quiz.getCreatedAt()).getTime());
        assertThat(result.getCategory()).isNull();
        assertThat(result.getAnswerResourceId()).isEqualTo(StringUtils.EMPTY);
    }

    private EnumMapperValue enumMapperValue(final EnumMapperType enumMapperType) {
        return EnumMapperValue.builder()
                .code(enumMapperType.name())
                .title(enumMapperType.getTitle())
                .desc(enumMapperType.getDesc())
                .build();
    }
}