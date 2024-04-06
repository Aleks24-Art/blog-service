package ua.aleksenko.blogservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.aleksenko.blogservice.model.entity.Post;
import ua.aleksenko.blogservice.service.PostService;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private PostService postService;

  @Test
  public void testCreatePost() throws Exception {
    Post postToSave = new Post();
    postToSave.setName("Test Title");
    postToSave.setContent("Test Content");
    postToSave.setImg("Test Img URL");
    postToSave.setTags(List.of("Tag1", "Tag2"));
    postToSave.setDate(new Date());
    postToSave.setPostedBy("Test User");

    when(postService.savePost(any(Post.class))).thenReturn(postToSave);

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/post/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postToSave))
            .accept(MediaType.APPLICATION_JSON))
        .andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(HttpStatus.CREATED.value(), status);

    String content = mvcResult.getResponse().getContentAsString();
    Post responsePost = objectMapper.readValue(content, Post.class);
    assertEquals(postToSave.getName(), responsePost.getName());
    assertEquals(postToSave.getContent(), responsePost.getContent());
    assertEquals(postToSave.getPostedBy(), responsePost.getPostedBy());
    assertEquals(postToSave.getImg(), responsePost.getImg());
    assertNotNull(responsePost.getDate());
  }

  @Test
  void testGetAllPosts() throws Exception {
    when(postService.getAllPost())
        .thenReturn(List.of(new Post(), new Post()));

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/all")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), status);

    String content = mvcResult.getResponse().getContentAsString();
    List<Post> responsePosts = objectMapper.readValue(content, new TypeReference<>() {
    });
    assertEquals(2, responsePosts.size());
  }

  @Test
  void testGetPostByIdViewCount() throws Exception {
    Long postId = 1L;
    Post post = new Post();
    post.setId(postId);
    post.setName("Test Title");
    post.setContent("Test Content");
    post.setImg("Test Img URL");
    post.setTags(List.of("Tag1", "Tag2"));
    post.setDate(new Date());
    post.setPostedBy("Test User");
    post.setViewCount(1);

    when(postService.getPostByIdViewCount(postId)).thenReturn(post);

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/view-count/{id}", postId)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), status);

    String content = mvcResult.getResponse().getContentAsString();
    Post responsePost = new ObjectMapper().readValue(content, Post.class);
    assertEquals(post, responsePost);
  }

  @Test
  void testGetPostById() throws Exception {
    Long postId = 1L;
    Post post = new Post();
    post.setId(postId);
    post.setName("Test Title");
    post.setContent("Test Content");
    post.setImg("Test Img URL");
    post.setTags(List.of("Tag1", "Tag2"));
    post.setDate(new Date());
    post.setPostedBy("Test User");

    when(postService.getPostById(postId)).thenReturn(post);

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/{id}", postId)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), status);

    String content = mvcResult.getResponse().getContentAsString();
    Post responsePost = new ObjectMapper().readValue(content, Post.class);
    assertEquals(post, responsePost);
  }

  @Test
  void testLikePost() throws Exception {
    Long postId = 1L;
    Post post = new Post();
    post.setId(postId);
    post.setLikeCount(1);

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/post/like/{id}", postId)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), status);
  }
}
