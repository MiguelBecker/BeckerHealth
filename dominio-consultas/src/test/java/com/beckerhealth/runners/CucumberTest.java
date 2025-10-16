package com.beckerhealth.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",       // Caminho onde estão os .feature
    glue = "com.beckerhealth.steps",                // Pacote dos Steps
    plugin = {
        "pretty",                                   // Saída legível no console
        "html:target/cucumber-report.html",         // Relatório em HTML
        "json:target/cucumber-report.json"          // Relatório em JSON
    },
    monochrome = true,
    snippets = CucumberOptions.SnippetType.CAMELCASE // Gera nomes de métodos no padrão camelCase
)
public class CucumberTest {
}
