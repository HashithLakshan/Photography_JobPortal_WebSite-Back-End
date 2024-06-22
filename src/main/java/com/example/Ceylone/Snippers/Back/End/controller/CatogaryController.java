package com.example.Ceylone.Snippers.Back.End.controller;


import com.example.Ceylone.Snippers.Back.End.dto.CatogoryDto;
import com.example.Ceylone.Snippers.Back.End.services.CatogoryService;
import com.example.Ceylone.Snippers.Back.End.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@CrossOrigin("*")
@RestController
//@RequestMapping("api/catagory")
 public class CatogaryController {
private  final CatogoryService catogoryService;

@Autowired
    public CatogaryController(CatogoryService catogoryService) {
        this.catogoryService = catogoryService;
    }

    @GetMapping("/getAllCatagories")
    public CommonResponse getAllCatagories() {

    return catogoryService.getAllCtogory();
    }

    @PostMapping("/saveCatagorys")
    public ResponseEntity<CommonResponse> saveCatagory(@ModelAttribute CatogoryDto catogoryDto,
                                                   @RequestParam("file") MultipartFile file) {
        CommonResponse response = catogoryService.saveCatagory(catogoryDto, file);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/updateCatagories")
    public CommonResponse updateCatagories(@RequestBody CatogoryDto catogoryDto){
       return catogoryService.updateCtogory(catogoryDto);
  }

    @GetMapping("/getCatagory/{id}")
    public CommonResponse getCatagoryByID(@PathVariable String id){
        return catogoryService.getCatagoryById(id);
    }
}
