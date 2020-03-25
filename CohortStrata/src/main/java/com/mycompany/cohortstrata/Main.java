/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cohortstrata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
 
import org.ohdsi.circe.cohortdefinition.CohortExpression;

/**
 *
 * @author admin_asena5
 */
public class Main {
    
    /*
     * ASSUMES: You've downloaded the file using R so that the JSON is NOT
     * modified in the way that it comes from ATLAS. Here is the code
     * cdT <- OhdsiRTools::getCohortDefinitionExpression(definitionId = 14896, baseUrl = "https://server:8443/WebAPI")
     * write(cdT$expression, file = file.path(cohortPath, "atlasDownloads", "t.json"))
    */
    public static void main(String[] args) {
        String tJson;
        String tWithoutOJson;
        String tWithOJson;
        try
        {
            tJson = new String( Files.readAllBytes(Paths.get("E:\\git\\anthonysena\\covidCharacterizationTest\\inst\\cohorts\\atlasDownloads\\T.json")));
            tWithoutOJson = new String( Files.readAllBytes(Paths.get("E:\\git\\anthonysena\\covidCharacterizationTest\\inst\\cohorts\\atlasDownloads\\t_without_o.json")));
            tWithOJson = new String( Files.readAllBytes(Paths.get("E:\\git\\anthonysena\\covidCharacterizationTest\\inst\\cohorts\\atlasDownloads\\t_with_o.json")));
            
            CohortExpression ceT;
            ceT = org.ohdsi.analysis.Utils.deserialize(tJson, CohortExpression.class);
            System.out.println(ceT.inclusionRules.size());
            
            CohortExpression ceTwithoutO;
            ceTwithoutO = org.ohdsi.analysis.Utils.deserialize(tWithoutOJson, CohortExpression.class);
            System.out.println(ceTwithoutO.inclusionRules.size());
            System.out.println(ceTwithoutO.inclusionRules.get(1).name);
            
            CohortExpression ceTwithO;
            ceTwithO = org.ohdsi.analysis.Utils.deserialize(tWithOJson, CohortExpression.class);
            System.out.println(ceTwithO.inclusionRules.size());
            System.out.println(ceTwithO.inclusionRules.get(1).name);
            
            ceT.inclusionRules.add(1, ceTwithO.inclusionRules.get(1));
            String foo = org.ohdsi.analysis.Utils.serialize(ceT);
            File file = new File("E:\\git\\anthonysena\\covidCharacterizationTest\\inst\\cohorts\\atlasDownloads\\java_generated_t_with_o.json");
            FileWriter fw = new FileWriter(file);
            fw.write(foo);
            fw.flush();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
