package com.ftt.service;

import com.ftt.dto.QueryDTO;
import com.ftt.result.Result;
import com.ftt.vo.SpecimenVO;

import java.util.List;

public interface QueryService {
    public Result<List<SpecimenVO>> querySpecimens(QueryDTO queryDTO);
}
