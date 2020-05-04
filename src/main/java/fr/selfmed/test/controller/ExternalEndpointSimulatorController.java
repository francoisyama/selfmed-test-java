package fr.selfmed.test.controller;

import fr.selfmed.test.services.impl.XmlInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExternalEndpointSimulatorController {
    private final XmlInputService xmlInputService;

    @Autowired
    public ExternalEndpointSimulatorController(XmlInputService xmlInputService) {
        this.xmlInputService = xmlInputService;
    }

    @GetMapping("api/input/xml")
    public String getXml() {
        return xmlInputService.getXmlInput();
    }
}