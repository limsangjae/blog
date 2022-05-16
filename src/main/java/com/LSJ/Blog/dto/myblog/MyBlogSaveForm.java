package com.LSJ.Blog.dto.myblog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyBlogSaveForm {

    @NotBlank // null , "" , " " 안됨
    private String blogName;

    private String blogDesc;

}
