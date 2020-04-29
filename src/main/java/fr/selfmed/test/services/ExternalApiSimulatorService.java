package fr.selfmed.test.services;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ExternalApiSimulatorService implements IExternalApiSimulatorService {

    // méthode pour appeler une api
    @Override
    public String callApi(String uri) throws IOException {
        // requête GET sur l'URI en paramètre
        Content content = Request.Get(uri).execute().returnContent();
        // retourne la response en string
        return content.asString();
    }
}