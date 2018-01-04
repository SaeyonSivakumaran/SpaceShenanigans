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

public class UpgradePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public UpgradePanel() {
		ImageIcon buttonIcon = null;
		ImageIcon blastCrystalIcon = null;
		ImageIcon grapheneIcon = null;
		ImageIcon plutoniumIcon = null;
		ImageIcon pyroxiumIcon = null;
		ImageIcon starliteIcon = null;
		ImageIcon steelIcon = null;
		ImageIcon repairIcon = null;
		ImageIcon purchaseIcon = null;
		
		try { //loading images
			blastCrystalIcon = new ImageIcon(ImageIO.read(new File("BlastCrystal.png")));
			grapheneIcon = new ImageIcon(ImageIO.read(new File("Graphene.png")));
			plutoniumIcon = new ImageIcon(ImageIO.read(new File("Plutonium.png")));
			pyroxiumIcon = new ImageIcon(ImageIO.read(new File("Pyroxium.png")));
			starliteIcon = new ImageIcon(ImageIO.read(new File("Starlite.png")));
			steelIcon = new ImageIcon(ImageIO.read(new File("Steel.png")));
			buttonIcon = new ImageIcon(ImageIO.read(new File("Button.png")));
			repairIcon = new ImageIcon(ImageIO.read(new File("RepairButton.png")));
			purchaseIcon = new ImageIcon(ImageIO.read(new File("PurchaseButton.png")));
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
		EnginePanel.setLayout(null);
		
		JLabel label = new JLabel("Current Level: ~~");
		label.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label.setBounds(10, 11, 227, 43);
		EnginePanel.add(label);
		
		JButton button = new JButton("");
		button.setFont(new Font("Tahoma", Font.PLAIN, 28));
		button.setBounds(227, 269, 655, 69);
		button.setIcon(buttonIcon);
		EnginePanel.add(button);
		
		JLabel label_1 = new JLabel("Cost:");
		label_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_1.setBounds(77, 168, 72, 20);
		EnginePanel.add(label_1);
		
		JLabel label_4 = new JLabel("Time: min");
		label_4.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_4.setBounds(692, 157, 257, 43);
		EnginePanel.add(label_4);
		
		JLabel lblUpgradeEngines = new JLabel("Upgrade Engines");
		lblUpgradeEngines.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));
		lblUpgradeEngines.setBounds(15, 100, 227, 43);
		EnginePanel.add(lblUpgradeEngines);
		
		JLabel lblNewLabel_1 = new JLabel(steelIcon);
		lblNewLabel_1.setBounds(148, 150, 50, 50);
		EnginePanel.add(lblNewLabel_1);
		
		JLabel label_9 = new JLabel(plutoniumIcon);
		label_9.setBounds(331, 150, 50, 50);
		EnginePanel.add(label_9);
		
		JLabel lblX = new JLabel("x1000");
		lblX.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		lblX.setBounds(208, 168, 116, 20);
		EnginePanel.add(lblX);
		
		JLabel label_16 = new JLabel("x1000");
		label_16.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_16.setBounds(391, 168, 116, 20);
		EnginePanel.add(label_16);
		
		//Tab for Deep Space Viewer upgrades
		JPanel DeepSpaceViewerPanel = new JPanel();
		DeepSpaceViewerPanel.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Deep Space Viewer", null, DeepSpaceViewerPanel, null);
		DeepSpaceViewerPanel.setLayout(null);
		
		JButton button_1 = new JButton("");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 28));
		button_1.setBounds(227, 269, 655, 69);
		button_1.setIcon(buttonIcon);
		DeepSpaceViewerPanel.add(button_1);
		
		JLabel lblUpgradeDeepSpace = new JLabel("Upgrade Deep Space Viewer");
		lblUpgradeDeepSpace.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));
		lblUpgradeDeepSpace.setBounds(15, 100, 382, 43);
		DeepSpaceViewerPanel.add(lblUpgradeDeepSpace);
		
		JLabel label_2 = new JLabel(grapheneIcon);
		label_2.setBounds(331, 150, 50, 50);
		DeepSpaceViewerPanel.add(label_2);
		
		JLabel label_3 = new JLabel("Current Level: ~~");
		label_3.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_3.setBounds(10, 11, 227, 43);
		DeepSpaceViewerPanel.add(label_3);
		
		JLabel label_5 = new JLabel("Cost:");
		label_5.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_5.setBounds(77, 168, 72, 20);
		DeepSpaceViewerPanel.add(label_5);
		
		JLabel label_17 = new JLabel("x1000");
		label_17.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_17.setBounds(208, 168, 116, 20);
		DeepSpaceViewerPanel.add(label_17);
		
		JLabel label_18 = new JLabel(steelIcon);
		label_18.setBounds(148, 150, 50, 50);
		DeepSpaceViewerPanel.add(label_18);
		
		JLabel label_19 = new JLabel("x1000");
		label_19.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_19.setBounds(391, 168, 116, 20);
		DeepSpaceViewerPanel.add(label_19);
		
		JLabel label_20 = new JLabel("Time: min");
		label_20.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_20.setBounds(692, 157, 257, 43);
		DeepSpaceViewerPanel.add(label_20);
		
		//Tab for Shield Upgrades
		JPanel ShieldPanel = new JPanel();
		ShieldPanel.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Shield Module", null, ShieldPanel, null);
		ShieldPanel.setLayout(null);
		
		JButton button_2 = new JButton("");
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 28));
		button_2.setBounds(227, 269, 655, 69);
		button_2.setIcon(buttonIcon);
		ShieldPanel.add(button_2);
		
		JLabel lblUpgradeShields = new JLabel("Upgrade Shields");
		lblUpgradeShields.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));
		lblUpgradeShields.setBounds(15, 100, 227, 43);
		ShieldPanel.add(lblUpgradeShields);
		
		JLabel label_6 = new JLabel(starliteIcon);
		label_6.setBounds(331, 150, 50, 50);
		ShieldPanel.add(label_6);
		
		JLabel label_7 = new JLabel("Current Level: ~~");
		label_7.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_7.setBounds(10, 11, 227, 43);
		ShieldPanel.add(label_7);
		
		JLabel label_8 = new JLabel("Cost:");
		label_8.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_8.setBounds(77, 168, 72, 20);
		ShieldPanel.add(label_8);
		
		JLabel label_21 = new JLabel("x1000");
		label_21.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_21.setBounds(208, 168, 116, 20);
		ShieldPanel.add(label_21);
		
		JLabel label_22 = new JLabel(steelIcon);
		label_22.setBounds(148, 150, 50, 50);
		ShieldPanel.add(label_22);
		
		JLabel label_23 = new JLabel("x1000");
		label_23.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_23.setBounds(391, 168, 116, 20);
		ShieldPanel.add(label_23);
		
		JLabel label_24 = new JLabel("Time: min");
		label_24.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_24.setBounds(692, 157, 257, 43);
		ShieldPanel.add(label_24);
		
		//Tab for weapons upgrades
		JPanel WeaponsPanel = new JPanel();
		WeaponsPanel.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Weapons Module", null, WeaponsPanel, null);
		WeaponsPanel.setLayout(null);
		
		JButton button_3 = new JButton("");
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 28));
		button_3.setBounds(227, 269, 655, 69);
		button_3.setIcon(buttonIcon);
		WeaponsPanel.add(button_3);
		
		JLabel lblUpgradeWeaponModule = new JLabel("Upgrade Weapon Module");
		lblUpgradeWeaponModule.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));
		lblUpgradeWeaponModule.setBounds(15, 100, 334, 43);
		WeaponsPanel.add(lblUpgradeWeaponModule);
		
		JLabel label_10 = new JLabel(blastCrystalIcon);
		label_10.setBounds(331, 150, 50, 50);
		WeaponsPanel.add(label_10);
		
		JLabel label_11 = new JLabel("Current Level: ~~");
		label_11.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_11.setBounds(10, 11, 227, 43);
		WeaponsPanel.add(label_11);
		
		JLabel label_12 = new JLabel("Cost:");
		label_12.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_12.setBounds(77, 168, 72, 20);
		WeaponsPanel.add(label_12);
		
		JLabel label_25 = new JLabel("x1000");
		label_25.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_25.setBounds(208, 168, 116, 20);
		WeaponsPanel.add(label_25);
		
		JLabel label_26 = new JLabel(steelIcon);
		label_26.setBounds(148, 150, 50, 50);
		WeaponsPanel.add(label_26);
		
		JLabel label_27 = new JLabel("x1000");
		label_27.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_27.setBounds(391, 168, 116, 20);
		WeaponsPanel.add(label_27);
		
		JLabel label_28 = new JLabel("Time: min");
		label_28.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_28.setBounds(692, 157, 257, 43);
		WeaponsPanel.add(label_28);
		
		//Tab for mining upgrades
		JPanel MiningPanel = new JPanel();
		MiningPanel.setForeground(Color.BLACK);
		MiningPanel.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Mining Module", null, MiningPanel, null);
		MiningPanel.setLayout(null);
		
		JButton button_4 = new JButton("");
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 28));
		button_4.setBounds(227, 269, 655, 69);
		button_4.setIcon(buttonIcon);
		MiningPanel.add(button_4);
		
		JLabel lblUpgradeMiningEquipment = new JLabel("Upgrade Mining Equipment");
		lblUpgradeMiningEquipment.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));
		lblUpgradeMiningEquipment.setBounds(15, 100, 420, 43);
		MiningPanel.add(lblUpgradeMiningEquipment);
		
		JLabel label_13 = new JLabel(pyroxiumIcon);
		label_13.setBounds(331, 150, 50, 50);
		MiningPanel.add(label_13);
		
		JLabel label_14 = new JLabel("Current Level: ~~");
		label_14.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_14.setBounds(10, 11, 227, 43);
		MiningPanel.add(label_14);
		
		JLabel label_15 = new JLabel("Cost:");
		label_15.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_15.setBounds(77, 168, 72, 20);
		MiningPanel.add(label_15);
		
		JLabel label_29 = new JLabel("x1000");
		label_29.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_29.setBounds(208, 168, 116, 20);
		MiningPanel.add(label_29);
		
		JLabel label_30 = new JLabel(steelIcon);
		label_30.setBounds(148, 150, 50, 50);
		MiningPanel.add(label_30);
		
		JLabel label_31 = new JLabel("x1000");
		label_31.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_31.setBounds(391, 168, 116, 20);
		MiningPanel.add(label_31);
		
		JLabel label_32 = new JLabel("Time: min");
		label_32.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_32.setBounds(692, 157, 257, 43);
		MiningPanel.add(label_32);
		
		//Tab to repair ship
		JPanel RepairPanel = new JPanel();
		RepairPanel.setBackground(Color.LIGHT_GRAY);
		RepairPanel.setForeground(Color.BLACK);
		tabbedPane.addTab("Repair Ship", null, RepairPanel, null);
		RepairPanel.setLayout(null);
		
		JButton btnRepairShipTo = new JButton(repairIcon);
		btnRepairShipTo.setFont(new Font("Tw Cen MT", Font.BOLD, 24));
		btnRepairShipTo.setBounds(89, 169, 400, 150);
		RepairPanel.add(btnRepairShipTo);
		
		JLabel lblCost = new JLabel("Cost:");
		lblCost.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		lblCost.setBounds(560, 169, 72, 43);
		RepairPanel.add(lblCost);
		
		JLabel lblNewLabel = new JLabel("Current Ship Health: ~~~");
		lblNewLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));
		lblNewLabel.setBounds(54, 62, 346, 43);
		RepairPanel.add(lblNewLabel);
		
		JLabel lblTimeMin = new JLabel("Time: min");
		lblTimeMin.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		lblTimeMin.setBounds(560, 251, 177, 43);
		RepairPanel.add(lblTimeMin);
		
		JLabel lblMaximumShipHealth = new JLabel("Maximum Ship Health: ~~~");
		lblMaximumShipHealth.setFont(new Font("Tw Cen MT", Font.PLAIN, 30));
		lblMaximumShipHealth.setBounds(504, 62, 346, 43);
		RepairPanel.add(lblMaximumShipHealth);
		
		//Tab to purchase items for battle
		JPanel PurchasePanel = new JPanel();
		tabbedPane.addTab("Purchase Items", null, PurchasePanel, null);
		PurchasePanel.setLayout(null);
		
		JLabel lblRepairKit = new JLabel("Repair Kit");
		lblRepairKit.setHorizontalAlignment(SwingConstants.CENTER);
		lblRepairKit.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));
		lblRepairKit.setBounds(10, 11, 420, 43);
		PurchasePanel.add(lblRepairKit);
		
		JLabel lblShieldSupercharger = new JLabel("Shield Supercharger");
		lblShieldSupercharger.setHorizontalAlignment(SwingConstants.CENTER);
		lblShieldSupercharger.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 28));
		lblShieldSupercharger.setBounds(553, 11, 420, 43);
		PurchasePanel.add(lblShieldSupercharger);
		
		JLabel lblRestoresHealth = new JLabel("- Restores 1000 health");
		lblRestoresHealth.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRestoresHealth.setBounds(109, 63, 242, 36);
		PurchasePanel.add(lblRestoresHealth);
		
		JLabel label_34 = new JLabel("Cost:");
		label_34.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_34.setBounds(78, 117, 72, 20);
		PurchasePanel.add(label_34);
		
		JLabel label_35 = new JLabel(steelIcon);
		label_35.setBounds(149, 99, 50, 50);
		PurchasePanel.add(label_35);
		
		JLabel label_36 = new JLabel("x1000");
		label_36.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_36.setBounds(209, 117, 116, 20);
		PurchasePanel.add(label_36);
		
		JLabel label_37 = new JLabel(grapheneIcon);
		label_37.setBounds(149, 160, 50, 50);
		PurchasePanel.add(label_37);
		
		JLabel label_38 = new JLabel("x1000");
		label_38.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_38.setBounds(209, 178, 116, 20);
		PurchasePanel.add(label_38);
		
		JLabel label_39 = new JLabel(starliteIcon);
		label_39.setBounds(149, 221, 50, 50);
		PurchasePanel.add(label_39);
		
		JLabel label_40 = new JLabel("x1000");
		label_40.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_40.setBounds(209, 239, 116, 20);
		PurchasePanel.add(label_40);
		
		JLabel lblIncreasesShield = new JLabel("- Increases shield deflect percentage");
		lblIncreasesShield.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIncreasesShield.setBounds(618, 68, 366, 36);
		PurchasePanel.add(lblIncreasesShield);
		
		JLabel lblByFor = new JLabel("   by 20% for two turns");
		lblByFor.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblByFor.setBounds(618, 88, 366, 36);
		PurchasePanel.add(lblByFor);
		
		JLabel label_33 = new JLabel("Cost:");
		label_33.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_33.setBounds(574, 135, 72, 20);
		PurchasePanel.add(label_33);
		
		JLabel label_41 = new JLabel(steelIcon);
		label_41.setBounds(645, 117, 50, 50);
		PurchasePanel.add(label_41);
		
		JLabel label_42 = new JLabel("x1000");
		label_42.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_42.setBounds(705, 135, 116, 20);
		PurchasePanel.add(label_42);
		
		JLabel label_43 = new JLabel("x1000");
		label_43.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_43.setBounds(705, 196, 116, 20);
		PurchasePanel.add(label_43);
		
		JLabel label_44 = new JLabel(plutoniumIcon);
		label_44.setBounds(645, 178, 50, 50);
		PurchasePanel.add(label_44);
		
		JLabel label_45 = new JLabel(pyroxiumIcon);
		label_45.setBounds(645, 239, 50, 50);
		PurchasePanel.add(label_45);
		
		JLabel label_46 = new JLabel("x1000");
		label_46.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
		label_46.setBounds(705, 257, 116, 20);
		PurchasePanel.add(label_46);
		
		JButton btnNewButton = new JButton(purchaseIcon);
		btnNewButton.setBounds(109, 300, 200, 75);
		PurchasePanel.add(btnNewButton);
		
		JButton button_5 = new JButton(purchaseIcon);
		button_5.setBounds(645, 300, 200, 75);
		PurchasePanel.add(button_5);
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
