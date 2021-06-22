package files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Scanner;

import javax.swing.JOptionPane;

import misc.Options;

public class OptionsManager {
	
	private File optionsFile = new File("Options.txt");
	
	public OptionsManager() {}
	
	public Options getOptions() {
		
		Options options = null;
		
		if (!optionsFile.exists()) {
			
			FileOutputStream fos = null;
			
			try {
				
				optionsFile.createNewFile();
				fos = new FileOutputStream(optionsFile);
				options = new Options();
				fos.write(options.toString().getBytes());
				fos.flush();
				fos.close();
				
			} catch(IOException ioe) {
				JOptionPane.showMessageDialog(null, "Cannot write in:" + optionsFile.getAbsolutePath(), "Write error!",
						JOptionPane.ERROR_MESSAGE);
				System.out.println(ioe.getMessage());
			} finally {
				
				try {
					if (fos != null) {
						fos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		else {
			
			Scanner readFile = null;
			
			try {
				
				readFile = new Scanner(optionsFile);
				int minesCount;
				int rowCount, columnCount;
				boolean allowQuestionMarks;
				
				// mines count
				String minesCountLine = readFile.nextLine();
				minesCount = Integer.parseInt(minesCountLine.substring("minesCount=".length()));
				
				// row count
				String rowCountLine = readFile.nextLine();
				rowCount = Integer.parseInt(rowCountLine.substring("rowCount=".length()));
				
				// column count
				String columnCountLine = readFile.nextLine();
				columnCount = Integer.parseInt(columnCountLine.substring("columnCount=".length()));
				
				// allow question marks
				String allowQuestionMarksLine = readFile.nextLine();
				String allowQuestionMarksString = allowQuestionMarksLine.substring("allowQuestionMarks=".length());
				if (allowQuestionMarksString.equals("yes")) allowQuestionMarks = true;
				else if (allowQuestionMarksString.equals("no")) allowQuestionMarks = false;
				else {
					System.out.println(allowQuestionMarksString);
					throw new Exception();
				}
				
				options = new Options(minesCount, rowCount, columnCount, allowQuestionMarks);
				
				// close scanner
				readFile.close();
			} catch (FileNotFoundException fnf) {
				fnf.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				readFile.close();
			}
		}
		
		return options;
	}
	
	public void writeOptions(Options options) {
		
		FileOutputStream fos = null;
		
		try {
			
			fos = new FileOutputStream(optionsFile);
			fos.write(options.toString().getBytes());
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String... args) {
		OptionsManager optionsManager = new OptionsManager();
		System.out.println(optionsManager.getOptions().toString());
	}
}
