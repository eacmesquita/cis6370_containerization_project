package com.veracode.verademo.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class IgnoreCommand implements BlabberCommand {
	private static final Logger logger = LogManager.getLogger("VeraDemo:IgnoreCommand");

	private Connection connect;

	private String username;

	public IgnoreCommand(Connection connect, String username) {
		super();
		this.connect = connect;
		this.username = username;
	}

	@Override
	public void execute(String blabberUsername) {
		String sqlQuery = "DELETE FROM listeners WHERE blabber=? AND listener=?;";
		logger.info(sqlQuery);
		PreparedStatement action, selectPstmt, insertPstmt;
		try {
			action = connect.prepareStatement(sqlQuery);

			action.setString(1, blabberUsername);
			action.setString(2, username);
			action.execute();

			sqlQuery = "SELECT blab_name FROM users WHERE username = ?";
			selectPstmt = connect.prepareStatement(sqlQuery);
			selectPstmt.setString(1, blabberUsername);
			logger.info(sqlQuery);
			ResultSet result = selectPstmt.executeQuery();
			result.next();

			String event = username + " is now ignoring " + blabberUsername + " (" + result.getString(1) + ")";
			sqlQuery = "INSERT INTO users_history (blabber, event) VALUES (?,?)";
			insertPstmt = connect.prepareStatement(sqlQuery);
			logger.info(sqlQuery);
			insertPstmt.setString(1, blabberUsername);
			insertPstmt.setString(2, event);
			insertPstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
