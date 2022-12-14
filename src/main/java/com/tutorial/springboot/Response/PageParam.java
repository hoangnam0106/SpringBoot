package com.tutorial.springboot.Response;

import com.tutorial.springboot.DTO.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParam {
    private PageInfo pageInfo;
    private CustomerDTO customerDTO;
}
