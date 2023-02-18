package com.mangkyu.employment.interview.app.mail.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangkyu.employment.interview.app.member.entity.Member;
import com.mangkyu.employment.interview.app.quiz.dto.AddQuizRequestHolder;
import com.mangkyu.employment.interview.app.quiz.entity.Quiz;
import com.mangkyu.employment.interview.app.quiz.repository.QuizRepository;
import com.mangkyu.employment.interview.app.quiz.service.QuizService;
import com.mangkyu.employment.interview.app.solvedquiz.repository.SolvedQuizRepository;
import com.mangkyu.employment.interview.app.member.repository.MemberRepository;
import com.mangkyu.employment.interview.enums.value.QuizCategory;
import com.mangkyu.employment.interview.enums.value.QuizDay;
import com.mangkyu.employment.interview.enums.value.QuizLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final MemberRepository memberRepository;
    private final QuizService quizService;
    private final QuizRepository quizRepository;
    private final SolvedQuizRepository solvedQuizRepository;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    @PostConstruct
    public void init() {
        final Set<QuizDay> quizDaySet = new HashSet<>();
        quizDaySet.add(QuizDay.MONDAY);
        quizDaySet.add(QuizDay.WEDNESDAY);
        quizDaySet.add(QuizDay.SATURDAY);

        final Set<QuizCategory> quizCategorySet = new HashSet<>();
        quizCategorySet.add(QuizCategory.JAVA);
        quizCategorySet.add(QuizCategory.SPRING);
        quizCategorySet.add(QuizCategory.SERVER);
        quizCategorySet.add(QuizCategory.NETWORK);
        quizCategorySet.add(QuizCategory.OPERATING_SYSTEM);
        quizCategorySet.add(QuizCategory.DATABASE);
        quizCategorySet.add(QuizCategory.PROGRAMMING);
        quizCategorySet.add(QuizCategory.DATA_STRUCTURE);
        quizCategorySet.add(QuizCategory.ALGORITHM);
        quizCategorySet.add(QuizCategory.PROBLEM_SOLVING);
        quizCategorySet.add(QuizCategory.CULTURE);
        quizCategorySet.add(QuizCategory.EXPERIENCE);
        quizCategorySet.add(QuizCategory.PERSONALITY);

        final Member member1 = Member.builder()
                .resourceId(UUID.randomUUID().toString())
                .email("whalsrb1226@naver.com")
                .quizLevel(QuizLevel.JUNIOR)
                .quizDaySet(quizDaySet)
//                .quizCategorySet(quizCategorySet)
                .solvedQuizList(Collections.emptyList())
                .build();

        final Member member2 = Member.builder()
                .resourceId(UUID.randomUUID().toString())
                .email("whalsrb1226@naver.com")
                .quizLevel(QuizLevel.JUNIOR)
                .solvedQuizList(Collections.emptyList())
                .build();
        memberRepository.save(member1);
//        userRepository.save(user2);

        System.out.println("http://localhost:8080/quizzes/editView/" + quizRepository.save(quiz(QuizCategory.JAVA, Collections.singletonList(QuizLevel.JUNIOR), "Junit4 vs Junit5 차이는 무엇인가?")).getResourceId());
        System.out.println("http://localhost:8080/quizzes/editView/" + quizRepository.save(quiz(QuizCategory.JAVA, Collections.singletonList(QuizLevel.JUNIOR), "Junit4 vs Junit5 차이는 무엇인가?")).getResourceId());
        System.out.println("http://localhost:8080/quizzes/editView/" + quizRepository.save(quiz(QuizCategory.JAVA, Collections.singletonList(QuizLevel.JUNIOR), "Junit4 vs Junit5 차이는 무엇인가?")).getResourceId());
        System.out.println("http://localhost:8080/quizzes/editView/" + quizRepository.save(quiz(QuizCategory.JAVA, Collections.singletonList(QuizLevel.JUNIOR), "Junit4 vs Junit5 차이는 무엇인가?")).getResourceId());
        System.out.println("http://localhost:8080/quizzes/editView/" + quizRepository.save(quiz(QuizCategory.JAVA, Collections.singletonList(QuizLevel.JUNIOR), "Junit4 vs Junit5 차이는 무엇인가?")).getResourceId());
        System.out.println("http://localhost:8080/quizzes/editView/" + quizRepository.save(quiz(QuizCategory.PROGRAMMING, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR), "DDD의 Layered Architecture에서 Presentation, Application, Domain, InfraStructure layer의 역할에 대해 설명해 주세요.")).getResourceId());
        System.out.println("http://localhost:8080/quizzes/editView/" + quizRepository.save(quiz(QuizCategory.SPRING, Collections.singletonList(QuizLevel.JUNIOR), "Spring Framework에서 사용되는 대표적인 디자인 패턴과 적용된 곳을 설명해주세요.")).getResourceId());
        System.out.println("http://localhost:8080/quizzes/editView/" + quizRepository.save(quiz(QuizCategory.DATABASE, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR), "MSA의 장점과 단점에 대해 설명해주세요")).getResourceId());
//        quizRepository.save(quiz(QuizCategory.JAVA, Collections.singletonList(QuizLevel.JUNIOR), "당신은 ㅜ구인가"));
//        quizRepository.save(quiz(QuizCategory.DATABASE, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR), "퇴근합시다"));
    }

    @Transactional
    @GetMapping("/temp")
    public ResponseEntity<AddQuizRequestHolder> sendMail1() throws IOException {
        final File file = ResourceUtils.getFile("classpath:quiz/quiz-211220.json");
        final ObjectMapper objectMapper = new ObjectMapper();
        final AddQuizRequestHolder addQuizRequestHolder = objectMapper.readValue(file, AddQuizRequestHolder.class);

        return ResponseEntity.ok(addQuizRequestHolder);
    }

    @GetMapping("/temp2")
    public ResponseEntity<Void> sendMail2() {
        mailService.sendMail("whalsrb1226@naver.com", quizList2(), true);
        return ResponseEntity.noContent().build();
    }

    private List<Quiz> quizList1() {
        return Arrays.asList(
                quiz(QuizCategory.JAVA, Collections.singletonList(QuizLevel.JUNIOR), "Junit4 vs Junit5 차이는 무엇인가?"),
                quiz(QuizCategory.DATABASE, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR), "Layered Architecture에서 Presentation, Application, Domain, InfraStructure layer의 역할에 대해 설명해 주세요."),
                quiz(QuizCategory.SPRING, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR, QuizLevel.NEW), "Spring에서 템플릿 메소드 패턴이 사용된 곳은(디스패처 서블릿)")
        );
    }

    private List<Quiz> quizList2() {
        return Arrays.asList(
                quiz(QuizCategory.DATABASE, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR), "Layered Architecture에서 Presentation, Application, Domain, InfraStructure layer의 역할에 대해 설명해 주세요."),
                quiz(QuizCategory.SPRING, Arrays.asList(QuizLevel.JUNIOR, QuizLevel.SENIOR, QuizLevel.NEW), "Spring에서 템플릿 메소드 패턴이 사용된 곳은(디스패처 서블릿)")
        );
    }

    private Quiz quiz(final QuizCategory quizCategory, final List<QuizLevel> quizLevelList, final String title) {
        return Quiz.builder()
                .resourceId(UUID.randomUUID().toString())
                .quizCategory(quizCategory)
                .quizLevel(quizLevelList)
                .title(title).build();
    }
}