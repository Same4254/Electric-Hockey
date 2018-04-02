package Main;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800, HEIGHT = 500;

	public static enum Games {
		Main_Menu("Main Menu"),
		Level_Select("Level Select");
		
		String name;
		private Games(String name) {
			this.name = name;
		}
		
		public String getName() { return name; }
	}
	
	private JPanel cardPanel;
	
	public GameFrame() {
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		
		cardPanel = new JPanel(new CardLayout());
		cardPanel.add(new MainMenu(this), Games.Main_Menu);
		cardPanel.add(new LevelSelect(this), Games.Level_Select);
	}
	
	public void changePanel(Games game) {
		CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
		cardLayout.show(cardPanel, game.getName());
	}
	
	public JPanel getCardPanel() { return cardPanel; }
	
	public static void main(String[] args) {
		new GameFrame();
	}
}
