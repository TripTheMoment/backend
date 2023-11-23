package com.ssafy.moment.controller;

import com.ssafy.moment.domain.dto.request.ArticleForm;
import com.ssafy.moment.domain.dto.request.ReplyForm;
import com.ssafy.moment.domain.dto.response.ResponseDto;
import com.ssafy.moment.service.ArticleReplyService;
import com.ssafy.moment.service.ArticleService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleReplyService replyService;

    @GetMapping
    public ResponseDto<?> getList(@RequestParam String title, Pageable pageable) {
        try {
            return ResponseDto.success(articleService.getList(title, pageable));
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @GetMapping("/{articleId}")
    public ResponseDto<?> get(@PathVariable int articleId) {
        try {
            return ResponseDto.success(articleService.get(articleId));
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @PostMapping
    public ResponseDto<?> create(HttpServletRequest request, @RequestPart MultipartFile file, @RequestPart ArticleForm form) {
        try {
            articleService.create(request, file, form);
            return ResponseDto.success("UPLOAD SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @PutMapping("/{articleId}")
    public ResponseDto<?> update(HttpServletRequest request, @PathVariable int articleId, @RequestBody ArticleForm form) {
        try {
            return ResponseDto.success(articleService.update(request, articleId, form));
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @DeleteMapping("/{articleId}")
    public ResponseDto<?> delete(HttpServletRequest request, @PathVariable int articleId) {
        try {
            articleService.delete(request, articleId);
            return ResponseDto.success("ARTICLE DELETE SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @PostMapping("/{articleId}/replies")
    public ResponseDto<?> createReply(HttpServletRequest request, @RequestBody ReplyForm form, @PathVariable int articleId) {
        try {
            replyService.create(request, articleId, form);
            return ResponseDto.success("REPLY CREATE SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

    @DeleteMapping("/{articleId}/replies/{replyId}")
    public ResponseDto<?> deleteReply(HttpServletRequest request, @PathVariable int articleId, @PathVariable int replyId) {
        try {
            replyService.delete(request, replyId);
            return ResponseDto.success("REPLY DELETE SUCCESS");
        } catch (Exception e) {
            return ResponseDto.fail(e.getMessage());
        }
    }

}