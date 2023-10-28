package com.veracode.verademo.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class RemoveAccountCommand implements BlabberCommand {
	private static final Logger logger = LogManager.getLogger("VeraDemo:RemoveAccountCommand");

	private Connection connect;

	public RemoveAccountCommand(Connection connect, String username) {
		super();
		this.connect = connect;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.veracode.verademo.commands.Command#execute()
	 */
	@Override
	public void execute(String blabberUsername) {
		String sqlQuery = "DELETE FROM listeners WHERE blabber=? OR listener=?;";
		logger.info(sqlQuery);
		PreparedStatement action;
		try {
			action = connect.prepareStatement(sqlQuery);

			action.setString(1, blabberUsername);
			action.setString(2, blabberUsername);
			action.execute();

			sqlQuery = "SELECT blab_name FROM users WHERE username = '" + blabberUsername + "'";
			Statement sqlStatement = connect.createStatement();
			logger.info(sqlQuery);
			ResultSet result = sqlStatement.executeQuery(sqlQuery);
			result.next();

			String event = "Removed account for blabber " + result.getString(1);
			sqlQuery = "INSERT INTO users_history (blabber, event) VALUES (?,?)";
			PreparedStatement pstmt = connect.prepareStatement(sqlQuery);
			pstmt.setString(0, blabberUsername);
			pstmt.setString(1, event);
			logger.info(sqlQuery);
			pstmt.executeQuery();

			sqlQuery = "DELETE FROM users WHERE username = ?";
			PreparedStatement delStatement = connect.prepareStatement(sqlQuery);
			delStatement.setString(0, blabberUsername);
			logger.info(sqlQuery);
			delStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
