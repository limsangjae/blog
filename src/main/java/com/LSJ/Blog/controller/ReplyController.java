package com.LSJ.Blog.controller;

import com.LSJ.Blog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
}
