import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
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
import javax.swing.Icon;

public class UpgradePanel extends JPanel {

	/**
	 * Create the panel.
	 */
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
		ImageIcon purchaseIcon = null;
		ImageIcon shieldIcon = null;
		ImageIcon repairKitIcon = null;
		ImageIcon shieldJammerIcon = null;
		ImageIcon laserIcon = null;
		ImageIcon missileIcon = null;
		
		try { //loading images
			blastCrystalIcon = new ImageIcon(ImageIO.read(new File("BlastCrystal.png")));
			grapheneIcon = new ImageIcon(ImageIO.read(new File("Graphene.png")));
			intellectiumIcon = new ImageIcon(ImageIO.read(new File("I.png")));
			plutoniumIcon = new ImageIcon(ImageIO.read(new File("Plutonium.png")));
			pyroxiumIcon = new ImageIcon(ImageIO.read(new File("Pyroxium.png")));
			starliteIcon = new ImageIcon(ImageIO.read(new File("Starlite.png")));
			steelIcon = new ImageIcon(ImageIO.read(new File("Steel.png")));
			buttonIcon = new ImageIcon(ImageIO.read(new File("Button.png")));
			repairIcon = new ImageIcon(ImageIO.read(new File("RepairButton.png")));
			purchaseIcon = new ImageIcon(ImageIO.read(new File("PurchaseButton.png")));
			shieldIcon = new ImageIcon(ImageIO.read(new File("ShieldSupercharger.png")));
			repairKitIcon = new ImageIcon(ImageIO.read(new File("RepairKit.png")));
			shieldJammerIcon = new ImageIcon(ImageIO.read(new File("ShieldJammer.png")));
			laserIcon = new ImageIcon(ImageIO.read(new File("Laser.png")));
			missileIcon = new ImageIcon(ImageIO.read(new File("MissileLauncher.png")));
			
		} catch (Exception ex) {
			System.out.println("image didn't load");
		}
		UIManager.put("TabbedPane.selected", new Color(0, 183, 229)); //change tab to white when it is selected
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
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(lblUpgradeShip, GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 1223, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblUpgradeShip)
					.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE))
		);
		
		tabbedPane.setBackground(new Color(3, 53, 132)); //change colour of all tabs
		
		//Tab for engine upgrades
		JPanel EnginePanel = new JPanel();
		EnginePanel.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Engine Module", null, EnginePanel, null);
		
		JLabel label = new JLabel("Current Level: ~~");
		label.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JButton button = new JButton("");
		button.setFont(new Font("Tahoma", Font.PLAIN, 28));
		button.setIcon(buttonIcon);
		
		JLabel label_1 = new JLabel("Cost:");
		label_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel lblUpgradeEngines = new JLabel("Upgrade Engines");
		lblUpgradeEngines.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));
		
		JLabel lblNewLabel_1 = new JLabel(steelIcon);
		
		JLabel label_9 = new JLabel(plutoniumIcon);
		
		JLabel lblX = new JLabel("x1000");
		lblX.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_16 = new JLabel("x1000");
		label_16.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		GroupLayout gl_EnginePanel = new GroupLayout(EnginePanel);
		gl_EnginePanel.setHorizontalGroup(
			gl_EnginePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_EnginePanel.createSequentialGroup()
					.addGap(10)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_EnginePanel.createSequentialGroup()
					.addGap(15)
					.addComponent(lblUpgradeEngines, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_EnginePanel.createSequentialGroup()
					.addGap(77)
					.addGroup(gl_EnginePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_EnginePanel.createSequentialGroup()
							.addGap(71)
							.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
						.addGroup(gl_EnginePanel.createSequentialGroup()
							.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
							.addGap(49)))
					.addGap(10)
					.addComponent(lblX, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(7)
					.addComponent(label_9, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(label_16, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(595))
				.addGroup(gl_EnginePanel.createSequentialGroup()
					.addGap(227)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE))
		);
		gl_EnginePanel.setVerticalGroup(
			gl_EnginePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_EnginePanel.createSequentialGroup()
					.addGap(11)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(46)
					.addComponent(lblUpgradeEngines, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addGroup(gl_EnginePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_EnginePanel.createSequentialGroup()
							.addGap(18)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_EnginePanel.createSequentialGroup()
							.addGap(18)
							.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_9, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_EnginePanel.createSequentialGroup()
							.addGap(18)
							.addComponent(label_16, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
					.addGap(69)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
		);
		EnginePanel.setLayout(gl_EnginePanel);
		
		//Tab for Deep Space Viewer upgrades
		JPanel DeepSpaceViewerPanel = new JPanel();
		DeepSpaceViewerPanel.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Deep Space Viewer", null, DeepSpaceViewerPanel, null);
		
		JButton button_1 = new JButton("");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 28));
		button_1.setIcon(buttonIcon);
		
		JLabel lblUpgradeDeepSpace = new JLabel("Upgrade Deep Space Viewer");
		lblUpgradeDeepSpace.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));
		
		JLabel label_2 = new JLabel(grapheneIcon);
		
		JLabel label_3 = new JLabel("Current Level: ~~");
		label_3.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_5 = new JLabel("Cost:");
		label_5.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_17 = new JLabel("x1000");
		label_17.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_18 = new JLabel(steelIcon);
		
		JLabel label_19 = new JLabel("x1000");
		label_19.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		GroupLayout gl_DeepSpaceViewerPanel = new GroupLayout(DeepSpaceViewerPanel);
		gl_DeepSpaceViewerPanel.setHorizontalGroup(
			gl_DeepSpaceViewerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
					.addGap(10)
					.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
					.addGap(15)
					.addComponent(lblUpgradeDeepSpace, GroupLayout.PREFERRED_SIZE, 382, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
					.addGap(77)
					.addGroup(gl_DeepSpaceViewerPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
							.addComponent(label_5, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
							.addGap(49))
						.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
							.addGap(71)
							.addComponent(label_18, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
					.addGap(10)
					.addComponent(label_17, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(7)
					.addComponent(label_2, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(label_19, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(595))
				.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
					.addGap(227)
					.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE))
		);
		gl_DeepSpaceViewerPanel.setVerticalGroup(
			gl_DeepSpaceViewerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
					.addGap(11)
					.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(46)
					.addComponent(lblUpgradeDeepSpace, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addGroup(gl_DeepSpaceViewerPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
							.addGap(18)
							.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_18, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
							.addGap(18)
							.addComponent(label_17, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_DeepSpaceViewerPanel.createSequentialGroup()
							.addGap(18)
							.addComponent(label_19, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
					.addGap(69)
					.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
		);
		DeepSpaceViewerPanel.setLayout(gl_DeepSpaceViewerPanel);
		
		//Tab for Shield Upgrades
		JPanel ShieldPanel = new JPanel();
		ShieldPanel.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Shield Module", null, ShieldPanel, null);
		
		JButton button_2 = new JButton("");
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 28));
		button_2.setIcon(buttonIcon);
		
		JLabel lblUpgradeShields = new JLabel("Upgrade Shields");
		lblUpgradeShields.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));
		
		JLabel label_6 = new JLabel(starliteIcon);
		
		JLabel label_7 = new JLabel("Current Level: ~~");
		label_7.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_8 = new JLabel("Cost:");
		label_8.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_21 = new JLabel("x1000");
		label_21.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_22 = new JLabel(steelIcon);
		
		JLabel label_23 = new JLabel("x1000");
		label_23.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		GroupLayout gl_ShieldPanel = new GroupLayout(ShieldPanel);
		gl_ShieldPanel.setHorizontalGroup(
			gl_ShieldPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ShieldPanel.createSequentialGroup()
					.addGap(10)
					.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_ShieldPanel.createSequentialGroup()
					.addGap(15)
					.addComponent(lblUpgradeShields, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_ShieldPanel.createSequentialGroup()
					.addGap(77)
					.addGroup(gl_ShieldPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_ShieldPanel.createSequentialGroup()
							.addComponent(label_8, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
							.addGap(49))
						.addGroup(gl_ShieldPanel.createSequentialGroup()
							.addGap(71)
							.addComponent(label_22, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
					.addGap(10)
					.addComponent(label_21, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(7)
					.addComponent(label_6, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(label_23, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(595))
				.addGroup(gl_ShieldPanel.createSequentialGroup()
					.addGap(227)
					.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE))
		);
		gl_ShieldPanel.setVerticalGroup(
			gl_ShieldPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ShieldPanel.createSequentialGroup()
					.addGap(11)
					.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(46)
					.addComponent(lblUpgradeShields, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addGroup(gl_ShieldPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_ShieldPanel.createSequentialGroup()
							.addGap(18)
							.addComponent(label_8, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_22, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_ShieldPanel.createSequentialGroup()
							.addGap(18)
							.addComponent(label_21, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_ShieldPanel.createSequentialGroup()
							.addGap(18)
							.addComponent(label_23, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
					.addGap(69)
					.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
		);
		ShieldPanel.setLayout(gl_ShieldPanel);
		
		//Tab for weapons upgrades
		JPanel WeaponsPanel = new JPanel();
		WeaponsPanel.setBackground(Color.GRAY);
		tabbedPane.addTab("Weapons Module", null, WeaponsPanel, null);
		WeaponsPanel.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 11, 1120, 395);
		tabbedPane_1.setBackground(new Color(3, 53, 132)); //change colour of all tabs
		tabbedPane_1.setFont(new Font("Tahoma", Font.BOLD, 19)); //change font
		tabbedPane_1.setForeground(Color.WHITE); //change text colour
		WeaponsPanel.add(tabbedPane_1);
		
		//Sub-tabbed panel for upgrading weapon module
		JPanel UpgradeWeaponModule = new JPanel();
		UpgradeWeaponModule.setBackground(Color.LIGHT_GRAY);
		tabbedPane_1.addTab("Upgrade Weapons Module", null, UpgradeWeaponModule, null);
		
		JButton button_3 = new JButton(buttonIcon);
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 28));
		
		JLabel label_10 = new JLabel("Upgrade Weapon Module");
		label_10.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));
		
		JLabel label_11 = new JLabel(blastCrystalIcon);
		
		JLabel label_12 = new JLabel("Current Level: ~~");
		label_12.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_25 = new JLabel("Cost:");
		label_25.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_26 = new JLabel("x1000");
		label_26.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_27 = new JLabel(steelIcon);
		
		JLabel label_28 = new JLabel("x1000");
		label_28.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		GroupLayout gl_UpgradeWeaponModule = new GroupLayout(UpgradeWeaponModule);
		gl_UpgradeWeaponModule.setHorizontalGroup(
			gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
					.addGap(10)
					.addComponent(label_12, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
					.addGap(15)
					.addComponent(label_10, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
					.addGap(77)
					.addGroup(gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
							.addComponent(label_25, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
							.addGap(49))
						.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
							.addGap(71)
							.addComponent(label_27, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
					.addGap(10)
					.addComponent(label_26, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(7)
					.addComponent(label_11, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(label_28, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(570))
				.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
					.addGap(227)
					.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE))
		);
		gl_UpgradeWeaponModule.setVerticalGroup(
			gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
					.addGap(11)
					.addComponent(label_12, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(46)
					.addComponent(label_10, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addGroup(gl_UpgradeWeaponModule.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
							.addGap(18)
							.addComponent(label_25, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_27, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
							.addGap(18)
							.addComponent(label_26, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_11, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_UpgradeWeaponModule.createSequentialGroup()
							.addGap(18)
							.addComponent(label_28, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
					.addGap(69)
					.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
		);
		UpgradeWeaponModule.setLayout(gl_UpgradeWeaponModule);
		
		//Sub-tabbed panel for upgrading/purchasing individual weapons
		JPanel PurchaseWeapons = new JPanel();
		PurchaseWeapons.setBackground(Color.LIGHT_GRAY);
		tabbedPane_1.addTab("Purchase Weapons", null, PurchaseWeapons, null);
		
		JLabel lblNewLabel_3 = new JLabel(shieldJammerIcon);
		
		JLabel lblNewLabel_4 = new JLabel(laserIcon);
		
		JLabel lblNewLabel_5 = new JLabel(missileIcon);
		GroupLayout gl_PurchaseWeapons = new GroupLayout(PurchaseWeapons);
		gl_PurchaseWeapons.setHorizontalGroup(
			gl_PurchaseWeapons.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PurchaseWeapons.createSequentialGroup()
					.addGap(87)
					.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
					.addGap(90)
					.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_PurchaseWeapons.createSequentialGroup()
					.addGap(286)
					.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE))
		);
		gl_PurchaseWeapons.setVerticalGroup(
			gl_PurchaseWeapons.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PurchaseWeapons.createSequentialGroup()
					.addGap(45)
					.addGroup(gl_PurchaseWeapons.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PurchaseWeapons.createSequentialGroup()
							.addGap(9)
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
					.addGap(64)
					.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
		);
		PurchaseWeapons.setLayout(gl_PurchaseWeapons);
		
		//Tab for mining upgrades
		JPanel MiningPanel = new JPanel();
		MiningPanel.setForeground(Color.BLACK);
		MiningPanel.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Mining Module", null, MiningPanel, null);
		
		JButton button_4 = new JButton("");
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 28));
		button_4.setIcon(buttonIcon);
		
		JLabel lblUpgradeMiningEquipment = new JLabel("Upgrade Mining Equipment");
		lblUpgradeMiningEquipment.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));
		
		JLabel label_13 = new JLabel(pyroxiumIcon);
		
		JLabel label_14 = new JLabel("Current Level: ~~");
		label_14.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_15 = new JLabel("Cost:");
		label_15.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_29 = new JLabel("x1000");
		label_29.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel label_30 = new JLabel(steelIcon);
		
		JLabel label_31 = new JLabel("x1000");
		label_31.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		GroupLayout gl_MiningPanel = new GroupLayout(MiningPanel);
		gl_MiningPanel.setHorizontalGroup(
			gl_MiningPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_MiningPanel.createSequentialGroup()
					.addGap(10)
					.addComponent(label_14, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_MiningPanel.createSequentialGroup()
					.addGap(15)
					.addComponent(lblUpgradeMiningEquipment, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_MiningPanel.createSequentialGroup()
					.addGap(77)
					.addGroup(gl_MiningPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_MiningPanel.createSequentialGroup()
							.addComponent(label_15, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
							.addGap(49))
						.addGroup(gl_MiningPanel.createSequentialGroup()
							.addGap(71)
							.addComponent(label_30, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
					.addGap(10)
					.addComponent(label_29, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(7)
					.addComponent(label_13, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(label_31, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(595))
				.addGroup(gl_MiningPanel.createSequentialGroup()
					.addGap(227)
					.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 655, GroupLayout.PREFERRED_SIZE))
		);
		gl_MiningPanel.setVerticalGroup(
			gl_MiningPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_MiningPanel.createSequentialGroup()
					.addGap(11)
					.addComponent(label_14, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(46)
					.addComponent(lblUpgradeMiningEquipment, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addGroup(gl_MiningPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_MiningPanel.createSequentialGroup()
							.addGap(18)
							.addComponent(label_15, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_30, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_MiningPanel.createSequentialGroup()
							.addGap(18)
							.addComponent(label_29, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_13, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_MiningPanel.createSequentialGroup()
							.addGap(18)
							.addComponent(label_31, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
					.addGap(69)
					.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
		);
		MiningPanel.setLayout(gl_MiningPanel);
		
		//Tab to repair ship
		JPanel RepairPanel = new JPanel();
		RepairPanel.setBackground(Color.LIGHT_GRAY);
		RepairPanel.setForeground(Color.BLACK);
		tabbedPane.addTab("Repair Ship", null, RepairPanel, null);
		
		JButton btnRepairShipTo = new JButton(repairIcon);
		btnRepairShipTo.setFont(new Font("Tw Cen MT", Font.BOLD, 24));
		
		JLabel lblCost = new JLabel("Cost:");
		lblCost.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		
		JLabel lblNewLabel = new JLabel("Current Ship Health: ~~~");
		lblNewLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));
		
		JLabel lblMaximumShipHealth = new JLabel("Maximum Ship Health: ~~~");
		lblMaximumShipHealth.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));
		GroupLayout gl_RepairPanel = new GroupLayout(RepairPanel);
		gl_RepairPanel.setHorizontalGroup(
			gl_RepairPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_RepairPanel.createSequentialGroup()
					.addGap(54)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
					.addGap(104)
					.addComponent(lblMaximumShipHealth, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
					.addGap(252))
				.addGroup(gl_RepairPanel.createSequentialGroup()
					.addGap(89)
					.addComponent(btnRepairShipTo, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
					.addGap(71)
					.addComponent(lblCost, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
					.addGap(470))
		);
		gl_RepairPanel.setVerticalGroup(
			gl_RepairPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_RepairPanel.createSequentialGroup()
					.addGap(62)
					.addGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMaximumShipHealth, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addGap(64)
					.addGroup(gl_RepairPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnRepairShipTo, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCost, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)))
		);
		RepairPanel.setLayout(gl_RepairPanel);
		setLayout(groupLayout);
		
	}
	
	class backButtonListener implements ActionListener{
	    public void actionPerformed(ActionEvent e){
	    	
	    }
	  }
	
	public static void main(String args[]) {
		JFrame frame = new JFrame("hello");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new UpgradePanel());
        frame.pack();
		frame.setVisible(true);
	}
}
