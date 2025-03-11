package com.ftt.service;

import com.ftt.dto.ImageRequestDTO;
import com.ftt.result.Result;
import com.ftt.vo.ImageResponseVO;

import java.util.List;

public interface ImageService {

    Result<List<ImageResponseVO>> getImages(ImageRequestDTO request);
}
