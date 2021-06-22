package components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.Queue;

public class Board extends JFrame {
	
	private static final long serialVersionUID = -3323133316287119808L;
	
	Cell[][] cells;
	
	Random random = new Random();
	
	private boolean isFirstClick = true;
	
	public int minesCount = 20;
	public int minesLeft;
	public int unopenedCells;
	public int cellsCount;
	public boolean over = false;
	public MinesCountChooser mcc;
	private int flagsCount;
	private JLabel flagsLeft = new JLabel();
	public JLabel name = new JLabel();
	public JLabel minesLeftCount = new JLabel();
	private JLabel minesLeftName = new JLabel();
	String cheat = "beetroot";
	String entered = new String();
	
	private KeyListener kl = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {
			entered += e.getKeyChar();
		}
		
		@Override
		public void keyReleased(KeyEvent e) {}
		
		@Override
		public void keyPressed(KeyEvent e) {}
	};
	
	public Board() {
		
		mcc = new MinesCountChooser(50);
		
		int rows = 10; int columns = 10;
		cells = new Cell[rows][columns];
		setTitle("Minesweeper v1.17");
		name.setText("Flags Left:");
		
		minesLeftName.setText("Mines Left:");
		minesLeftName.setBounds(120, 298, 70, 20);
		minesLeftName.setVisible(false);
		minesLeftCount.setBounds(200, 298, 20, 20);
		minesLeftCount.setVisible(false);
		
		flagsLeft.setBounds(80, 298, 20, 20);
		
		name.setBounds(0, 298, 70, 20);
		
		setIconImage(new ImageIcon("Resources\\Frame Image.png").getImage());
		setResizable(false);
		
		int x = 0; int y = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				cells[i][j] = new Cell(x, y, i, j, false);
				cells[i][j].addKeyListener(kl);
				add(cells[i][j]);
				x += cells[0][0].getCellSize();
			}
			y += cells[0][0].getCellSize();
			x = 0;
		}
		
		setSize(266, 400);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(name); add(flagsLeft);
		add(minesLeftName); add(minesLeftCount);
		startGame();
	}
	
	public void decreaseFlagsLeft() {
		this.flagsCount--;
		flagsLeft.setText(Integer.toString(this.flagsCount));
	}
	
	public void increasFlagsLeft() {
		this.flagsCount++;
		flagsLeft.setText(Integer.toString(this.flagsCount));
	}
	
	public boolean flagsLeft() {
		
		return !(this.flagsCount == 0);
	}
	
	public void startGame() {
		
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				cell.close();
				cell.setIsMine(false);
			}
		}
		this.cellsCount = 100;
		this.unopenedCells = cellsCount - minesCount;
		this.minesLeft = minesCount;
		this.over = false;
		this.minesLeft = this.minesCount;
		this.unopenedCells = this.cellsCount - this.minesCount;
		this.flagsCount = this.minesCount;
		flagsLeft.setText(Integer.toString(this.flagsCount));
		minesLeftCount.setText(Integer.toString(this.minesCount));
		setVisible(true);
	}
	
	boolean notStartingCell(int x, int y) {
		if (x == Cell.startingX && y == Cell.startingY) return false;
		return true;
	}
	
	void generateMines() {
		int cnt = 0;
		while (cnt < minesCount) {
			int x = random.nextInt(10);
			int y = random.nextInt(10);
			if (!cells[x][y].isMine() && notStartingCell(x, y)) {
				cells[x][y].setIsMine(true);
				cnt++;
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (!cells[i][j].isMine()) 
					cells[i][j].setDigit(countNeighbouringMines(i, j));
			}
		}
		// openAllCells();
	}
	
	private int countNeighbouringMines(int x, int y) {
		int cnt = 0;
		if (x - 1 >= 0 && y - 1 >= 0 && cells[x - 1][y - 1].isMine()) cnt++;
		if (x - 1 >= 0 && cells[x - 1][y].isMine()) cnt++;
		if (x - 1 >= 0 && y + 1 < 10 && cells[x - 1][y + 1].isMine()) cnt++;
		if (y + 1 < 10 && cells[x][y + 1].isMine()) cnt++;
		if (x + 1 < 10 && y + 1 < 10 && cells[x + 1][y + 1].isMine()) cnt++;
		if (y - 1 >= 0 && cells[x][y - 1].isMine()) cnt++;
		if (x + 1 < 10 && y - 1 >= 0 && cells[x + 1][y - 1].isMine()) cnt++;
		if (x + 1 < 10 && cells[x + 1][y].isMine()) cnt++;
		return cnt;
	}
	
	private void openAllCells() {
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				if (cell.getIsFlagged() && !cell.isMine()) {
					cell.setIcon(new ImageIcon("Resources\\Crossed Flag.jpg"));
				}
				cell.open();
				cell.setEnabled(true);
			}
		}
	}
	
	void restartGame() {
		openAllCells();
		mcc.getMinesCount();
		isFirstClick = true;
	}
	
	public boolean isFirstClick() {
		return this.isFirstClick;
	}
	
	public void setIsFirstClick(boolean isFirstClick) {
		this.isFirstClick = isFirstClick;
	}
	
	void BFS(int x, int y) {
		Queue<Integer> q = new LinkedList<Integer>();
		int i = x, j = y;
		q.add(i); q.add(j);
		
		while (!q.isEmpty()) {
			i = q.remove();
			j = q.remove();
			if (i - 1 >= 0 && j - 1 >= 0 &&
					!cells[i - 1][j - 1].getIsFlagged() && 
					!cells[i - 1][j - 1].getIsOpened() &&
					!cells[i - 1][j - 1].isMine()) {
				if (cells[i - 1][j - 1].getDigit() == 0 || cells[i][j].getDigit() == 0) {
					q.add(i - 1); q.add(j - 1);
					cells[i - 1][j - 1].open();
				}
			}
			if (i - 1 >= 0 &&
					!cells[i - 1][j].getIsFlagged() && 
					!cells[i - 1][j].getIsOpened() &&
					!cells[i - 1][j].isMine()) {
				if (cells[i - 1][j].getDigit() == 0 || cells[i][j].getDigit() == 0) {
					q.add(i - 1); q.add(j);
					cells[i - 1][j].open();
				}
			}
			if (i - 1 >= 0 && j + 1 < 10 &&
					!cells[i - 1][j + 1].getIsFlagged() && 
					!cells[i - 1][j + 1].getIsOpened() &&
					!cells[i - 1][j + 1].isMine()) {
				if (cells[i - 1][j + 1].getDigit() == 0 || cells[i][j].getDigit() == 0) {
					q.add(i - 1); q.add(j + 1);
					cells[i - 1][j + 1].open();
				}
			}
			if (j + 1 < 10 &&
					!cells[i][j + 1].getIsFlagged() && 
					!cells[i][j + 1].getIsOpened() &&
					!cells[i][j + 1].isMine()) {
				if (cells[i][j + 1].getDigit() == 0 || cells[i][j].getDigit() == 0) {
					q.add(i); q.add(j + 1);
					cells[i][j + 1].open();
				}
				
			}
			if (i + 1 < 10 && j + 1 < 10 &&
					!cells[i + 1][j + 1].getIsFlagged() && 
					!cells[i + 1][j + 1].getIsOpened() &&
					!cells[i + 1][j + 1].isMine()) {
				if (cells[i + 1][j + 1].getDigit() == 0 || cells[i][j].getDigit() == 0) {
					q.add(i + 1); q.add(j + 1);
					cells[i + 1][j + 1].open();
				}
			}
			if (j - 1 >= 0 &&
					!cells[i][j - 1].getIsFlagged() && 
					!cells[i][j - 1].getIsOpened() &&
					!cells[i][j - 1].isMine()) {
				if (cells[i][j - 1].getDigit() == 0 || cells[i][j].getDigit() == 0) {
					q.add(i); q.add(j - 1);
					cells[i][j - 1].open();
				}
				
			}
			if (i + 1 < 10 && j - 1 >= 0 &&
					!cells[i + 1][j - 1].getIsFlagged() && 
					!cells[i + 1][j - 1].getIsOpened() &&
					!cells[i + 1][j - 1].isMine()) {
				if (cells[i + 1][j - 1].getDigit() == 0 || cells[i][j].getDigit() == 0) {
					q.add(i + 1); q.add(j - 1);
					cells[i + 1][j - 1].open();
				}
			}
			if (i + 1 < 10 &&
					!cells[i + 1][j].getIsFlagged() && 
					!cells[i + 1][j].getIsOpened() &&
					!cells[i + 1][j].isMine()) {
				if (cells[i + 1][j].getDigit() == 0 || cells[i][j].getDigit() == 0) {
					q.add(i + 1); q.add(j);
					cells[i + 1][j].open();
				}
			}
		}
	}
	
	public void win() {
		this.over = true;
		Object[] options = {"Yes.", "No"};
		String message = "Would you like to play again?";
		int n = JOptionPane.showOptionDialog(this, message, 
				"Congratulations! You have won!", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, options, options[0]);
		if (n == JOptionPane.YES_OPTION) {
			setVisible(false);
			restartGame();
		}
		else {
			setVisible(false);
			dispose();
		}
	}
	
	public void lose() {
		this.over = true;
		openAllCells();
		Object[] options = {"Yes.", "No"};
		int n = JOptionPane.showOptionDialog(this, "Would you like to try again?", 
				"You lose!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, options, options[0]);
		if (n == JOptionPane.YES_OPTION) {
			setVisible(false);
			restartGame();
		}
		else {
			setVisible(false);
			dispose();
		}
	}
	
	private int calculateFlaggedTiles(int x, int y) {
		int cnt = 0;
		if (x - 1 >= 0 && y - 1 >= 0 && cells[x - 1][y - 1].getIsFlagged()) cnt++;
		if (x - 1 >= 0 && cells[x - 1][y].getIsFlagged()) cnt++;
		if (x - 1 >= 0 && y + 1 < 10 && cells[x - 1][y + 1].getIsFlagged()) cnt++;
		if (y + 1 < 10 && cells[x][y + 1].getIsFlagged()) cnt++;
		if (x + 1 < 10 && y + 1 < 10 && cells[x + 1][y + 1].getIsFlagged()) cnt++;
		if (y - 1 >= 0 && cells[x][y - 1].getIsFlagged()) cnt++;
		if (x + 1 < 10 && y - 1 >= 0 && cells[x + 1][y - 1].getIsFlagged()) cnt++;
		if (x + 1 < 10 && cells[x + 1][y].getIsFlagged()) cnt++;
		return cnt;
	}
	
	public void middleButtonClicked(int x, int y) {
		
		if (cells[x][y].getDigit() == this.calculateFlaggedTiles(x, y)) {
			if (x - 1 >= 0 && y - 1 >= 0 && !cells[x - 1][y - 1].getIsFlagged()) {
				cells[x - 1][y - 1].open();
			}
			if (x - 1 >= 0 && !cells[x - 1][y].getIsFlagged()) {
				cells[x - 1][y].open();
			}
			if (x - 1 >= 0 && y + 1 < 10 && !cells[x - 1][y + 1].getIsFlagged()) {
				cells[x - 1][y + 1].open();
			}
			if (y + 1 < 10 && !cells[x][y + 1].getIsFlagged()) {
				cells[x][y + 1].open();
			}
			if (x + 1 < 10 && y + 1 < 10 && !cells[x + 1][y + 1].getIsFlagged()) {
				cells[x + 1][y + 1].open();
			}
			if (y - 1 >= 0 && !cells[x][y - 1].getIsFlagged()) {
				cells[x][y - 1].open();
			}
			if (x + 1 < 10 && y - 1 >= 0 && !cells[x + 1][y - 1].getIsFlagged()) {
				cells[x + 1][y - 1].open();
			}
			if (x + 1 < 10 && !cells[x + 1][y].getIsFlagged()) {
				cells[x + 1][y].open();
			}
		}
	}
}
