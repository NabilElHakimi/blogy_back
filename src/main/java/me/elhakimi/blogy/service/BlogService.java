package me.elhakimi.blogy.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.elhakimi.blogy.domain.Blog;
import me.elhakimi.blogy.domain.Images;
import me.elhakimi.blogy.dtos.BlogDTO;
import me.elhakimi.blogy.repository.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final StorageService storageService;

    @Transactional
    public BlogDTO save(Blog blog , MultipartFile[] images) {
        if(blog.getAuthor() == null) {

            for (MultipartFile image : images) {
                   String imageUrl = storageService.uploadFile(image , "blogs");
                     if (imageUrl == null || imageUrl.isEmpty()) {
                          throw new RuntimeException("Failed to upload image.");
                     }

                Images img = new Images();
                img.setImageUrl(imageUrl);
                img.setBlog(blog);
                blog.getImages().add(img);

            }

            Random random = new Random();
            int randomInt = random.nextInt(999999);
            blog.setAuthor("Anonymous_" + randomInt);
            blog.setCreatedDate(LocalDateTime.now());

        }

        return BlogDTO.from(blogRepository.save(blog));
    }

    public Page<BlogDTO> findAllByPublishedIsTrue(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return blogRepository.findAllByPublishedIsTrueOrderByCreatedDateDesc(pageable).map(BlogDTO::from);
    }

    public Blog findById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    public Blog update(Blog blog) {
        return blogRepository.save(blog);
    }

    public List<Blog> findByTitle(String title) {
        return blogRepository.findByTitle(title);
    }

    public Blog acceptBlog(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog != null) {
            blog.setPublished(true);
            return blogRepository.save(blog);
        }
        return null;
    }


    public Blog updateStatus(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog != null) {
            blog.setPublished(!blog.isPublished());
            return blogRepository.save(blog);
        }

        return null;
    }


}
