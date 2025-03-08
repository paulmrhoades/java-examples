package com.keepitsimples.examples.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MySqlJdbcExampleMain {

    private static final Logger logger = LoggerFactory.getLogger(MySqlJdbcExampleMain.class);

    public static void main(String... args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        final Connection con = DriverManager.getConnection("jdbc:mysql://192.168.68.129:3306/paul", "dev", "password");
        final Statement stmt = con.createStatement();
        executeQuery(stmt, "select table_name, column_name, extra from information_schema.columns where table_schema='paul'");
        executeQuery(stmt, "select * from flyway_schema_history");
        executeQuery(stmt, "select * from person");
        execute(stmt, "insert into person(first_name, last_name) values ('paul', 'rhoades')");
        execute(stmt,"insert into person(first_name, last_name) values ('bernie', 'partridge')");
        execute(stmt,"insert into person(first_name, last_name) values ('charlie', 'rhoades')");
        execute(stmt,"insert into person(first_name, last_name) values ('millie', 'rhoades')");
        executeQuery(stmt, "select * from person");
        execute(stmt,"delete from person");
        executeQuery(stmt, "select * from person");
        stmt.close();
        con.close();
    }

    private static  void execute(Statement stmt, String sql) throws SQLException {
        logger.info("{}", sql);
        stmt.execute(sql);
    }

    private static  void executeQuery(Statement stmt, String sql) throws SQLException {
        logger.info("{} ...", sql);
        boolean empty = true;
        final ResultSet results = stmt.executeQuery(sql);
        final ResultSetMetaData resultsMetaData = results.getMetaData();
        while (results.next()) {
            final StringBuilder sb = new StringBuilder();
            sb.append("  ").append(resultsMetaData.getTableName(1)).append(":{");
            for (int col_idx = 1; col_idx <= resultsMetaData.getColumnCount(); col_idx++) {
                sb.append(resultsMetaData.getColumnName(col_idx)).append("='").append(results.getString(col_idx)).append("', ");
            }
            sb.setCharAt(sb.length() - 2, '}');
            logger.info(sb.toString().toLowerCase());
            empty = false;
        }
        results.close();
        if (empty) {
            logger.info("  {no data}");
        }
    }
}