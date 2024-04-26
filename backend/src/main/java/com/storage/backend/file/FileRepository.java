package com.storage.backend.file;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    File findByfileName(String fileName);

    @Transactional
    long removeByfileName(String fileName);
}
