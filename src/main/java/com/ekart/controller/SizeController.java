package com.ekart.controller;

import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Size;
import com.ekart.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/sizes")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping
    public ResponseEntity<List<Size>> getAllSize() {
        List<Size> sizes = sizeService.getAllSize();
        return new ResponseEntity<List<Size>>(sizes, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Size> getSizeById(@PathVariable Long id) throws RecordNotFoundException {
        Size size = sizeService.getSizeById(id);
        return new ResponseEntity<Size>(size, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Size> addSize(@RequestBody Size size) {
        Size newSize = sizeService.saveSize(size);
        return new ResponseEntity<Size>(newSize, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteSizeById(@PathVariable Long id) throws RecordNotFoundException {
        sizeService.deleteSizeById(id);
        return HttpStatus.FORBIDDEN;
    }
}
