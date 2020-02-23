package com.zedge.homework.http.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Controller
public class IndexController {
    @Value("classpath:swagger.yaml")
    private Resource swaggerYaml;

    @GetMapping("/")
    public String index(Model model) throws IOException {
        Reader reader = new InputStreamReader(swaggerYaml.getInputStream());
        String yaml = FileCopyUtils.copyToString(reader);

        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        ObjectMapper jsonWriter = new ObjectMapper();
        String swaggerJson = jsonWriter.writeValueAsString(yamlReader.readValue(yaml, Object.class));
//System.out.println(swaggerJson);

        model.addAttribute("documentation", swaggerJson );
        return "index";
    }
}
