package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import components.Board;

public class Main {
	
	public static Board board;
	
	public static void setMenus() {
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu gameMenu = new JMenu("Game");
		gameMenu.setMnemonic('G');
			
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				board.dispose();
			}
		});
		
		JMenuItem newGameItem = new JMenuItem("New Game");
		newGameItem.setMnemonic('N');
		newGameItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				board.setVisible(false);
				board.dispose();
				startNewGame();
			}
		});
		
		gameMenu.add(newGameItem);
		gameMenu.add(new JSeparator());
		gameMenu.add(exitItem);
		
		menuBar.add(gameMenu);
		
		board.setJMenuBar(menuBar);
	}
	
	public static void startNewGame() {
		
		board = new Board();
		setMenus();
	}
	
	public static void main(String... args) {
		
		startNewGame();
	}
}
