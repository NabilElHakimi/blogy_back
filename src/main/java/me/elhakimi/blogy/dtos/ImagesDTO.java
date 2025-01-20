package me.elhakimi.blogy.dtos;

import me.elhakimi.blogy.domain.Images;

import java.util.List;
import java.util.stream.Collectors;

public record ImagesDTO(
        String imageUrl
) {

    public static List<ImagesDTO> from(List<Images> images) {
        return images.stream()
                .map(image -> new ImagesDTO(image.getImageUrl()))
                .collect(Collectors.toList());
    }
}
