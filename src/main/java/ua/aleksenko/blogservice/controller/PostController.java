package ua.aleksenko.blogservice.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.aleksenko.blogservice.model.entity.Post;
import ua.aleksenko.blogservice.service.PostService;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostController {

  private final PostService postService;

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Post createPost(@RequestBody Post post) {
    return postService.savePost(post);
  }

  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  public List<Post> getAllPosts() {
    return postService.getAllPost();
  }

  @GetMapping("/view-count/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Post getPostByIdViewCount(@PathVariable Long id) {
    return postService.getPostByIdViewCount(id);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Post getPostById(@PathVariable Long id) {
    return postService.getPostById(id);
  }

  @PutMapping("/like/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void createPost(@PathVariable Long id) {
     postService.likePost(id);
  }
}
