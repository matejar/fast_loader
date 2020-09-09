package org.playground;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class DatabaseAgent {
    private static final int BATCH_SIZE = 100;
    private static Logger logger = LoggerFactory.getLogger(DatabaseAgent.class);

    private Connection connection;

    public DatabaseAgent() {
        try {
            connection = DriverManager.getConnection(
                    AppProperties.getInstance().getValue(AppProperties.DATABASE_CONNECTION_URL),
                    AppProperties.getInstance().getValue(AppProperties.DATABASE_USERNAME),
                    AppProperties.getInstance().getValue(AppProperties.DATABASE_PASSWORD));
            logger.info("Database connection established.");
        } catch (SQLException e) {
            logger.error("Failed establish the database connection.", e);
            throw new RuntimeException("Failed establish the database connection.");
        }
    }

    public void saveFastParserRecords(Set<FastLoaderRecord> sortedRecords) throws SQLException {
        int recordCounter = 0;
        PreparedStatement preparedStatement = null;

        for (FastLoaderRecord fastLoaderRecord : sortedRecords) {
            if (recordCounter == 0) {
                preparedStatement =
                        connection.prepareStatement(
                                "INSERT INTO fast_insert (match_id, market_id, outcome_id, specifiers) VALUES (?, ?, ?, ?)");

            }
            preparedStatement.setString(1, fastLoaderRecord.getMatchId().getMatchId());
            preparedStatement.setInt(2, fastLoaderRecord.getMarketId());
            preparedStatement.setString(3, fastLoaderRecord.getOutcomeId());
            preparedStatement.setString(4, fastLoaderRecord.getSpecifiers());
            preparedStatement.addBatch();
            preparedStatement.clearParameters();
            recordCounter++;

            if (recordCounter == BATCH_SIZE) {
                int[] recordsSaved = preparedStatement.executeBatch();
                preparedStatement.close();
                logger.info(String.format("Saved %d records.", recordsSaved.length));
                recordCounter = 0;
            }
        }

        if (recordCounter > 0) {
            int[] recordsSaved = preparedStatement.executeBatch();
            preparedStatement.close();
            logger.info(String.format("Saved %d records.", recordsSaved.length));
        }
    }
}
