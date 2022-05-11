package com.LSJ.Blog.service;

import com.LSJ.Blog.dao.ReplyRepository;
import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.domain.Reply;
import com.LSJ.Blog.dto.reply.ReplyModifyForm;
import com.LSJ.Blog.dto.reply.ReplySaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public void writeReply(ReplySaveForm replySaveForm, Member findMember, Article findArticle) {

        Reply reply = Reply.createReply(replySaveForm);
        reply.setMember(findMember);
        reply.setArticle(findArticle);

        replyRepository.save(reply);
    }

    public Reply findById(Long id){
        Optional<Reply> findReply = replyRepository.findById(id);

        return findReply.orElseThrow(
                () -> {
                    throw new NoSuchElementException("해당 댓글은 존재하지 않습니다.");
                }
        );
    }

    @Transactional
    public void modifyReply(ReplyModifyForm replyModifyForm, Reply findReply) {
        findReply.modifyReply(replyModifyForm);
    }

    @Transactional
    public void deleteReply(Article findArticle, Member findMember, Reply findReply) {
    }
}
