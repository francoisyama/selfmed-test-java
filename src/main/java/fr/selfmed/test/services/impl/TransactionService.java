package fr.selfmed.test.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fr.selfmed.test.model.Transaction;
import fr.selfmed.test.services.api.ITransactionService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    @Override
    public String xmlToJson(String xmlInput) throws IOException {
        return transactionsToJson(getTransactionsList(xmlInput));
    }

    @Override
    public String xmlToSelfmed(String xmlInput) throws IOException {
        return transactionsToSelfmed(getTransactionsList(xmlInput));
    }

    private String transactionsToJson(List<Transaction> transactionList) throws IOException {
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
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper
                .writerWithDefaultPrettyPrinter() // passe d'une présentation en ligne à une présentation sur plusieurs lignes
                .withRootName("transactions") // ajoute un titre à la racine
                .writeValueAsString(transactionList);
    }

    private String transactionsToSelfmed(List<Transaction> transactionList) {
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
    private List <Transaction> getTransactionsList(String xmlInput) throws IOException {
        // remplace les , par des . afin de pouvoir mapper les double
        xmlInput = xmlInput.replace(',','.');
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(xmlInput, new TypeReference<>() {});
    }
}
