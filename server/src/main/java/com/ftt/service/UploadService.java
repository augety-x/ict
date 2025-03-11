package com.ftt.service;

import com.ftt.dto.UploadDTO;
import com.ftt.result.Result;
import com.ftt.vo.UploadVO;

import java.io.IOException;

public interface UploadService {

    Result<UploadVO> uploadFile(UploadDTO uploadDTO) throws IOException;
    Result<UploadVO> insertSpecimen(UploadDTO uploadDTO) throws IOException;

    public Result deleteSpecimen(int specimenId,int userId);
}
