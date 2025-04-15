package com.xpert.service;

import com.xpert.dto.service.CreateServiceRequestDTO;
import com.xpert.dto.service.ServiceDTO;

import java.util.List;

public interface ServiceService {

    ServiceDTO createService(CreateServiceRequestDTO dto);

    List<ServiceDTO> getAllServices();

    ServiceDTO getServiceById(Integer id);
}
