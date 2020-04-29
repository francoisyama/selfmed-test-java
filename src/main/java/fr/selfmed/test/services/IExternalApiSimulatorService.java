package fr.selfmed.test.services;


import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface IExternalApiSimulatorService {

    String callApi(String uri) throws IOException;
}
