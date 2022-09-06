package com.tutorial.springboot.Response;


import com.tutorial.springboot.DTO.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileReport {
    public CustomerDTO customerDTO;
    public String report;
}
