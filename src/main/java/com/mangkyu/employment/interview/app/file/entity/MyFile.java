package com.mangkyu.employment.interview.app.file.entity;

import com.mangkyu.employment.interview.app.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "my_file")
@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class MyFile extends BaseEntity {

    @Column(nullable = false)
    private String resourceId;

    @Column(nullable = false)
    private String fileName;

}
