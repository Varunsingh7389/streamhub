package com.netflix.contentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Genre genre;


    private String director;
    private String cast;
    private int releaseYear;

    private double rating;
    private String tumbnailUrl;
    private int durationMinutes;

    //S3 key for videofile
    private String videoKey;

    //HLS master playlist URL for Streaming
    private String hlsUrl;

    @Enumerated(EnumType.STRING)
    private VideoStatus videoStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
