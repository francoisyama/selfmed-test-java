package fr.selfmed.test.services.api;

import java.io.IOException;

public interface ITransactionService {

    String xmlToJson (String xmlInput) throws IOException;
    String xmlToSelfmed (String xmlInput) throws IOException;

}
