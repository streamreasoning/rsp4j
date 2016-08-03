package it.polimi.preprocessing;

import it.polimi.rsp.baselines.utils.RDFSUtils;
import it.polimi.services.FileService;
import lombok.extern.log4j.Log4j;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


@Log4j
public class NTInputFileCleaner {
    static FileWriter w;
    static File file;
    static Model temp;
    private static long datatypeSize, inputSize, typeOfSizeBefore, typeOfSizeAfter, excludedSize = 0;
    private static int finalSize = 0;
    private static final boolean uniqueFile = true;

    public static void main(String[] args) throws IOException {
        datatypeSize = inputSize = typeOfSizeBefore = typeOfSizeAfter = excludedSize = finalSize = 0;

        for (String filenameOrURI : args) {
            cleanFile(filenameOrURI, filenameOrURI.replace(".nt", "_CLND.nt"), "CLEANED/");
        }

    }

    private static void cleanFile(String inputFileWithPath, String outputFile, String outputPath) throws IOException, FileNotFoundException {

        FileService.createFolders(outputPath);
        FileService.createFolders(outputPath + "datatype/");
        FileService.createFolders(outputPath + "excluded/");
        FileService.createFolders(outputPath + "logs/");

        Model inputOriginal = FileManager.get().loadModel(inputFileWithPath, null, "RDF/XML");
        inputSize = inputOriginal.size();
        Model typeOfBefore = inputOriginal.query(new SimpleSelector(null, RDF.type, (RDFNode) null));
        typeOfSizeBefore = typeOfBefore.size();

        Model input = inputOriginal.difference(typeOfBefore);

        input = removeDatatypeProperties(outputPath + "datatype/" + outputFile, inputOriginal, input);
        log.info("Datatype removed");
        input = removeSpecicifProperties(outputPath + "excluded/" + outputFile, inputOriginal, input);
        log.info("Excluded Properties removed");

        if (uniqueFile) {
            writeCleanedFile(outputPath + "BIG_FILE", typeOfBefore, input);
        } else {
            writeCleanedFile(outputPath + outputFile, typeOfBefore, input);
            log.info("CLeand File Build and Written");
        }

        typeOfSizeAfter = input.query(new SimpleSelector(null, RDF.type, (RDFNode) null)).size();

        // writeLogFile(inputFileWithPath, outputPath + "logs/" + outputFile);

        log.info("Log NOT File Avaliable");
    }

    private static void writeLogFile(String inputFile, String outputFileWithPath) throws IOException {
        file = new File(outputFileWithPath.replace("_CLND.nt", "_LOG.txt"));

        if (!file.exists()) {
            file.createNewFile();
        }

        w = new FileWriter(file, true);

        w.write("Input File Name with path [" + inputFile + "]");
        w.write(System.getProperty("line.separator"));
        w.write("Input File Size [" + inputSize + "]");
        w.write(System.getProperty("line.separator"));
        w.write("Input TypeOf Size [" + typeOfSizeBefore + "]");
        w.write(System.getProperty("line.separator"));
        w.write("Datatype Size [" + datatypeSize + "]");
        w.write(System.getProperty("line.separator"));
        w.write("Excluded Size [" + excludedSize + "]");
        w.write(System.getProperty("line.separator"));
        w.write("Output File Name With path [" + outputFileWithPath + "]");
        w.write(System.getProperty("line.separator"));
        w.write("Output File Size [" + finalSize + "]");
        w.write(System.getProperty("line.separator"));
        w.write("Output TypeOf Size [" + typeOfSizeAfter + "]");
        w.write(System.getProperty("line.separator"));
        w.flush();
        w.close();
    }

    private static void writeCleanedFile(String outputFileWithPath, Model typeOf, Model input) throws IOException {

        StmtIterator inputIterator = input.listStatements();
        Set<Statement> statments = new HashSet<Statement>();
        while (inputIterator.hasNext()) {
            Statement nextStatement = inputIterator.next();

            statments.add(nextStatement);

            Statement typeStatmentSubj = null, typeStatmentObj = null;
            if (!nextStatement.getSubject().isLiteral() && !nextStatement.getObject().isLiteral()) {

                StmtIterator typeIterSubj = typeOf.listStatements(nextStatement.getSubject(), RDF.type, (RDFNode) null);
                StmtIterator typeIterObj = typeOf.listStatements(nextStatement.getObject().asResource(), RDF.type, (RDFNode) null);

                while (typeIterSubj.hasNext() || typeIterObj.hasNext()) {
                    typeStatmentSubj = (typeIterSubj.hasNext()) ? typeIterSubj.nextStatement() : typeStatmentSubj;
                    typeStatmentObj = (typeIterObj.hasNext()) ? typeIterObj.nextStatement() : typeStatmentObj;
                    if (typeStatmentObj != null && typeIterSubj != null) {
                        statments.add(typeStatmentObj);
                        statments.add(typeStatmentSubj);
                        finalSize += 1;
                    }
                }
            }

        }

        file = new File(outputFileWithPath + ".nt");
        if (!file.exists()) {
            file.createNewFile();
        }

        w = new FileWriter(file, true);

        List<Statement> list = new ArrayList<Statement>(statments);
        Collections.shuffle(list);
        for (Statement statement : list) {

            w.write(("<" + statement.getSubject() + "> <" + statement.getPredicate() + "> <" + statement.getObject() + "> ."));
            w.write(System.getProperty("line.separator"));
        }

        w.flush();
        w.close();
    }

    private static Model removeSpecicifProperties(String outputFile, Model inputOriginal, Model input) throws IOException {

        for (String dp : RDFSUtils.EXCLUDED_PROPERTIES) {
            temp = inputOriginal.query(new SimpleSelector(null, ResourceFactory.createProperty(dp), (RDFNode) null));
            excludedSize += temp.size();
            input = input.difference(temp);
            // RDFDataMgr.write(new FileOutputStream(new File(outputFile + "-" + dp.split("#")[1] +
            // ".nt"), true), temp, RDFFormat.NT);

        }
        return input;
    }

    private static Model removeDatatypeProperties(String outputFile, Model inputOriginal, Model input) throws IOException {
        for (String dp : RDFSUtils.DATATYPEPROPERTIES) {
            temp = inputOriginal.query(new SimpleSelector(null, ResourceFactory.createProperty(dp), (RDFNode) null));
            datatypeSize += temp.size();
            input = input.difference(temp);
            // RDFDataMgr.write(new FileOutputStream(new File(outputFile + "-" + dp.split("#")[1] +
            // ".nt"), true), temp, RDFFormat.NT);
        }
        return input;
    }

}
