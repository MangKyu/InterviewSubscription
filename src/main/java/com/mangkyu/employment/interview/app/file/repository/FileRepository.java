package com.mangkyu.employment.interview.app.file.repository;

import com.mangkyu.employment.interview.app.file.entity.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<MyFile, Long> {

    Optional<MyFile> findByResourceId(final String resourceId);

}