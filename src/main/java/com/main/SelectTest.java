package com.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectTest {

    private static final Logger log = LoggerFactory.getLogger(SelectTest.class);

    // TODO: move creds to env vars or a config file; hardcoded here only for demo
    private static final String JDBC_URL  = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String JDBC_USER = "c##harsh";
    private static final String JDBC_PWD  = "harsh";

    private static final String GET_EMPS_QUERY =
            "SELECT EMPNO, ENAME, JOB, SAL, DEPTNO FROM EMP";

    public static void main(String[] args) {
        log.debug("Start of main");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
             PreparedStatement ps = conn.prepareStatement(GET_EMPS_QUERY);
             ResultSet rs = ps.executeQuery()) {

            log.info("Connection established; executing query.");

            int rows = 0;
            while (rs.next()) {
                int empNo     = rs.getInt("EMPNO");
                String name   = rs.getString("ENAME");
                String job    = rs.getString("JOB");
                float sal     = rs.getFloat("SAL");
                int deptNo    = rs.getInt("DEPTNO");

                System.out.printf("%d %s %s %.2f %d%n", empNo, name, job, sal, deptNo);
                rows++;
            }

            if (rows == 0) {
                log.warn("Query returned no rows.");
            } else {
                log.debug("Processed {} row(s).", rows);
            }

        } catch (SQLException e) {

            log.error("SQL error while executing query.", e);
        } catch (Exception e) {
            log.error("Unexpected error.", e);
        } finally {
            log.debug("End of main");
        }
    }
}
