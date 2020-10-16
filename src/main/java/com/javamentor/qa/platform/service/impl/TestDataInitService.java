package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.entity.Badge;
import com.javamentor.qa.platform.models.entity.Comment;
import com.javamentor.qa.platform.models.entity.CommentType;
import com.javamentor.qa.platform.models.entity.question.*;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.AnswerVote;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.*;
import com.javamentor.qa.platform.service.impl.model.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

    @Transactional
    public void createEntity() {

        User user1 = new User();
        user1.setEmail("ivanov@mail.com");
        user1.setPassword("password");
        user1.setFullName("Ivanov Ivan");
        user1.setIsEnabled(true);
        user1.setReputationCount(0);
        user1.setCity("Moscow");
        user1.setLinkSite("http://google.com");
        user1.setLinkGitHub("http://github.com");
        user1.setLinkVk("http://vk.com");
        user1.setAbout("very good man");
        user1.setImageLink("https://www.google.com/search?q=%D0%");
        user1.setRole(Role.builder().name("USER").build());
        user1.setReputationCount(1);
        userService.persist(user1);

        Badge badge1 = new Badge();
        badge1.setBadgeName("Super Badge");
        badge1.setReputationForMerit(1);
        badge1.setDescription("Badge Description");
        badgeService.persist(badge1);

        Question question1 = new Question();
        question1.setTitle("Question Title");
        question1.setViewCount(0);
        question1.setDescription("Question Description");
        question1.setUser(user1);
        question1.setTags(new List<Tag>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Tag> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Tag tag) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Tag> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Tag> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Tag get(int index) {
                return null;
            }

            @Override
            public Tag set(int index, Tag element) {
                return null;
            }

            @Override
            public void add(int index, Tag element) {

            }

            @Override
            public Tag remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Tag> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Tag> listIterator(int index) {
                return null;
            }

            @Override
            public List<Tag> subList(int fromIndex, int toIndex) {
                return null;
            }
        });
        question1.setIsDeleted(false);
        questionService.persist(question1);

        Answer answer1 = new Answer();
        answer1.setUser(user1);
        answer1.setQuestion(question1);
        answer1.setHtmlBody("<HtmlBody>");
        answer1.setIsHelpful(true);
        answer1.setIsDeleted(false);
        answerService.persist(answer1);

        Reputation reputation1 = new Reputation();
        reputation1.setUser(user1);
        reputation1.setCount(1);
        reputationService.persist(reputation1);


        UserBadges userBadges1 = new UserBadges();
        userBadges1.setReady(true);
        userBadges1.setUser(user1);
        userBadges1.setBadge(badge1);
        userBadgesService.persist(userBadges1);

        Tag tag1 = new Tag();
        tag1.setName("Tag Name");
        tag1.setDescription("Tag Description ");
        tagService.persist(tag1);

        UserFavoriteQuestion userFavoriteQuestion1 = new UserFavoriteQuestion();
        userFavoriteQuestion1.setUser(user1);
        userFavoriteQuestion1.setQuestion(question1);
        userFavoriteQuestionService.persist(userFavoriteQuestion1);

        CommentQuestion commentQuestion1 = new CommentQuestion();
        commentQuestion1.setQuestion(question1);
        commentQuestion1.setComment(Comment.builder().text("Comment Text")
                .user(user1).commentType(CommentType.QUESTION).build());
        commentQuestionService.persist(commentQuestion1);

        CommentAnswer commentAnswer1 = new CommentAnswer();
        commentAnswer1.setAnswer(answer1);
        commentAnswer1.setComment(Comment.builder().text("Comment Text")
                .user(user1).commentType(CommentType.ANSWER).build());
        commentAnswerService.persist(commentAnswer1);

        RelatedTag relatedTag1 = new RelatedTag();
        relatedTag1.setChildTag(tag1);
        relatedTag1.setMainTag(tag1);
        relatedTagService.persist(relatedTag1);

        AnswerVote answerVote1 = new AnswerVote();
        answerVote1.setUser(user1);
        answerVote1.setAnswer(answer1);
        answerVote1.setVote(1);
        answerVoteService.persist(answerVote1);

        VoteQuestion voteQuestion1 = new VoteQuestion();
        voteQuestion1.setUser(user1);
        voteQuestion1.setQuestion(question1);
        voteQuestion1.setVote(1);
        voteQuestionService.persist(voteQuestion1);
    }

}
