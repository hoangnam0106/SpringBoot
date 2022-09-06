package com.tutorial.springboot.Services;

import com.tutorial.springboot.DTO.CustomerDTO;
import com.tutorial.springboot.DTO.CustomerMapper;
import com.tutorial.springboot.Models.Customer;
import com.tutorial.springboot.Repositories.CustomerRepository;
import com.tutorial.springboot.Response.*;
import com.tutorial.springboot.ResponseObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.*;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    List<FileReport> fileReports;


    public Customer save(Customer customer) throws Exception{
        if(customerRepository.existsByIdNo(customer.getIdNo())){
            throw new Exception("ID no existed");
        } else if(customer.getPostalCode().length() > 6){
            throw new Exception("postal code > 6");
        } else {
            return customerRepository.save(customer);
        }
    }

    public Customer update(Customer customer, Integer id){
        Optional<Customer> customerFound = customerRepository.findById(id);
        customerFound.ifPresent(customer1 -> {
              customer1.setFirstName(customer.getFirstName());
              customer1.setLastName(customer.getLastName());
              customer1.setCity(customer.getCity());
              customer1.setPostalCode(customer.getPostalCode());
              customer1.setIdNo(customer.getIdNo());

              customerRepository.save(customer1);
        });
        return customer;
    }

    public ResponseEntity<BasicResponse> deteleCustomer(Integer id){
        if(customerRepository.findById(id).isPresent()){
            customerRepository.deleteCustomer(id);
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("");
            basicResponse.setResult(null);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }else {
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("Khách hàng không tôn tại");
            basicResponse.setResult(null);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }
    }

    public ResponseEntity<BasicResponse> activeCustomer(Integer id){
            customerRepository.activeCustomer(id);
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("");
            basicResponse.setResult(null);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
    public ResponseEntity<BasicResponse> createCustomer(CustomerDTO dto){
        Customer customer = CustomerMapper.getINSTANCE().toEntity(dto);

        if(!customerRepository.existsByIdNo(customer.getIdNo())){
            customer.setIsActive(1);
            Customer customerSave = customerRepository.save(customer);
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("");
            basicResponse.setResult(customerSave);

            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
        } else if(customerRepository.existsByIdNo(customer.getIdNo()) && customerRepository.getActive(customer.getIdNo()) == 0){
            customerRepository.activeCustomer(customer.getIdNo());
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("ID No đã tồn tại");
            basicResponse.setResult(null);

            return new ResponseEntity<>(basicResponse,HttpStatus.OK);
        }else {
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("ID No đã tồn tại");
            basicResponse.setResult(null);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }

    }

    public ResponseEntity<BasicResponse> importFile( MultipartFile file) throws Exception{
        if (file.isEmpty()) {
            throw new RuntimeException("empty");
        }
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        String exel = "xlsx";
        if(!exel.equals(extension)){
            return null;
        }

        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        final int COLUMN_FIRST_NAME = 0;
        final int COLUMN_LAST_NAME = 1;
        final int COLUMN_STREET = 2;
        final int COLUMN_CITY = 3;
        final int COLUMN_POSTAL_CODE = 4;
        final int COLUMN_ID_NO = 5;

        List<Customer> customers = new ArrayList<>();
        Iterator<Row> iterator = sheet.iterator();
        List<FileReport> fileReports = new ArrayList<>();
        while (iterator.hasNext()){
            Row nextRow = iterator.next();
            Customer customer = new Customer();
            String report = "";

            for(int i = 0; i <= 5; i++){
                Cell cell = nextRow.getCell(i);
                if(cell == null || cell.toString().isEmpty()){
                    if(i == COLUMN_FIRST_NAME){
                        report = report + "Thiếu first name,";
                    }else if(i == COLUMN_LAST_NAME) {
                        report = report + "Thiếu last name,";
                    }else if(i == COLUMN_CITY) {
                        report = report + "Thiếu City,";
                    }else if(i == COLUMN_ID_NO) {
                        report = report + "Thiếu Id no,";
                    }

                }else{
                    int columnIndex = cell.getColumnIndex();
                    Object cellValue = getCellValue(cell);
                    if(cell.getRowIndex() > 0){
                        switch (columnIndex){
                            case COLUMN_FIRST_NAME:
                                String firstName = String.valueOf(cellValue);
                                customer.setFirstName(firstName);
                                break;
                            case COLUMN_LAST_NAME:
                                String lastName = String.valueOf(cellValue);
                                customer.setLastName(lastName);
                                break;
                            case COLUMN_STREET:
                                String street = String.valueOf(cellValue);
                                customer.setStreet(street);
                                break;
                            case COLUMN_CITY:
                                String city = String.valueOf(cellValue);
                                customer.setCity(city);
                                break;
                            case COLUMN_POSTAL_CODE:
                                Integer postalCode = (int) Double.parseDouble(cellValue.toString());
                                customer.setPostalCode(String.valueOf(postalCode));
                                break;
                            case COLUMN_ID_NO:
                                Integer idNo = (int) Double.parseDouble(cellValue.toString());
                                if(customerRepository.existsByIdNo(idNo)){
                                    report = report + "Id No đã tồn tại,";
                                }
                                customer.setIdNo(idNo);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            FileReport fileReport = new FileReport();
            fileReport.setCustomerDTO(CustomerMapper.getINSTANCE().toDTO(customer));
            if(report.isEmpty()){
                report = "OK!";
            }
            fileReport.setReport(report);
            fileReports.add(fileReport);
        }
        this.fileReports = fileReports;
        workbook.close();
        inputStream.close();
        int saveCount = 0;
        int errorCount = 0;

        for (FileReport fileReport: fileReports) {
            if(fileReport.getCustomerDTO().getIdNo() == null){
                errorCount++;
            }else {
                Customer customerSave = CustomerMapper.getINSTANCE().toEntity(fileReport.getCustomerDTO());
                ResponseEntity<BasicResponse> result = createCustomer(CustomerMapper.getINSTANCE().toDTO(customerSave));
                if(result.getBody().isSuccess()){
                    saveCount++;
                }else{
                    errorCount++;
                }
            }
        }
        ImportIF importIF = new ImportIF();
        importIF.setSaveCount(saveCount);
        importIF.setErrorCount(errorCount);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("importIF", importIF);
        fileReports.remove(0);
        resultMap.put("fileReports", fileReports);

        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setSuccess(true);
        basicResponse.setErrorCode("");
        basicResponse.setMessage("");
        basicResponse.setResult(resultMap);

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    private static Object getCellValue(Cell cell) {
            CellType cellType = cell.getCellType();
            Object cellValue = null;
            switch (cellType) {
                case BOOLEAN:
                    cellValue = cell.getBooleanCellValue();
                    break;
                case FORMULA:
                    Workbook workbook = cell.getSheet().getWorkbook();
                    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                    cellValue = evaluator.evaluate(cell).getNumberValue();
                    break;
                case NUMERIC:
                    cellValue = cell.getNumericCellValue();
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case _NONE:
                case BLANK:
                case ERROR:
                    break;
                default:
                    break;
            }
            return cellValue;
    }

    public ByteArrayInputStream exportFile() throws IOException {
        String[] HEADERs = {"First Name", "Last Name", "Street", "City", "Postal Code", "Id No", "error"};
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Customers");
        Row headerRow = sheet.createRow(0);
        for(int col = 0; col < HEADERs.length; col++){
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(HEADERs[col]);
        }
        int rowIndex = 1;
        for (FileReport fileReport: fileReports) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(fileReport.getCustomerDTO().getFirstName());
            row.createCell(1).setCellValue(fileReport.getCustomerDTO().getLastName());
            row.createCell(2).setCellValue(fileReport.getCustomerDTO().getStreet());
            row.createCell(3).setCellValue(fileReport.getCustomerDTO().getCity());
            row.createCell(4).setCellValue(fileReport.getCustomerDTO().getPostalCode());
            Integer idNo = fileReport.getCustomerDTO().getIdNo();
            if (idNo != null) {
                row.createCell(5).setCellValue(fileReport.getCustomerDTO().getIdNo());
            } else {
                row.createCell(5).setCellValue("");
            }
            Cell cell6 = row.createCell(6);
            cell6.setCellValue(fileReport.getReport());
            sheet.autoSizeColumn(6);
        }

        workbook.write(out);

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ResponseEntity<BasicResponse> searchCustomers(PageParam pageParam){
        CustomerDTO customerDTO = pageParam.getCustomerDTO();
        PageInfo pageInfo = pageParam.getPageInfo();
        Integer limit = pageInfo.getPageSize();
        Integer offset = pageInfo.getPageIndex() * limit;
        String idNo;
        if(customerDTO.getIdNo() != null){
            idNo = String.valueOf(customerDTO.getIdNo());
        }else{
            idNo = "";
        }

        List<Customer> customersFound = customerRepository.searchCustomers(
                "%" +customerDTO.getCity() + "%",
                "%" +customerDTO.getFirstName() + "%",
                "%" +customerDTO.getLastName() + "%",
                "%" +customerDTO.getStreet() + "%",
                "%" +customerDTO.getPostalCode() + "%",
                "%" +idNo + "%"
        );

        if(customersFound.size() > 0){
            List<CustomerDTO> customerDTOS = new ArrayList<>();
            for (int i = offset; i < offset + limit; i++){
                customerDTOS.add(CustomerMapper.getINSTANCE().toDTO(customersFound.get(i)));
            }
            Integer length = customersFound.size();

            Pageable pageable = PageRequest.of(pageInfo.getPageIndex(), pageInfo.getPageSize());
            Page<CustomerDTO> page = new PageImpl<>(customerDTOS, pageable, length);
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("Page " + pageInfo.getPageIndex() + 1);
            basicResponse.setResult(page);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }else{
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("Không có bản ghi nào");
            basicResponse.setResult(null);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }
    }
    public ResponseEntity<BasicResponse> findById(Integer id){
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer == null){
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(false);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("Khong ton tai customer");
            basicResponse.setResult(null);

            return new ResponseEntity<>(basicResponse, HttpStatus.NOT_FOUND);
        }else {
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("");
            basicResponse.setResult(customer);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }
    }


    public List<Integer> findAllCountryID(){
        return customerRepository.findAllCountryID();
    }

    public ResponseEntity<BasicResponse> getAllCustomers(){
        List<Customer> customers = customerRepository.findAll();

        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setSuccess(false);
        basicResponse.setErrorCode("");
        basicResponse.setMessage("");
        basicResponse.setResult(null);

        if(!customers.isEmpty()){
            List<CustomerDTO> customerDTOS = new ArrayList<>();
            for (Customer customer: customers) {
                customerDTOS.add(CustomerMapper.getINSTANCE().toDTO(customer));
            }
            basicResponse.setSuccess(true);
            basicResponse.setErrorCode("");
            basicResponse.setMessage("");
            basicResponse.setResult(customerDTOS);

            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
}
