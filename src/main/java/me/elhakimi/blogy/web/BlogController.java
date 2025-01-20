package me.elhakimi.blogy.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import me.elhakimi.blogy.domain.Blog;
import me.elhakimi.blogy.service.BlogService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/blog")
@AllArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<?> save(
                @RequestParam("blog") String blogJson,
                  @RequestParam("images") MultipartFile[] images) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
         Blog blog = objectMapper.readValue(blogJson, Blog.class);

        return ResponseEntity.ok(blogService.save(blog , images));

    }

    @GetMapping("/published")
    public ResponseEntity<?> getPublishedBlogs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        return ResponseEntity.ok(blogService.findAllByPublishedIsTrue(page , size));
    }
}
