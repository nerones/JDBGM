package com.nelsonx.jdbgm;

import java.sql.SQLException;

public class DefaultExceptionHandler implements ExceptionHandler {

	@Override
	public void connectionError(SQLException e) {
		System.out.println(e.getLocalizedMessage());
		System.out.println("testing");

	}

	@Override
	public void commitError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void AutoCommitError(SQLException e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeError(SQLException e) {
		// TODO Auto-generated method stub

	}

}
