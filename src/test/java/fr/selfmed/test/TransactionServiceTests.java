package fr.selfmed.test;


import fr.selfmed.test.services.impl.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class TransactionServiceTests {

    @Autowired
    private TransactionService transactionService;

    private static final String validXmlInput = "<root>\n" +
            "\t<transaction id=\"12\" date=\"2020-03-16\">\n" +
            "\t\t<label>Remboursement CPAM</label>\n" +
            "\t\t<amount>202,10</amount>\n" +
            "\t\t<status>ASSIGNED</status>\n" +
            "\t</transaction>\n" +
            "\t<transaction id=\"24\" date=\"2020-04-01\">\n" +
            "\t\t<label>CHQ 567489-l</label>\n" +
            "\t\t<amount>-32,52</amount>\n" +
            "\t\t<status>NOTASSIGNED</status>\n" +
            "\t</transaction>\n" +
            "</root>";

    private static final String expectedJsonOutput = "{\r\n" +
            "  \"transactions\" : [ {\r\n" +
            "    \"id\" : 12,\r\n" +
            "    \"date\" : \"16/03/2020\",\r\n" +
            "    \"label\" : \"Remboursement CPAM\",\r\n" +
            "    \"debit\" : 0.0,\r\n" +
            "    \"credit\" : 202.1,\r\n" +
            "    \"status\" : \"ASSIGNED\"\r\n" +
            "  }, {\r\n" +
            "    \"id\" : 24,\r\n" +
            "    \"date\" : \"01/04/2020\",\r\n" +
            "    \"label\" : \"CHQ 567489-l\",\r\n" +
            "    \"debit\" : 32.52,\r\n" +
            "    \"credit\" : 0.0,\r\n" +
            "    \"status\" : \"NOTASSIGNED\"\r\n" +
            "  } ]\r\n" +
            "}";

    private static final String expectedSelfmedOutput = "transaction:\n" +
            "\t\tid: 12\n" +
            "\t\tdate: 2020-03-16\n" +
            "\t\tamount: 20210\n" +
            "\t\tlabel: Remboursement CPAM\n" +
            "\t\tstatus: 0\n" +
            "transaction:\n" +
            "\t\tid: 24\n" +
            "\t\tdate: 2020-04-01\n" +
            "\t\tamount: -3252\n" +
            "\t\tlabel: CHQ 567489-l\n" +
            "\t\tstatus: 1\n"
    ;

    @Test
    public void testXmlToJson() throws IOException {
        Assertions.assertEquals(expectedJsonOutput,transactionService.xmlToJson(validXmlInput));
    }

    @Test
    public void testXmlToSelfmed() throws IOException {
        Assertions.assertEquals(expectedSelfmedOutput, transactionService.xmlToSelfmed(validXmlInput));
    }

}
