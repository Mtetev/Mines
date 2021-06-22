package components;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import main.Main;


public class Cell extends JButton {

	private static final long serialVersionUID = -3059067480908071499L;
	
	private int cellSize = 26;
	private int x, y;
	private boolean isMine;
	private int digit;
	private String res = "Resources\\";
	private String[] digits = new String[9];
	private int state = 0;
	private boolean isFlagged = false;
	private boolean isOpened = false;
	public static int startingX, startingY;
	private int posX, posY;
	private boolean leftMouseButtonDown = false, rightMouseButtonDown = false;
	
	public Cell() {
		this(0, 0, 0, 0, false);
	}
	
	public Cell(int x, int y, int posX, int posY, boolean isMine) {
		
		for (int i = 0; i < 9; i++) digits[i] = res + i + ".jpg";
		this.x = x;
		this.y = y;
		this.posX = posX;
		this.posY = posY;
		this.isMine = isMine;
		if (this.isMine) digit = -1;
		setBounds(x, y, cellSize, cellSize);
		setSize(cellSize, cellSize);
		setFont(new Font("Arial", Font.PLAIN, 8));
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON1) leftMouseButtonDown = false;
				if (arg0.getButton() == MouseEvent.BUTTON3) rightMouseButtonDown = false;
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON1) leftMouseButtonDown = true;
				if (arg0.getButton() == MouseEvent.BUTTON3) rightMouseButtonDown = true;
				if (leftMouseButtonDown && rightMouseButtonDown)
					if (getIsOpened()) {
						Main.board.middleButtonClicked(getXPosition(), getYPosition());
					}
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (Main.board.isFirstClick()) {
					startingX = getXPosition(); startingY = getYPosition();
					System.out.println(startingX + "   " + startingY);
					Main.board.generateMines();
					Main.board.setIsFirstClick(false);
				}
				if (arg0.getButton() == MouseEvent.BUTTON1) {
					open();
				} else if (arg0.getButton() == MouseEvent.BUTTON3) {
					flag();
				} else {
					if (getIsOpened()) {
						Main.board.middleButtonClicked(getXPosition(), getYPosition());
					}
				}
			}
		});
		setIcon(new ImageIcon("Resources//Tile.jpg"));
		
	}

	// ---------------------------------------------------------------
	// ------------------ Getters ------------------------------------
	// ---------------------------------------------------------------
	
	public int getXCoordinate() {
		return this.x;
	}
	
	public int getYCoordinate() {
		return this.y;
	}
	
	public Point getCoordiantes() {
		return new Point(this.x, this.y);
	}
	
	public boolean isMine() {
		return this.isMine;
	}
	
	public int getDigit() {
		return this.digit;
	}
	
	public boolean getIsFlagged() {
		return this.state == 1;
	}
	
	public boolean getIsOpened() {
		return this.isOpened;
	}
	
	public int getCellSize() {
		return this.cellSize;
	}
	
	public int getXPosition() {
		return this.posX;
	}
	
	public int getYPosition() {
		return this.posY;
	}

	// ---------------------------------------------------------------
	// ------------------ Setters ------------------------------------
	// ---------------------------------------------------------------
	
	public void setXCoordinate(int x) {
		this.x = x;
	}
	
	public void setYCoordinate(int y) {
		this.y = y;
	}
	
	public void setCoordinates(Point point) {
		this.x = point.x;
		this.y = point.y;
	}
	
	public void setDigit(int digit) {
		this.digit = digit;
	}
	
	public void setIsMine(boolean isMine) {
		this.isMine = isMine;
	}
	
	// ---------------------------------------------------------------
	// ------------------------- Actions -----------------------------
	// ---------------------------------------------------------------
	
	public boolean open() {
		if (this.state == 0 && !this.getIsOpened()) {
			isOpened = true;
			if(this.isMine) {
				this.explode();
				return false;
			} else {
				Main.board.unopenedCells--;
				if (Main.board.unopenedCells == 0 && !Main.board.over) Main.board.win();
				this.showDigit();
				Main.board.BFS(getXPosition(), getYPosition());
				return true;
			}
		}
		return true;
	}
	
	private void explode() {
		ImageIcon pic = new ImageIcon("Resources\\Mine.jpg");
		setIcon(pic);
		if(!Main.board.over) Main.board.lose();
	}
	
	private void showDigit() {
		setIcon(new ImageIcon(digits[this.digit]));
	}
	
	private void flag() {
		if (!this.getIsOpened()) {
			if (this.state == 0) {
				if (Main.board.flagsLeft()) {
					this.state = 1;
					Main.board.decreaseFlagsLeft();
					if (this.isMine()) {
						Main.board.minesLeft--;
						Main.board.minesLeftCount.setText(Integer.toString(Main.board.minesLeft));
						if (Main.board.minesLeft == 0) {
							Main.board.win();
						}
					}
					this.setIcon(new ImageIcon("Resources\\flag.jpg"));
				} else {
					this.state = 2;
					this.setIcon(new ImageIcon("Resources\\Question_mark.jpg"));
					
				}
			} else if (this.state == 1) {
				this.state = 2;
				Main.board.increasFlagsLeft();
				if (this.isMine()) {
					Main.board.minesLeft++;
					Main.board.minesLeftCount.setText(Integer.toString(Main.board.minesLeft));
				}
				this.setIcon(new ImageIcon("Resources\\Question_mark.jpg"));
			} else {
				this.state = 0;
				this.setIcon(new ImageIcon("Resources\\Tile.jpg"));
			}
		}
	}
	
	public void close() {
		this.setIcon(new ImageIcon("Resources//Tile.jpg"));
		this.state = 0;
		this.isOpened = false;
	}
	
	@Override
	public String toString() {
		return "cell: " + x + "  " + y;
	}

	public boolean isFlagged() {
		return isFlagged;
	}

	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}
}
