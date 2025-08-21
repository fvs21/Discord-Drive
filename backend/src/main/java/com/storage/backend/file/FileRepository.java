package com.storage.backend.file;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    File findByfileName(String fileName); //method to find file by fileName;

    @Transactional
    long removeByfileName(String fileName); //method to remove file by fileName;

    @Transactional
    long removeById(Long id);

    List<File> findAllByOrderByIdDesc();
}
