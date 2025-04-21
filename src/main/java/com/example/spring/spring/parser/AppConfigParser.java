package com.example.spring.spring.parser;

import com.example.spring.spring.model.servizio.TimeInterval;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppConfigParser {

    private String nomeApp;
    private String formatoDate;
    private List<TimeInterval> fascePerServizio=new ArrayList<>();

    public AppConfigParser(String filePath) {
        loadConfig(filePath);
    }

    private void loadConfig(String filePath) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            nomeApp = doc.getElementsByTagName("app-name").item(0).getTextContent();
            formatoDate = doc.getElementsByTagName("default-date-format").item(0).getTextContent();

            String stime1= doc.getElementsByTagName("start-time1").item(0).getTextContent();
            String etime1= doc.getElementsByTagName("end-time1").item(0).getTextContent();

            String stime2= doc.getElementsByTagName("start-time2").item(0).getTextContent();
            String etime2= doc.getElementsByTagName("end-time2").item(0).getTextContent();

            String stime3= doc.getElementsByTagName("start-time3").item(0).getTextContent();
            String etime3= doc.getElementsByTagName("end-time3").item(0).getTextContent();


            TimeInterval fasciaMattina = new TimeInterval(
                    LocalTime.of(Integer.parseInt(stime1.split(":")[0]), Integer.parseInt(stime1.split(":")[1])), LocalTime.of(Integer.parseInt(etime1.split(":")[0]), Integer.parseInt(stime1.split(":")[1]))
            );
            fascePerServizio.add(fasciaMattina);

            TimeInterval fasciaPomeriggio = new TimeInterval(
                    LocalTime.of(Integer.parseInt(stime2.split(":")[0]), Integer.parseInt(stime2.split(":")[1])), LocalTime.of(Integer.parseInt(etime2.split(":")[0]), Integer.parseInt(stime2.split(":")[1]))
            );
            fascePerServizio.add(fasciaPomeriggio);

            TimeInterval fasciaGiornata = new TimeInterval(
                    LocalTime.of(Integer.parseInt(stime3.split(":")[0]), Integer.parseInt(stime3.split(":")[1])), LocalTime.of(Integer.parseInt(etime3.split(":")[0]), Integer.parseInt(stime3.split(":")[1]))
            );
            fascePerServizio.add(fasciaGiornata);

            //maxTasks = Integer.parseInt(doc.getElementsByTagName("maxTasks").item(0).getTextContent());
            //outputFormat = doc.getElementsByTagName("outputFormat").item(0).getTextContent();



        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public String getNomeApp() {
        return nomeApp;
    }

    public String getFormatoDate() {
        return formatoDate;
    }

    public List<TimeInterval> getFascePerServizio() {
        return fascePerServizio;
    }
}
