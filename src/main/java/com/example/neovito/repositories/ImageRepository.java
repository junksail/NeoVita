package com.example.neovito.repositories;

import com.example.neovito.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.LinkOption;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
