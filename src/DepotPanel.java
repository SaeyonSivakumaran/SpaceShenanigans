import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.MatteBorder;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepotPanel extends JPanel {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int screenX = (int) screenSize.getWidth();
	int screenY = (int) screenSize.getHeight();
	Image backgroundImage = null;
	JFrame frame;

	DepotPanel(JFrame referenceFrame) {
		this.frame = referenceFrame;
		JPanel upgrade = new UpgradePanel();
		this.setLayout(null);
		upgrade.setBounds(0, 2 * screenY / 3, screenX, screenY / 3);
		try { // loading images
			backgroundImage = new ImageIcon("SpaceStationHangar.png").getImage();
		} catch (Exception ex) {
			System.out.println("image didn't load");
		}
		backgroundImage = backgroundImage.getScaledInstance(screenX, screenY, Image.SCALE_DEFAULT);
		
		//Make a JButton to go back to the Map Panel
		JButton mapButton = new JButton("Travel Elsewhere");
		mapButton.addActionListener(new MapListener());
		mapButton.setBounds(screenX/12, screenX/12, 400, 100);
		mapButton.setFont(new Font("Tahoma", Font.BOLD, 29));
		
		this.add(upgrade);
		this.add(mapButton);
	}
	
	class MapListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame.setContentPane(new MapPanel());
			frame.invalidate();
			frame.validate();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // required to ensure the panel is correctly redrawn
		g.drawImage(backgroundImage, 0, 0, null);
		repaint();
	}

	public class UpgradePanel extends JPanel {
		/**
		 * Create the panel.
		 */
		JButton button; //engine
		JButton button_1; //mining
		JButton button_2; //shields
		JButton button_3; //weapons
		JButton button_5; //Deepspaceviewer
		
		public UpgradePanel() {
			ImageIcon buttonIcon = null;
			ImageIcon blastCrystalIcon = null;
			ImageIcon grapheneIcon = null;
			ImageIcon intellectiumIcon = null;
			ImageIcon plutoniumIcon = null;
			ImageIcon pyroxiumIcon = null;
			ImageIcon starliteIcon = null;
			ImageIcon steelIcon = null;
			ImageIcon repairIcon = null;
			ImageIcon shieldJammerIcon = null;
			ImageIcon laserIcon = null;
			ImageIcon missileIcon = null;

			try { // loading images
				blastCrystalIcon = new ImageIcon(ImageIO.read(new File("BlastCrystal.png")));
				grapheneIcon = new ImageIcon(ImageIO.read(new File("Graphene.png")));
				intellectiumIcon = new ImageIcon(ImageIO.read(new File("Intellectium.png")));
				plutoniumIcon = new ImageIcon(ImageIO.read(new File("Plutonium.png")));
				pyroxiumIcon = new ImageIcon(ImageIO.read(new File("Pyroxium.png")));
				starliteIcon = new ImageIcon(ImageIO.read(new File("Starlite.png")));
				steelIcon = new ImageIcon(ImageIO.read(new File("Steel.png")));
				buttonIcon = new ImageIcon(ImageIO.read(new File("Button.png")));
				repairIcon = new ImageIcon(ImageIO.read(new File("RepairButton.png")));
				shieldJammerIcon = new ImageIcon(ImageIO.read(new File("ShieldJammer.png")));
				laserIcon = new ImageIcon(ImageIO.read(new File("Laser.png")));
				missileIcon = new ImageIcon(ImageIO.read(new File("MissileLauncher.png")));

			} catch (Exception ex) {
				System.out.println("image didn't load");
			}

			this.setPreferredSize(new Dimension(screenX, screenY / 3));
			UIManager.put("TabbedPane.selected", new Color(0, 183, 229)); // change tab to white when it is selected
			setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			setForeground(new Color(0, 0, 139));
			setBackground(new Color(51, 0, 102));

			JLabel lblUpgradeShip = new JLabel("Upgrade Ship");
			lblUpgradeShip.setBackground(new Color(102, 205, 170));
			lblUpgradeShip.setForeground(new Color(255, 255, 255));
			lblUpgradeShip.setHorizontalAlignment(SwingConstants.CENTER);
			lblUpgradeShip.setFont(new Font("Gill Sans MT", Font.BOLD, 28));

			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 19));
			tabbedPane.setForeground(Color.WHITE);
			GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(lblUpgradeShip, GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 1223, Short.MAX_VALUE));
			groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup().addComponent(lblUpgradeShip)
							.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE)));

			tabbedPane.setBackground(new Color(3, 53, 132));

			// Tab for engine upgrades
			JPanel EnginePanel = new JPanel();
			EnginePanel.setBackground(Color.LIGHT_GRAY);
			tabbedPane.addTab("Engine Module", null, EnginePanel, null);

			JLabel label = new JLabel("Current Level: ~~");
			label.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			button = new JButton(buttonIcon);
			button.setFont(new Font("Tahoma", Font.PLAIN, 28));
			button.addActionListener(new UpgradeListener());

			JLabel label_1 = new JLabel("Cost:");
			label_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel lblUpgradeEngines = new JLabel("Upgrade Engines");
			lblUpgradeEngines.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));

			JLabel lblNewLabel_1 = new JLabel(steelIcon);

			JLabel label_9 = new JLabel(grapheneIcon);

			JLabel lblX = new JLabel("x1000");
			lblX.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_16 = new JLabel("x1000");
			label_16.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_4 = new JLabel(plutoniumIcon);

			JLabel label_20 = new JLabel("x1000");
			label_20.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
			GroupLayout gl_EnginePanel = new GroupLayout(EnginePanel);
			gl_EnginePanel.setHorizontalGroup(gl_EnginePanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_EnginePanel.createSequentialGroup().addGap(10).addComponent(label,
							GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_EnginePanel.createSequentialGroup().addGap(15).addComponent(lblUpgradeEngines,
							GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_EnginePanel.createSequentialGroup().addGap(77)
							.addGroup(gl_EnginePanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_EnginePanel.createSequentialGroup().addGap(71).addComponent(lblNewLabel_1,
											GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
									.addGroup(gl_EnginePanel.createSequentialGroup()
											.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
											.addGap(49)))
							.addGap(10).addComponent(lblX, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE).addGap(7)
							.addComponent(label_9, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE).addGap(10)
							.addComponent(label_16, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE).addGap(42)
							.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_20, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addGap(337))
					.addGroup(gl_EnginePanel.createSequentialGroup().addGap(220)
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(308, Short.MAX_VALUE)));
			gl_EnginePanel.setVerticalGroup(gl_EnginePanel.createParallelGroup(Alignment.LEADING).addGroup(gl_EnginePanel
					.createSequentialGroup()
					.addGroup(gl_EnginePanel.createParallelGroup(Alignment.LEADING, false).addGroup(gl_EnginePanel
							.createSequentialGroup().addGap(11)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE).addGap(46)
							.addComponent(lblUpgradeEngines, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addGap(7)
							.addGroup(gl_EnginePanel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_EnginePanel.createSequentialGroup().addGap(18).addComponent(label_1,
											GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_EnginePanel.createSequentialGroup().addGap(18).addComponent(lblX,
											GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
									.addComponent(label_9, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_EnginePanel.createSequentialGroup().addGap(18).addComponent(label_16,
											GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
									.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
							.addGap(42))
							.addGroup(Alignment.TRAILING,
									gl_EnginePanel.createSequentialGroup()
											.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(label_20).addGap(51)))
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE).addGap(106)));
			EnginePanel.setLayout(gl_EnginePanel);

			// Tab for weapons upgrades
			JPanel WeaponsPanel = new JPanel();
			WeaponsPanel.setBackground(Color.GRAY);
			tabbedPane.addTab("Weapons Module", null, WeaponsPanel, null);
			WeaponsPanel.setLayout(null);

			JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane_1.setBounds(10, 11, 1120, 395);
			tabbedPane_1.setBackground(new Color(3, 53, 132)); // change colour of all tabs
			tabbedPane_1.setFont(new Font("Tahoma", Font.BOLD, 19)); // change font
			tabbedPane_1.setForeground(Color.WHITE); // change text colour
			WeaponsPanel.add(tabbedPane_1);

			// Sub-tabbed panel for upgrading weapon module
			JPanel UpgradeWeaponModule = new JPanel();
			UpgradeWeaponModule.setBackground(Color.LIGHT_GRAY);
			tabbedPane_1.addTab("Upgrade Weapons Module", null, UpgradeWeaponModule, null);

			JLabel label_10 = new JLabel("Upgrade Weapon Module");
			label_10.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));

			JLabel label_11 = new JLabel(grapheneIcon);

			JLabel label_12 = new JLabel("Current Level: ~~");
			label_12.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_25 = new JLabel("Cost:");
			label_25.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_26 = new JLabel("x1000");
			label_26.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_27 = new JLabel(steelIcon);

			JLabel label_28 = new JLabel("x1000");
			label_28.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_48 = new JLabel(blastCrystalIcon);

			JLabel label_49 = new JLabel("x1000");
			label_49.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			button_3 = new JButton(buttonIcon);
			button_3.setFont(new Font("Tahoma", Font.PLAIN, 28));
			button_3.addActionListener(new UpgradeListener());

			GroupLayout gl_UpgradeWeaponModule = new GroupLayout(UpgradeWeaponModule);
			gl_UpgradeWeaponModule.setHorizontalGroup(gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGroup(gl_UpgradeWeaponModule
							.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(10).addComponent(label_12,
									GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(15).addComponent(label_10,
									GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(77)
									.addGroup(gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING, false)
											.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
													.addComponent(label_25, GroupLayout.PREFERRED_SIZE, 81,
															GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE))
											.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
													.addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
													.addComponent(label_27, GroupLayout.PREFERRED_SIZE, 59,
															GroupLayout.PREFERRED_SIZE)))
									.addGap(10)
									.addGroup(gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING)
											.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 655,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
													.addComponent(label_26, GroupLayout.PREFERRED_SIZE, 125,
															GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(label_11, GroupLayout.PREFERRED_SIZE, 61,
															GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(label_28, GroupLayout.PREFERRED_SIZE, 125,
															GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(label_48, GroupLayout.PREFERRED_SIZE, 61,
															GroupLayout.PREFERRED_SIZE)
													.addGap(9).addComponent(label_49, GroupLayout.PREFERRED_SIZE, 125,
															GroupLayout.PREFERRED_SIZE)))))
							.addContainerGap(243, Short.MAX_VALUE)));
			gl_UpgradeWeaponModule.setVerticalGroup(gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGroup(gl_UpgradeWeaponModule
							.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(11)
									.addComponent(label_12, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
									.addGap(46)
									.addComponent(label_10, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
									.addGap(7)
									.addGroup(gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING)
											.addComponent(label_48, GroupLayout.PREFERRED_SIZE, 50,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(18)
													.addComponent(label_25, GroupLayout.PREFERRED_SIZE, 20,
															GroupLayout.PREFERRED_SIZE))
											.addComponent(label_27, GroupLayout.PREFERRED_SIZE, 50,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(18)
													.addComponent(label_26, GroupLayout.PREFERRED_SIZE, 20,
															GroupLayout.PREFERRED_SIZE))
											.addComponent(label_11, GroupLayout.PREFERRED_SIZE, 50,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(18)
													.addComponent(label_28, GroupLayout.PREFERRED_SIZE, 20,
															GroupLayout.PREFERRED_SIZE))))
							.addGroup(gl_UpgradeWeaponModule.createSequentialGroup().addGap(161).addComponent(label_49)))
							.addGap(33).addComponent(button_3, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(56, Short.MAX_VALUE)));
			UpgradeWeaponModule.setLayout(gl_UpgradeWeaponModule);

			// Sub-tabbed panel for upgrading/purchasing individual weapons
			JPanel WeaponStats = new JPanel();
			WeaponStats.setBackground(Color.LIGHT_GRAY);
			tabbedPane_1.addTab("Weapon Stats", null, WeaponStats, null);

			JLabel lblNewLabel_3 = new JLabel(shieldJammerIcon);

			JLabel lblNewLabel_4 = new JLabel(laserIcon);

			JLabel lblNewLabel_5 = new JLabel(missileIcon);
			
			JLabel lblNewLabel_2 = new JLabel("New label");
			
			JLabel label_50 = new JLabel("New label");
			
			JLabel label_51 = new JLabel("New label");
			GroupLayout gl_WeaponStats = new GroupLayout(WeaponStats);
			gl_WeaponStats.setHorizontalGroup(
				gl_WeaponStats.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_WeaponStats.createSequentialGroup()
						.addGap(87)
						.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
						.addGap(90)
						.addComponent(lblNewLabel_5, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
						.addGap(95)
						.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
						.addGap(56))
					.addGroup(gl_WeaponStats.createSequentialGroup()
						.addGap(139)
						.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
						.addGap(187)
						.addComponent(label_50, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
						.addGap(235)
						.addComponent(label_51, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
						.addGap(128))
			);
			gl_WeaponStats.setVerticalGroup(
				gl_WeaponStats.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_WeaponStats.createSequentialGroup()
						.addGap(45)
						.addGroup(gl_WeaponStats.createParallelGroup(Alignment.LEADING)
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_WeaponStats.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_WeaponStats.createSequentialGroup()
								.addGap(55)
								.addGroup(gl_WeaponStats.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
									.addComponent(label_50, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_WeaponStats.createSequentialGroup()
								.addGap(51)
								.addComponent(label_51, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)))
						.addGap(47))
			);
			WeaponStats.setLayout(gl_WeaponStats);

			JPanel MiningPanel = new JPanel();
			MiningPanel.setBackground(Color.LIGHT_GRAY);
			tabbedPane.addTab("Mining Module", null, MiningPanel, null);

			JLabel label_2 = new JLabel("Current Level: ~~");
			label_2.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_3 = new JLabel("Upgrade Mining Equipment");
			label_3.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));

			JLabel label_5 = new JLabel(steelIcon);

			JLabel label_6 = new JLabel("Cost:");
			label_6.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_7 = new JLabel("x1000");
			label_7.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_8 = new JLabel(grapheneIcon);

			JLabel label_13 = new JLabel("x1000");
			label_13.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_14 = new JLabel(pyroxiumIcon);

			JLabel label_15 = new JLabel("x1000");
			label_15.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			button_1 = new JButton(buttonIcon);
			button_1.setFont(new Font("Tahoma", Font.PLAIN, 28));
			button_1.addActionListener(new UpgradeListener());

			GroupLayout gl_MiningPanel = new GroupLayout(MiningPanel);
			gl_MiningPanel.setHorizontalGroup(gl_MiningPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_MiningPanel.createSequentialGroup().addGap(10).addComponent(label_2,
							GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_MiningPanel.createSequentialGroup().addGap(77)
							.addGroup(gl_MiningPanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_MiningPanel.createSequentialGroup().addGap(71).addComponent(label_5,
											GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
									.addGroup(gl_MiningPanel.createSequentialGroup()
											.addComponent(label_6, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
											.addGap(49)))
							.addGap(10).addComponent(label_7, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE).addGap(7)
							.addComponent(label_8, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE).addGap(10)
							.addComponent(label_13, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE).addGap(42)
							.addComponent(label_14, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_15, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addGap(337))
					.addGroup(gl_MiningPanel.createSequentialGroup().addGap(220)
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(308, Short.MAX_VALUE))
					.addGroup(gl_MiningPanel.createSequentialGroup().addContainerGap()
							.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(846, Short.MAX_VALUE)));
			gl_MiningPanel.setVerticalGroup(gl_MiningPanel.createParallelGroup(Alignment.LEADING)
					.addGap(0, 417, Short.MAX_VALUE)
					.addGroup(gl_MiningPanel.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_MiningPanel.createParallelGroup(Alignment.TRAILING, false).addGroup(gl_MiningPanel
									.createSequentialGroup()
									.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
									.addGap(46)
									.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
									.addGap(7)
									.addGroup(gl_MiningPanel.createParallelGroup(Alignment.LEADING)
											.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 50,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_MiningPanel.createSequentialGroup().addGap(18).addComponent(
													label_6, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_MiningPanel.createSequentialGroup().addGap(18).addComponent(
													label_7, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
											.addComponent(label_8, GroupLayout.PREFERRED_SIZE, 50,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_MiningPanel.createSequentialGroup().addGap(18).addComponent(
													label_13, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
											.addComponent(label_14, GroupLayout.PREFERRED_SIZE, 50,
													GroupLayout.PREFERRED_SIZE))
									.addGap(42))
									.addGroup(gl_MiningPanel.createSequentialGroup().addComponent(label_15).addGap(51)))
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(106)));
			MiningPanel.setLayout(gl_MiningPanel);

			JPanel DeepSpaceViewerPanel = new JPanel();
			DeepSpaceViewerPanel.setBackground(Color.LIGHT_GRAY);
			tabbedPane.addTab("Deep Space Viewer", null, DeepSpaceViewerPanel, null);

			JLabel label_24 = new JLabel("Current Level: ~~");
			label_24.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_32 = new JLabel("Upgrade Deep Space Viewer");
			label_32.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));

			JLabel label_33 = new JLabel(steelIcon);

			JLabel label_34 = new JLabel("Cost:");
			label_34.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_35 = new JLabel("x1000");
			label_35.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_36 = new JLabel(grapheneIcon);

			JLabel label_37 = new JLabel("x1000");
			label_37.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_38 = new JLabel(intellectiumIcon);

			JLabel label_39 = new JLabel("x1000");
			label_39.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			button_5 = new JButton(buttonIcon);
			button_5.setFont(new Font("Tahoma", Font.PLAIN, 28));
			button_5.addActionListener(new UpgradeListener());

			GroupLayout gl_DeepSpaceViewerPanel = new GroupLayout(DeepSpaceViewerPanel);
			gl_DeepSpaceViewerPanel.setHorizontalGroup(gl_DeepSpaceViewerPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addGap(10).addComponent(label_24,
							GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addGap(77).addGroup(gl_DeepSpaceViewerPanel
							.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addGap(71).addComponent(label_33,
									GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
							.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
									.addComponent(label_34, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE).addGap(49)))
							.addGap(10).addComponent(label_35, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE).addGap(7)
							.addComponent(label_36, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE).addGap(10)
							.addComponent(label_37, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE).addGap(42)
							.addComponent(label_38, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_39, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addGap(337))
					.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addGap(220)
							.addComponent(button_5, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(308, Short.MAX_VALUE))
					.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addContainerGap()
							.addComponent(label_32, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(807, Short.MAX_VALUE)));
			gl_DeepSpaceViewerPanel.setVerticalGroup(gl_DeepSpaceViewerPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_DeepSpaceViewerPanel.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
											.addComponent(label_24, GroupLayout.PREFERRED_SIZE, 43,
													GroupLayout.PREFERRED_SIZE)
											.addGap(46)
											.addComponent(
													label_32, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
											.addGap(7)
											.addGroup(gl_DeepSpaceViewerPanel.createParallelGroup(Alignment.LEADING)
													.addComponent(label_33, GroupLayout.PREFERRED_SIZE, 50,
															GroupLayout.PREFERRED_SIZE)
													.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addGap(18)
															.addComponent(label_34, GroupLayout.PREFERRED_SIZE, 20,
																	GroupLayout.PREFERRED_SIZE))
													.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addGap(18)
															.addComponent(label_35, GroupLayout.PREFERRED_SIZE, 20,
																	GroupLayout.PREFERRED_SIZE))
													.addComponent(label_36, GroupLayout.PREFERRED_SIZE, 50,
															GroupLayout.PREFERRED_SIZE)
													.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addGap(18)
															.addComponent(label_37, GroupLayout.PREFERRED_SIZE, 20,
																	GroupLayout.PREFERRED_SIZE))
													.addComponent(label_38, GroupLayout.PREFERRED_SIZE, 50,
															GroupLayout.PREFERRED_SIZE))
											.addGap(42))
									.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup().addComponent(label_39)
											.addGap(51)))
							.addComponent(button_5, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(106)));
			DeepSpaceViewerPanel.setLayout(gl_DeepSpaceViewerPanel);

			JPanel ShieldPanel = new JPanel();
			ShieldPanel.setBackground(Color.LIGHT_GRAY);
			tabbedPane.addTab("Shield Module", null, ShieldPanel, null);

			JLabel label_17 = new JLabel("Current Level: ~~");
			label_17.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_18 = new JLabel("Upgrade Shields");
			label_18.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));

			JLabel label_19 = new JLabel(steelIcon);

			JLabel label_21 = new JLabel("Cost:");
			label_21.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_22 = new JLabel("x1000");
			label_22.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_23 = new JLabel(grapheneIcon);

			JLabel label_29 = new JLabel("x1000");
			label_29.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_30 = new JLabel(starliteIcon);

			JLabel label_31 = new JLabel("x1000");
			label_31.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			button_2 = new JButton(buttonIcon);
			button_2.setFont(new Font("Tahoma", Font.PLAIN, 28));
			button_2.addActionListener(new UpgradeListener());

			GroupLayout gl_ShieldPanel = new GroupLayout(ShieldPanel);
			gl_ShieldPanel.setHorizontalGroup(gl_ShieldPanel.createParallelGroup(Alignment.LEADING)
					.addGap(0, 1183, Short.MAX_VALUE).addGap(0, 1183, Short.MAX_VALUE)
					.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(10).addComponent(label_17,
							GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(15).addComponent(label_18,
							GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(77)
							.addGroup(gl_ShieldPanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(71).addComponent(label_19,
											GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
									.addGroup(gl_ShieldPanel.createSequentialGroup()
											.addComponent(label_21, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
											.addGap(49)))
							.addGap(10).addComponent(label_22, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE).addGap(7)
							.addComponent(label_23, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE).addGap(10)
							.addComponent(label_29, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE).addGap(42)
							.addComponent(label_30, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_31, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addGap(337))
					.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(220)
							.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(308, Short.MAX_VALUE)));
			gl_ShieldPanel.setVerticalGroup(gl_ShieldPanel.createParallelGroup(Alignment.LEADING)
					.addGap(0, 417, Short.MAX_VALUE).addGap(0, 417, Short.MAX_VALUE)
					.addGroup(gl_ShieldPanel.createSequentialGroup().addContainerGap(11, Short.MAX_VALUE)
							.addGroup(gl_ShieldPanel.createParallelGroup(Alignment.TRAILING, false).addGroup(gl_ShieldPanel
									.createSequentialGroup()
									.addComponent(label_17, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
									.addGap(46)
									.addComponent(label_18, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
									.addGap(7)
									.addGroup(gl_ShieldPanel.createParallelGroup(Alignment.LEADING)
											.addComponent(label_19, GroupLayout.PREFERRED_SIZE, 50,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(18).addComponent(
													label_21, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(18).addComponent(
													label_22, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
											.addComponent(label_23, GroupLayout.PREFERRED_SIZE, 50,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_ShieldPanel.createSequentialGroup().addGap(18).addComponent(
													label_29, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
											.addComponent(label_30, GroupLayout.PREFERRED_SIZE, 50,
													GroupLayout.PREFERRED_SIZE))
									.addGap(42))
									.addGroup(gl_ShieldPanel.createSequentialGroup().addComponent(label_31).addGap(51)))
							.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(106)));
			ShieldPanel.setLayout(gl_ShieldPanel);

			// Tab to repair ship
			JPanel RepairPanel = new JPanel();
			RepairPanel.setBackground(Color.LIGHT_GRAY);
			RepairPanel.setForeground(Color.BLACK);
			tabbedPane.addTab("Repair Ship", null, RepairPanel, null);

			JButton btnRepairShipTo = new JButton(repairIcon);
			btnRepairShipTo.setFont(new Font("Tw Cen MT", Font.BOLD, 24));
			btnRepairShipTo.addActionListener(new UpgradeListener());

			JLabel lblCost = new JLabel("Cost:");
			lblCost.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel lblNewLabel = new JLabel("Current Ship Health: ~~~");
			lblNewLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));

			JLabel lblMaximumShipHealth = new JLabel("Maximum Ship Health: ~~~");
			lblMaximumShipHealth.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));

			JLabel label_40 = new JLabel(steelIcon);

			JLabel label_41 = new JLabel("x1000");
			label_41.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_42 = new JLabel(grapheneIcon);

			JLabel label_43 = new JLabel("x1000");
			label_43.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_44 = new JLabel(starliteIcon);

			JLabel label_45 = new JLabel("x1000");
			label_45.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));

			JLabel label_46 = new JLabel(blastCrystalIcon);

			JLabel label_47 = new JLabel("x1000");
			label_47.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
			GroupLayout gl_RepairPanel = new GroupLayout(RepairPanel);
			gl_RepairPanel.setHorizontalGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_RepairPanel.createSequentialGroup().addGap(54)
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE).addGap(104)
							.addComponent(lblMaximumShipHealth, GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE).addGap(252))
					.addGroup(gl_RepairPanel.createSequentialGroup().addGap(89)
							.addComponent(btnRepairShipTo, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
							.addGap(71).addComponent(lblCost, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_RepairPanel
									.createSequentialGroup()
									.addComponent(label_40, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE).addGap(10)
									.addComponent(label_41, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE).addGap(7)
									.addComponent(label_42, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE).addGap(10)
									.addComponent(label_43, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
									.addGroup(gl_RepairPanel.createSequentialGroup()
											.addComponent(label_44, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
											.addGap(10)
											.addComponent(label_45, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
											.addGap(7).addComponent(label_46, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
											.addGap(10)
											.addComponent(label_47, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)))));
			gl_RepairPanel.setVerticalGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_RepairPanel
					.createSequentialGroup().addGap(62)
					.addGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblMaximumShipHealth, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addGap(64)
					.addGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
							.addComponent(btnRepairShipTo, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_RepairPanel.createSequentialGroup()
									.addGroup(gl_RepairPanel.createParallelGroup(Alignment.TRAILING)
											.addGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
													.addComponent(label_40, GroupLayout.PREFERRED_SIZE, 50,
															GroupLayout.PREFERRED_SIZE)
													.addGroup(gl_RepairPanel.createSequentialGroup().addGap(18)
															.addComponent(label_41))
													.addComponent(label_42, GroupLayout.PREFERRED_SIZE, 50,
															GroupLayout.PREFERRED_SIZE)
													.addGroup(gl_RepairPanel.createSequentialGroup().addGap(18)
															.addComponent(label_43)))
											.addComponent(lblCost, GroupLayout.PREFERRED_SIZE, 43,
													GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
											.addComponent(label_44, GroupLayout.PREFERRED_SIZE, 50,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_RepairPanel.createSequentialGroup().addGap(18)
													.addComponent(label_45))
											.addComponent(label_46, GroupLayout.PREFERRED_SIZE, 50,
													GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_RepairPanel.createSequentialGroup().addGap(18)
													.addComponent(label_47)))))));
			RepairPanel.setLayout(gl_RepairPanel);
			setLayout(groupLayout);

		}

		class UpgradeListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == button) { //engine upgrade

				}else if (e.getSource() == button_1) { //mining upgrade
					
				}else if (e.getSource() == button_2) { //shields upgrade
					
				}else if (e.getSource() == button_3) { //weapons upgrade
					
				}else if (e.getSource() == button_5) { //deep space viewer upgrade
					
				}
			}
		}
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new DepotPanel(frame));
		frame.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
