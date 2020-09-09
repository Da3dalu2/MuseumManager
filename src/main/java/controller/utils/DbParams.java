package controller.utils;

public final class DbParams {

	/*
	 * final Calendar cal = Calendar.getInstance(); cal.set(Calendar.YEAR, 1988);
	 * cal.set(Calendar.MONTH, Calendar.JANUARY); cal.set(Calendar.DAY_OF_MONTH, 1);
	 *
	 * final Date dateRepresentation = cal.getTime();
	 */

	public static final String DBNAME = "Museo";
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String DBURI = "jdbc:mysql://localhost:3306/" + DBNAME + "?serverTimezone=UTC#";

	public static final String USERNAME = "root";
	public static final String PASSWORD = "6514";
}
