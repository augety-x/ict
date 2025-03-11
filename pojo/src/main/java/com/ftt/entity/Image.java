package com.ftt.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Image {
    private Integer imageId;
    private Integer fileId;
    private Integer userId;
    private Integer height;
    private Integer width;
    private String cameraModel;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private LocalDateTime takenAt;
    private String exifData;
    private Double focalLength;
    private Double aperture;
    private Integer iso;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}