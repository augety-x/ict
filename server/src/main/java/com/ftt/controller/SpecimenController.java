package com.ftt.controller;


import com.ftt.result.Result;
import com.ftt.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/specimen")
public class SpecimenController {

    @Autowired
    UploadService uploadService;

    @DeleteMapping("/delete/{specimen_id}")
    public Result deleteSpecimen(@PathVariable("specimen_id") int specimen_id,@RequestParam("user_id")Integer userId )
    {
        return uploadService.deleteSpecimen(specimen_id,userId);
    }

}
