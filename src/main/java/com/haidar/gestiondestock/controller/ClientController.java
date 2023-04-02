package com.haidar.gestiondestock.controller;

import com.haidar.gestiondestock.controller.api.ClientApi;
import com.haidar.gestiondestock.dto.ClientDto;
import com.haidar.gestiondestock.service.ClientService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
public class ClientController implements ClientApi {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        return clientService.save(dto);
    }

    @Override
    public ClientDto findById(Integer id) {
        return clientService.findById(id);
    }

    @Override
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    @Override
    public void delete(Integer id) {
      clientService.delete(id);
    }
}
