package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.entity.Badge;
import com.javamentor.qa.platform.models.entity.Comment;
import com.javamentor.qa.platform.models.entity.CommentType;
import com.javamentor.qa.platform.models.entity.question.*;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.AnswerVote;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.*;
import com.javamentor.qa.platform.service.abstracts.model.*;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class TestDataInitService {

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
    final AnswerVoteService answerVoteService;
    final VoteQuestionService voteQuestionService;

    int numberOfUsers = 50;
    List<Tag> tagList = new ArrayList<>();
    Role USER_ROLE = Role.builder().name("USER").build();
    Role ADMIN_ROLE = Role.builder().name("ADMIN").build();

    public TestDataInitService(UserService userService, BadgeService badgeService, QuestionService questionService,
                               CommentService commentService, ReputationService reputationService, UserBadgesService userBadgesService,
                               TagService tagService, UserFavoriteQuestionService userFavoriteQuestionService,
                               RelatedTagService relatedTagService, CommentQuestionService commentQuestionService,
                               CommentAnswerService commentAnswerService, AnswerService answerService,
                               AnswerVoteService answerVoteService, VoteQuestionService voteQuestionService) {
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
        this.answerVoteService = answerVoteService;
        this.voteQuestionService = voteQuestionService;
    }


    public void createTagEntity() {
        for (int i = 0; i < numberOfUsers; i++) {
            Tag childTag = Tag.builder().name("Child").description("DescriptionChildTag").build();
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
        for (int i = 0; i < numberOfUsers; i++) {
            User user = new User();
            user.setEmail("ivanov@mail.com" + i);
            user.setPassword("password" + i);
            user.setFullName("Ivanov Ivan" + i);
            user.setIsEnabled(true);
            user.setReputationCount(0);
            user.setCity("Moscow" + i);
            user.setLinkSite("http://google.com" + i);
            user.setLinkGitHub("http://github.com");
            user.setLinkVk("http://vk.com");
            user.setAbout("very good man");
            user.setImageLink("https://www.google.com/search?q=%D0%");
            user.setReputationCount(1);
            if (i == 0) user.setRole(ADMIN_ROLE);
            else user.setRole(USER_ROLE);
            userService.persist(user);

            Reputation reputation = new Reputation();
            reputation.setUser(user);
            reputation.setCount(1);
            reputationService.persist(reputation);

            Question question = new Question();
            question.setTitle("Question Title" + i);
            question.setViewCount(0);
            question.setDescription("Question Description" + i);
            question.setUser(user);
            question.setTags(tagList);
            question.setIsDeleted(false);
            questionService.persist(question);

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
            answer.setHtmlBody("<HtmlBody>" + i);
            answer.setIsHelpful(true);
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

            AnswerVote answerVote = new AnswerVote();
            answerVote.setUser(user);
            answerVote.setAnswer(answer);
            answerVote.setVote(1);
            answerVoteService.persist(answerVote);
        }
    }
}
