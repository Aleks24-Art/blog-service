package ua.aleksenko.blogservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.aleksenko.blogservice.model.entity.Post;
import ua.aleksenko.blogservice.repository.PostRepository;
import ua.aleksenko.blogservice.util.UserUtils;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

  @Mock
  private UserUtils userUtils;

  @Mock
  private PostRepository postRepository;

  @InjectMocks
  private PostServiceImpl postService;


  @Test
  public void testSavePost() {
    // Given
    Post postToSave = new Post();
    postToSave.setName("Test Title");
    postToSave.setContent("Test Content");
    postToSave.setImg("Test Img URL");
    postToSave.setTags(List.of("Tag1", "Tag2"));
    when(postRepository.save(postToSave)).thenReturn(postToSave);
    when(userUtils.getUserName()).thenReturn("test@gmail.com");

    // When
    Post savedPost = postService.savePost(postToSave);

    // Then
    assertEquals(postToSave.getName(), savedPost.getName());
    assertEquals(postToSave.getContent(), savedPost.getContent());
    assertEquals(postToSave.getPostedBy(), savedPost.getPostedBy());
    assertEquals(postToSave.getImg(), savedPost.getImg());
    assertNotNull(savedPost.getDate());
  }

  @Test
  public void testGetAllPost() {
    // Given
    List<Post> posts = new ArrayList<>();
    posts.add(new Post());
    posts.add(new Post());
    when(postRepository.findAll()).thenReturn(posts);

    // When
    List<Post> result = postService.getAllPost();

    // Then
    assertEquals(2, result.size());
  }

  @Test
  public void testGetPostByIdViewCount() {
    // Given
    Long postId = 1L;
    Post post = new Post();
    post.setId(postId);
    post.setViewCount(0);
    when(postRepository.findById(postId)).thenReturn(Optional.of(post));
    when(postRepository.save(post)).thenReturn(post);

    // When
    Post result = postService.getPostByIdViewCount(postId);

    // Then
    assertEquals(1, result.getViewCount());
  }

  @Test
  public void testGetPostByIdNotFound() {
    // Given
    Long postId = 1L;
    when(postRepository.findById(postId)).thenReturn(Optional.empty());

    // When
    // Then
    assertThrows(EntityNotFoundException.class, () -> postService.getPostById(postId));
  }

  @Test
  public void testGetPostById() {
    // Given
    Long postId = 1L;
    Post post = new Post();
    post.setId(postId);
    when(postRepository.findById(postId)).thenReturn(Optional.of(post));

    // When
    Post result = postService.getPostById(postId);

    // Then
    assertEquals(post, result);
  }

  @Test
  public void testLikePost() {
    // Given
    Long postId = 1L;
    Post post = new Post();
    post.setId(postId);
    post.setLikeCount(0);
    when(postRepository.findById(postId)).thenReturn(Optional.of(post));
    when(postRepository.save(post)).thenReturn(post);

    // When
    postService.likePost(postId);

    // Then
    assertEquals(1, post.getLikeCount());
  }
}
