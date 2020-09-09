package org.playground;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.*;

public class FastLoader {
    private static final int MIN_REQUIRED_DATA_IN_RECORD = 3;
    private static Logger logger = LoggerFactory.getLogger(FastLoader.class);

    private static String filename;

    public static void main(String[] args) throws IOException {
        logger.info("FastLoader started.");

        readArguments(args);
        logger.info(String.format("Start loading file %s.", filename));

        Set<FastLoaderRecord> parsedRecords = parseFile();
        logger.info(String.format("Parsed %d lines from file %s.", parsedRecords.size(), filename));


        DatabaseAgent databaseAgent = new DatabaseAgent();
        try {
            databaseAgent.saveFastParserRecords(parsedRecords);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.info("FastLoader finished.");
    }

    private static Set<FastLoaderRecord> parseFile() throws IOException {
        File csvData = new File(filename);
        CSVFormat csvFormat = CSVFormat.RFC4180.withHeader().withDelimiter('|').withQuote('\'');

        Set<FastLoaderRecord> recordMap = new TreeSet<>(new FastLoadComparator());
        CSVParser parser = CSVParser.parse(csvData, Charset.forName("UTF-8"), csvFormat);
        for (CSVRecord csvRecord : parser) {
            if (csvRecord.size() < MIN_REQUIRED_DATA_IN_RECORD) {
                logger.warn("Skipping csv line because there is not enough columns. " + csvRecord.toString());
                continue;
            }

            try {
                Integer marketId = Integer.parseInt(csvRecord.get(1));
                recordMap.add(new FastLoaderRecord(
                                new MatchId(csvRecord.get(0)),
                                marketId,
                                csvRecord.get(2),
                                csvRecord.size() == 3 ? null : csvRecord.get(3)));

            } catch (NumberFormatException e) {
                logger.debug("Skipping csv line because the marketId is not a number." + csvRecord.toString());
                continue;
            }
        }
        return recordMap;
    }

    private static void readArguments(String[] args) {
        if (args.length == 0) {
            logger.error("No arguments provided.");
            throw new IllegalArgumentException("At least one argument is expected.");
        }

        filename = args[0];
    }
}
