package com.javamentor.qa.platform.webapp.configs.initializer;

import com.javamentor.qa.platform.service.impl.TestDataInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingClass({"org.springframework.boot.test.context.SpringBootTest"})
public class TestEntityInit implements CommandLineRunner {

    private TestDataInitService testDataInitService;

    @Autowired
    public TestEntityInit(TestDataInitService testDataInitService) {
        this.testDataInitService = testDataInitService;
    }

    @Override
    public void run(String... args) {

        testDataInitService.createUserEntity();
//        testDataInitService.createBadgeEntity();
//        testDataInitService.createQuestionEntity();
//        testDataInitService.createAnswerEntity();
//        testDataInitService.createReputationEntity();
//        testDataInitService.createUserBadgesEntity();
//        testDataInitService.createTagEntity();
//        testDataInitService.createUserFavoriteQuestionEntity();
//        testDataInitService.createCommentQuestionEntity();
//        testDataInitService.createCommentAnswerEntity();
//        testDataInitService.createRelatedTagEntity();
//        testDataInitService.createAnswerVoteEntity();
//        testDataInitService.createVoteQuestionEntity();

    }
}
