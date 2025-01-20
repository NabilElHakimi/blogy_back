package me.elhakimi.blogy.dtos;


import me.elhakimi.blogy.domain.Blog;
import me.elhakimi.blogy.domain.Images;

import java.awt.*;
import java.util.List;

public record BlogDTO(
        String title,
        String content,
        String author,
        List<ImagesDTO> images
) {

    public static BlogDTO from(Blog blog) {
        return new BlogDTO(
                blog.getTitle(),
                blog.getContent(),
                blog.getAuthor(),
                ImagesDTO.from(blog.getImages())
        );
    }

}
