package com.veracode.verademo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Constants {
	private final String JDBC_DRIVER = "mysql";
	private final String JDBC_HOSTNAME = "localhost";
	private final String JDBC_PORT = "3306";
	private final String JDBC_DATABASE = "blab";
	private final String JDBC_USER = "blab";

	private String hostname;
	private String port;
	private String dbname;
	private String username;
	private String password;

	public Constants() {
		String dbnameProp = System.getenv("RDS_DB_NAME");
		this.dbname = (dbnameProp == null) ? JDBC_DATABASE : dbnameProp;
		
		String hostnameProp = System.getenv("RDS_HOSTNAME");
		this.hostname = (hostnameProp == null) ? JDBC_HOSTNAME : hostnameProp;
		
		String portProp = System.getenv("RDS_PORT");
		this.port = (portProp == null) ? JDBC_PORT : portProp;
		
		String userProp = System.getenv("RDS_USERNAME");
		this.username = (userProp == null) ? JDBC_USER : userProp;
		    //REFACTOR to pull password from file
		try {
			this.password = readFile("/usr/db_creds/password");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final Constants create() {
		return new Constants();
	}

	public final String getJdbcConnectionString() {
		String connection = null;
		try {
			connection = String.format(
					"jdbc:%s://%s:%s/%s?user=%s&password=%s",
					JDBC_DRIVER,
					hostname,
					port,
					dbname,
					URLEncoder.encode(username, "UTF-8"),
					URLEncoder.encode(password, "UTF-8")
			);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return connection;
	}

	//REFACTOR - read file
	public String readFile(String path) throws IOException{
        return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.US_ASCII);
    }
}
