package pl.nullpointerexception.Api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.nullpointerexception.Api.controller.dto.PostDto;
import pl.nullpointerexception.Api.controller.dto.PostDtoMapper;
import pl.nullpointerexception.Api.model.Post;
import pl.nullpointerexception.Api.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public List<PostDto> getPosts(@RequestParam(required = false) Integer page, Sort.Direction sort,
                                  @AuthenticationPrincipal UsernamePasswordAuthenticationToken user){
        int pageNumber =page!=null && page >=0 ? page:0;
        Sort.Direction sortDirection=sort!=null ? sort: Sort.Direction.ASC;
        return PostDtoMapper.mapToPostDtos(postService.getPosts(pageNumber,sortDirection));
    }
    @GetMapping("/posts/comments")
    public List<Post> getPostsWithComments(@RequestParam(required = false) Integer page,Sort.Direction sort){
        int pageNumber =page!=null && page >=0 ? page:0;
        Sort.Direction sortDirection=sort!=null ? sort: Sort.Direction.ASC;
        return postService.getPostsWithComments(pageNumber,sortDirection);
    }
    @GetMapping("/posts/{id}")
    public Post getSinglePost(@PathVariable long id){
        return postService.getSinglePost(id);
    }

    @PostMapping("/posts")
    public Post addPost(@RequestBody Post post){
        return postService.addPost(post);

    }
    @PutMapping("/posts")
    public Post editPost(@RequestBody Post post){
        return postService.editPost(post);
    }
    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable  long id){
        postService.deletePost(id);
    }
}
