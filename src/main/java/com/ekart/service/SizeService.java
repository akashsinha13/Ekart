package com.ekart.service;

import com.ekart.dao.SizeRepository;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    public List<Size> getAllSize() {

        return sizeRepository.findAll();
    }

    public Optional<Size> getSizeById(Long id) throws RecordNotFoundException {

        Optional<Size> size = sizeRepository.findById(id);

        if (size.isPresent()) {
            return size;
        } else {
            throw new RecordNotFoundException("No size exists for given id " + id);
        }
    }

    public Size saveSize(Size size) {

        return sizeRepository.save(size);
    }


    public void deleteSizeById(Long id) throws RecordNotFoundException {

        Optional<Size> size = sizeRepository.findById(id);

        if (size.isPresent()) {
            sizeRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No size exists for given id " + id);
        }
    }
}
