package com.LSJ.Blog.service;

import com.LSJ.Blog.dao.ReplyRepository;
import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.domain.Reply;
import com.LSJ.Blog.dto.reply.ReplySaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;

    public void writeReply(ReplySaveForm replySaveForm, Member findMember, Article findArticle) {

        Reply reply = Reply.createReply(replySaveForm);
        reply.setMember(findMember);
        reply.setArticle(findArticle);

        replyRepository.save(reply);
    }
}
