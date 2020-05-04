package fr.selfmed.test.controller;

import fr.selfmed.test.services.api.IExternalApiSimulatorService;

import fr.selfmed.test.services.api.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;



@RestController
public class OutputController {

    private final IExternalApiSimulatorService externalApiSimulatorService;
    private final ITransactionService transactionService;

    private static final String XMLINPUTAPI_URI = "http://localhost:8080/api/input/xml";

    @Autowired
    public OutputController(IExternalApiSimulatorService externalApiSimulatorService, ITransactionService transactionService) {
        this.externalApiSimulatorService = externalApiSimulatorService;
        this.transactionService = transactionService;
    }

    // méthode pour obtenir les données des transactions au format JSON
    @GetMapping("/api/output/json")
    public String getAsJsonFormat() throws IOException {
        String xmlInput = externalApiSimulatorService.callApi(XMLINPUTAPI_URI);
        return transactionService.xmlToJson(xmlInput);
    }

    // méthode pour obtenir les données des transaction au format selfmed
    @GetMapping("/api/output/selfmed")
    public String getAsSelfmedFormat() throws IOException {
        String xmlInput = externalApiSimulatorService.callApi(XMLINPUTAPI_URI);
        return transactionService.xmlToSelfmed(xmlInput);
    }


}