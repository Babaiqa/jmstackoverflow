package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.entity.Badge;
import com.javamentor.qa.platform.models.entity.Comment;
import com.javamentor.qa.platform.models.entity.CommentType;
import com.javamentor.qa.platform.models.entity.question.*;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.*;
import com.javamentor.qa.platform.service.abstracts.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class  TestDataInitService {

    final UserService userService;
    final QuestionService questionService;
    final CommentService commentService;
    final ReputationService reputationService;
    final UserBadgesService userBadgesService;
    final TagService tagService;
    final UserFavoriteQuestionService userFavoriteQuestionService;
    final BadgeService badgeService;
    final RelatedTagService relatedTagService;
    final CommentQuestionService commentQuestionService;
    final CommentAnswerService commentAnswerService;
    final AnswerService answerService;
    final VoteAnswerService voteAnswerService;
    final VoteQuestionService voteQuestionService;
    final RoleService roleService;
    final IgnoredTagService ignoredTagService;
    final TrackedTagService trackedTagService;
    final Random random;

    int numberOfUsers = 50;
    List<Tag> tagList = new ArrayList<>();
    Role USER_ROLE = Role.builder().name("USER").build();
    Role ADMIN_ROLE = Role.builder().name("ADMIN").build();

    @Autowired
    public TestDataInitService(UserService userService, BadgeService badgeService, QuestionService questionService,
                               CommentService commentService, ReputationService reputationService, UserBadgesService userBadgesService,
                               TagService tagService, UserFavoriteQuestionService userFavoriteQuestionService,
                               RelatedTagService relatedTagService, CommentQuestionService commentQuestionService,
                               CommentAnswerService commentAnswerService, AnswerService answerService,
                               VoteAnswerService voteAnswerService, VoteQuestionService voteQuestionService, RoleService roleService,
                               IgnoredTagService ignoredTagService, TrackedTagService trackedTagService) {
        this.userService = userService;
        this.badgeService = badgeService;
        this.questionService = questionService;
        this.commentService = commentService;
        this.reputationService = reputationService;
        this.userBadgesService = userBadgesService;
        this.tagService = tagService;
        this.userFavoriteQuestionService = userFavoriteQuestionService;
        this.relatedTagService = relatedTagService;
        this.commentQuestionService = commentQuestionService;
        this.commentAnswerService = commentAnswerService;
        this.answerService = answerService;
        this.voteAnswerService = voteAnswerService;
        this.voteQuestionService = voteQuestionService;
        this.roleService = roleService;
        this.ignoredTagService = ignoredTagService;
        this.trackedTagService = trackedTagService;
        random = new Random();
    }


    public void createTagEntity() {
        for (int i = 0; i < numberOfUsers; i++) {
            Tag childTag = Tag.builder().name("Child" + i).description("DescriptionChildTag").build();
            Tag tag = new Tag();
            tag.setName("Tag Name" + i);
            tag.setDescription("Tag Description " + i);
            tagService.persist(tag);
            tagService.persist(childTag);

            RelatedTag relatedTag = new RelatedTag();
            relatedTag.setChildTag(childTag);
            relatedTag.setMainTag(tag);
            relatedTagService.persist(relatedTag);

            tagList.add(tag);
        }
    }

    @Transactional
    public void createEntity() {
        createTagEntity();
        roleService.persist(USER_ROLE);
        roleService.persist(ADMIN_ROLE);

        for (int i = 0; i < numberOfUsers; i++) {
            User user = new User();
            user.setEmail("ivanov@mail.com" + i);
            user.setPassword("password" + i);
            user.setFullName("Ivanov Ivan" + i);
            user.setIsEnabled(true);
            //user.setReputationCount(0);
            user.setCity("Moscow" + i);
            user.setLinkSite("http://google.com" + i);
            user.setLinkGitHub("http://github.com");
            user.setLinkVk("http://vk.com");
            user.setAbout("very good man");
            user.setImageLink("https://pbs.twimg.com/profile_images/1182694005408186375/i5xT6juJ_400x400.jpg");
            //user.setReputationCount(1);
            if (i == 0) user.setRole(ADMIN_ROLE);
            else user.setRole(USER_ROLE);
            userService.persist(user);

            Reputation reputation = new Reputation();
            reputation.setUser(user);
            reputation.setCount(random.nextInt(100) + 1);
            reputationService.persist(reputation);

            Question question = new Question();
            List<Tag> randomQuestionTagList = new ArrayList<>();

            int questionTagsNumber = random.nextInt(5) + 1;
            int j = 0;
            while (j != questionTagsNumber) {
                j++;
                int tagIndex = random.nextInt(49) + 1;
                randomQuestionTagList.add(tagList.get(tagIndex));
            }

            Integer viewCountQuestion = random.nextInt(1000) + 1;
            question.setTitle("Question Title" + i);
            question.setViewCount(viewCountQuestion);
            question.setDescription("Question Description" + i);
            question.setUser(user);
            question.setTags(randomQuestionTagList.stream().limit(5).collect(Collectors.toList()));
            question.setIsDeleted(false);
            questionService.persist(question);

            Question questionNoAnswer = new Question();
            List<Tag> randomQuestionNoAnsTagList = new ArrayList<>();

            int questionNoAnsTagsNumber = random.nextInt(5) + 1;
            int k = 0;
            while (k != questionNoAnsTagsNumber) {
                k++;
                int tagIndex = random.nextInt(49) + 1;
                randomQuestionNoAnsTagList.add(tagList.get(tagIndex));
            }

            Integer viewCountQuestionNoAnswer = random.nextInt(1000) + 1;
            questionNoAnswer.setTitle("Question NoAnswer " + i);
            questionNoAnswer.setViewCount(viewCountQuestionNoAnswer);
            questionNoAnswer.setDescription("Question NoAnswer Description" + i);
            questionNoAnswer.setUser(user);
            questionNoAnswer.setTags(randomQuestionNoAnsTagList.stream().limit(5).collect(Collectors.toList()));
            questionNoAnswer.setIsDeleted(false);
            questionService.persist(questionNoAnswer);

            UserFavoriteQuestion userFavoriteQuestion = new UserFavoriteQuestion();
            userFavoriteQuestion.setUser(user);
            userFavoriteQuestion.setQuestion(question);
            userFavoriteQuestionService.persist(userFavoriteQuestion);


            VoteQuestion voteQuestion = new VoteQuestion();
            voteQuestion.setUser(user);
            voteQuestion.setQuestion(question);
            voteQuestion.setVote(1);
            voteQuestionService.persist(voteQuestion);

            Answer answer = new Answer();
            answer.setUser(user);
            answer.setQuestion(question);
            answer.setHtmlBody("Answer" + i + ":  Hello! There you can find an answer on your question: www.google.com.");
            answer.setIsHelpful(false);
            answer.setIsDeleted(false);
            answerService.persist(answer);

            CommentQuestion commentQuestion = new CommentQuestion();
            commentQuestion.setQuestion(question);
            commentQuestion.setComment(Comment.builder().text("Comment Text" + i)
                    .user(user).commentType(CommentType.QUESTION).build());
            commentQuestionService.persist(commentQuestion);

            CommentAnswer commentAnswer = new CommentAnswer();
            commentAnswer.setAnswer(answer);
            commentAnswer.setComment(Comment.builder().text("Comment Text" + i)
                    .user(user).commentType(CommentType.ANSWER).build());
            commentAnswerService.persist(commentAnswer);

            Badge badge = new Badge();
            badge.setBadgeName("Super Badge" + i);
            badge.setReputationForMerit(1);
            badge.setDescription("Badge Description" + i);
            badgeService.persist(badge);

            UserBadges userBadges = new UserBadges();
            userBadges.setReady(true);
            userBadges.setUser(user);
            userBadges.setBadge(badge);
            userBadgesService.persist(userBadges);


            IgnoredTag ignoredTag = new IgnoredTag();
            int randomIgnoredTagNum = random.nextInt(7);
            ignoredTag.setUser(user);
            if (randomIgnoredTagNum > 0) {
                ignoredTag.setIgnoredTag(tagList.get(randomIgnoredTagNum));
                ignoredTagService.persist(ignoredTag);
            }

            TrackedTag trackedTag = new TrackedTag();
            int randomTrackedTagNum = (int) (Math.random() * 7); //TODO: double check randoms
            trackedTag.setUser(user);
            if (randomTrackedTagNum > 0) {
                trackedTag.setTrackedTag(tagList.get(randomTrackedTagNum));
                trackedTagService.persist(trackedTag);
            }
        }
    }
}
