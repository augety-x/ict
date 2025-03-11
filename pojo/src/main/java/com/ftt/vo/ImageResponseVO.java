package com.ftt.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ImageResponseVO {
        private Integer image_id;
        private Integer file_id;

        private String image_path;
        private String thumbnail_path;
        private Integer specimen_identification;
        private String category_name;
        private Integer height;
        private Integer width;
        private Double latitude;
        private Double longitude;
        private Double altitude;
        private LocalDateTime taken_at;
        private Map<String, Object> extra_data;
}
