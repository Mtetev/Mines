package misc;

public class Options {
	
	private int minesCount;
	
	private int rowCount;
	private int columnCount;
	
	private boolean allowQuestionMarks = true;
	
	// --------- Constructors -------------
	
	/**
	 * Default constructor:
	 * sets mines count to 20
	 * sets row and column count to 10
	 * question marks are allowed
	 */
	public Options() {
		// default constructor
		minesCount = 20;
		rowCount = 10;
		columnCount = 10;
		allowQuestionMarks = true;
	}
	
	public Options(int minesCount, int rowCount, int columnCount, boolean allowQuestionMarks) {
		this.minesCount = minesCount;
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.allowQuestionMarks = allowQuestionMarks;
	}
	
	// --------- Setters ------------------
	
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}
	
	public void setMinesCount(int minesCount) {
		this.minesCount = minesCount;
	}
	
	public void allowQuestionMarks(boolean allowQuestionMarks) {
		this.allowQuestionMarks = allowQuestionMarks;
	}
	
	// ---------- Getters -------------------
	
	public int getRowCount() {
		return this.rowCount;
	}
	
	public int getColumnCount() {
		return this.columnCount;
	}
	
	public int getMinesCount() {
		return this.minesCount;
	}
	
	public boolean isQuestionMarksAllowed() {
		return this.allowQuestionMarks;
	}
	
	@Override
	public String toString() {
		
		String output = "";
		String eol = System.getProperty("line.separator");
		
		output += "minesCount=" + minesCount + eol;
		output += "rowCount=" + rowCount + eol;
		output += "columnCount=" + columnCount + eol;
		output += "allowQuestionMarks=";
		if (allowQuestionMarks) output +="yes";
		else output += "no";
		output += eol;
		
		return output;
	}
}
