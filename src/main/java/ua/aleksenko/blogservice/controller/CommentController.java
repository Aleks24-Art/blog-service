package ua.aleksenko.blogservice.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.aleksenko.blogservice.model.entity.Comment;
import ua.aleksenko.blogservice.service.CommentService;
import ua.aleksenko.blogservice.util.UserUtils;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Comment createComment(@RequestParam Long postId, @RequestBody String content) {
    return commentService.createComment(postId, UserUtils.getUserName(), content);
  }

  @GetMapping("/comments/{postId}")
  @ResponseStatus(HttpStatus.CREATED)
  public List<Comment> getCommentsForPost(@PathVariable Long postId) {
    return commentService.getPostComments(postId);
  }
}
