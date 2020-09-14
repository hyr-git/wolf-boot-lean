package com.hyr.boot.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author:cyz
 * @Date:2020/4/6 15:58
 * @Description:
 */
@Getter
@Setter
@Data
public class PageDTO {
    private int pageNum = 1;
    private int pageSize = 10;
}
