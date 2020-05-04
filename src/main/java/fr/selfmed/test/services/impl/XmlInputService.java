package fr.selfmed.test.services.impl;

import fr.selfmed.test.services.api.IXmlInputService;
import org.springframework.stereotype.Service;

@Service
public class XmlInputService implements IXmlInputService {
    @Override
    public String getXmlInput() {
        return "<root>\n" +
                "\t<transaction id=\"127\" date=\"2019-07-26\">\n" +
                "\t\t<label>Achat De Titres</label>\n" +
                "\t\t<amount>-800,10</amount>\n" +
                "\t\t<status>ASSIGNED</status>\n" +
                "\t</transaction>\n" +
                "\t<transaction id=\"124\" date=\"2019-02-01\">\n" +
                "\t\t<label>CHQ 564789-l</label>\n" +
                "\t\t<amount>42,22</amount>\n" +
                "\t\t<status>ASSIGNED</status>\n" +
                "\t</transaction>\n" +
                "\t<transaction id=\"956\" date=\"2019-04-25\">\n" +
                "\t\t<label>NETFLIX ORIGINAL</label>\n" +
                "\t\t<amount>-14,00</amount>\n" +
                "\t\t<status>NOTASSIGNED</status>\n" +
                "\t</transaction>\n" +
                "</root>";
    }
}