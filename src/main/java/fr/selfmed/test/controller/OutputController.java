package fr.selfmed.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import fr.selfmed.test.model.Transaction;
import fr.selfmed.test.services.IExternalApiSimulatorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@RestController
public class OutputController {
    private final IExternalApiSimulatorService externalApiSimulatorService;

    private static Logger logger = LogManager.getLogger(OutputController.class);

    private static final String XMLINPUTAPI_URI = "http://localhost:8080/api/input/xml";

    @Autowired
    public OutputController(IExternalApiSimulatorService externalApiSimulatorService) {
        this.externalApiSimulatorService = externalApiSimulatorService;
    }

    // méthode pour obtenir les données des transactions au format JSON
    @GetMapping("/api/output/json")
    public String getAsJsonFormat() {
        // initialise les variables
        String output = "";
        List <Transaction> transactionList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        // récupère la liste des transactions
        try {
            transactionList = getTransactionList();
        } catch (IOException e) {
           logger.error("Exception levée par getTransactionList dans la méthode getAsJsonFormat : " + e);
        }
        // passe de l'utilisation d'un champ "amount" à l'utilisation de deux champs "debit" et "credit"
        for (Transaction t : transactionList) {
            if (t.getAmount() < 0) {
                t.setCredit(0d);
                t.setDebit(-t.getAmount());
            }
            else {
                t.setCredit(t.getAmount());
                t.setDebit(0d);
            }
        }
        // Construit la string au format JSON
        try {
            output = objectMapper
                    .writerWithDefaultPrettyPrinter() // passe d'une présentation en ligne à une présentation sur plusieurs lignes
                    .withRootName("transactions") // ajoute un titre à la racine
                    .writeValueAsString(transactionList);
        } catch (JsonProcessingException e) {
            logger.error("Exception levée par la construction de la string JSON dans la méthode getAsJsonFormat : " + e);
        }
        return output;
    }

    // méthode pour obtenir les données des transaction au format selfmed
    @GetMapping("/api/output/selfmed")
    public String getAsSelfmedFormat() {
        // initialise la variable
        List<Transaction> transactionList = new ArrayList<>();
        try {
            //récupère la liste des transactions
           transactionList = getTransactionList();
        } catch (IOException e) {
            logger.error("Exception levée par getTransactionList dans la methode getAsSelfmedFormat : " + e);
        }
        // construit la string au format selfmed
        StringBuilder bld = new StringBuilder();
        for (Transaction t : transactionList) {
            int amount = (int) (t.getAmount()*100); //modifie le format d'affichage du champ "amount"
            // modifie le format d'affichage de la date
            LocalDate date = Instant.ofEpochMilli(t.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            bld.append("transaction:\n")
                    .append("\t\tid: ").append(t.getId()).append("\n")
                    .append("\t\tdate: ").append(date).append("\n")
                    .append("\t\tamount: ").append(amount).append("\n")
                    .append("\t\tlabel: ").append(t.getLabel()).append("\n")
                    .append("\t\tstatus: ").append(t.getStatus().getStatusNumber()).append("\n");
        }
        return bld.toString();
    }

    //méthode pour récupérer une liste de transactions à partir du format XML fourni par l'API
    public List <Transaction> getTransactionList() throws IOException {
        String xmlInput = externalApiSimulatorService.callApi(XMLINPUTAPI_URI);
        // remplace les , par des . afin de pouvoir mapper les double
        xmlInput = xmlInput.replace(',','.');
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(xmlInput, new TypeReference<>() {});
    }
}