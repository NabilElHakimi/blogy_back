package me.elhakimi.blogy.web;

import lombok.AllArgsConstructor;
import me.elhakimi.blogy.domain.Blog;
import me.elhakimi.blogy.service.BlogService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/blog")
@AllArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Blog blog) {
        return ResponseEntity.ok(blogService.save(blog));
    }

    @GetMapping("/published")
    public ResponseEntity<?> getPublishedBlogs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        return ResponseEntity.ok(blogService.findAllByPublishedIsTrue(page , size));
    }
}
