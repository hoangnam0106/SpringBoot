package com.tutorial.springboot.Response;

import com.tutorial.springboot.DTO.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportIF {
    public int saveCount;
    public int errorCount;
}
