package com.tutorial.springboot.Controllers;

import com.mysql.cj.log.Log;
import com.tutorial.springboot.DTO.CustomerDTO;
import com.tutorial.springboot.DTO.InvoiceDTO;
import com.tutorial.springboot.Models.Customer;
import com.tutorial.springboot.Response.BasicResponse;
import com.tutorial.springboot.Response.FileReport;
import com.tutorial.springboot.Response.PageParam;
import com.tutorial.springboot.ResponseObject;
import com.tutorial.springboot.Services.CustomerService;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Controller
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> save(@RequestBody Customer customer){
        Customer customerSave = null;
        try {
            customerSave = customerService.save(customer);
            return new ResponseEntity<>(new ResponseObject("Save customer!", customerSave), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Customer> update(@PathVariable Integer id, @RequestBody Customer customer){
        return new ResponseEntity<>(customerService.update(customer, id), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    public ResponseEntity<BasicResponse> delete(@RequestBody CustomerDTO dto){
        Integer id = dto.getId();
        return customerService.deteleCustomer(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<BasicResponse> createCustomer(@RequestBody CustomerDTO dto){
        return customerService.createCustomer(dto);
    }

    @RequestMapping(value = "/country", method = RequestMethod.GET)
    public ResponseEntity<List<Integer>> findAllCountry(){
        List<Integer> countryFound = customerService.findAllCountryID();
        return new ResponseEntity<>(countryFound, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<BasicResponse> findCustomers(@RequestBody PageParam pageParam){
        return customerService.searchCustomers(pageParam);
    }
    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<BasicResponse> upload(@RequestParam("file") MultipartFile file) throws Exception{
        return customerService.importFile(file);
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=customers.xlsx");
        ByteArrayInputStream stream = customerService.exportFile();
        IOUtils.copy(stream, response.getOutputStream());
    }

    @RequestMapping(value = "/find-customer/{id}")
    public ResponseEntity<BasicResponse> findById(@PathVariable("id") Integer id){
        return customerService.findById(id);
    }

    @RequestMapping(value = "/get-all", method = RequestMethod.GET)
    public ResponseEntity<BasicResponse> getAllCustomer(){
        return customerService.getAllCustomers();
    }
}
