package ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import business.factory.Factory;
import database.WorkerDAO;
import database.MachineDAO;
import business.models.Worker;
import business.models.machines.AssemblyRobot;
import business.models.machines.CNCMachine;
import business.models.machines.ConveyorBelt;
import business.models.machines.PaintingRobot;
import business.models.machines.WeldingRobot;
import business.models.workers.Operator;
import business.models.workers.Supervisor;
import business.models.workers.Technician;

public class MainWindow extends JFrame {
    private Factory factory;
    private WorkerDAO workerDAO = new WorkerDAO();
    private MachineDAO machineDAO = new MachineDAO();
    private JTextField idF, nameF, extraF;
    private JComboBox<String> roleC;
    private JLabel workerCountLabel;
    private JLabel outputLabel;

    private JPanel workerRowsPanel;
    private JPanel machineRowsPanel;

    public MainWindow(Factory factory) {
        this.factory = factory;
        
        loadMachinesIntoFactory();
        
        setTitle("AXIS Industrial Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 16));
        tabbedPane.addTab("Dashboard", setupDashboardTab());
        tabbedPane.addTab("Workers", setupWorkersTab());
        tabbedPane.addTab("Machines", setupMachinesTab());
        
        getContentPane().add(tabbedPane);
        
        refreshWorkerList();
        refreshMachineList(); 
        updateDashboard();    
    }

    private JPanel setupWorkersTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBorder(new EmptyBorder(20, 60, 20, 60));

        JLabel title = new JLabel("Personnel Management");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel header = new JPanel(new GridLayout(1, 4));
        header.setBackground(new Color(216, 216, 216));
        header.setPreferredSize(new Dimension(1100, 50));
        header.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
        header.add(new JLabel("  ID", JLabel.LEFT)).setFont(new Font("Arial", Font.BOLD, 18));
        header.add(new JLabel("  Name", JLabel.LEFT)).setFont(new Font("Arial", Font.BOLD, 18));
        header.add(new JLabel("  Role", JLabel.LEFT)).setFont(new Font("Arial", Font.BOLD, 18));
        header.add(new JLabel("  Assignment", JLabel.LEFT)).setFont(new Font("Arial", Font.BOLD, 18));
        
        centerPanel.add(header, BorderLayout.NORTH);

        workerRowsPanel = new JPanel(new GridLayout(0, 1, 0, 1));
        JScrollPane scroll = new JScrollPane(workerRowsPanel);
        scroll.setBorder(new EmptyBorder(0,0,0,0));
        centerPanel.add(scroll, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);

        panel.add(createWorkerInputForm(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createWorkerInputForm() {
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        form.setBorder(new TitledBorder("Quick Register"));

        idF = new JTextField(5);
        nameF = new JTextField(10);
        extraF = new JTextField(10);
        roleC = new JComboBox<>(new String[]{"OPERATOR", "TECHNICIAN", "SUPERVISOR"});
        
        JButton btnAddWorker = new JButton("Add to Axis");
        btnAddWorker.setBackground(new Color(0, 120, 215));
        btnAddWorker.setForeground(Color.WHITE);

        form.add(new JLabel("ID:")); form.add(idF);
        form.add(new JLabel("Name:")); form.add(nameF);
        form.add(new JLabel("Role:")); form.add(roleC);
        form.add(new JLabel("Line/Dept:")); form.add(extraF);
        form.add(btnAddWorker);

        btnAddWorker.addActionListener(e -> {
            String id = idF.getText();
            String name = nameF.getText();
            String role = (String) roleC.getSelectedItem();
            String extra = extraF.getText();

            Worker newWorker;
            if (role.equals("SUPERVISOR")) newWorker = new Supervisor(id, name, extra);
            else if (role.equals("TECHNICIAN")) newWorker = new Technician(id, name, extra);
            else newWorker = new Operator(id, name, extra);

            factory.addWorker(newWorker);
            workerDAO.save(newWorker); 
            
            refreshWorkerList(); 
            
            idF.setText("");
            nameF.setText("");
            extraF.setText("");
        });

        return form;
    }

    private JPanel setupDashboardTab() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        outputLabel = new JLabel(machineDAO.findAll().size() + " units", JLabel.CENTER);
        statsPanel.add(createStatCard("Total Output", outputLabel, Color.BLUE));
        
        workerCountLabel = new JLabel(factory.getWorkers().size() + " members", JLabel.CENTER);
        
        statsPanel.add(createStatCard("Active Workers", workerCountLabel, Color.DARK_GRAY));
        
        statsPanel.add(createStatCard("System Status", "READY", new Color(0, 150, 0)));
        
        panel.add(statsPanel, BorderLayout.CENTER);

        JButton runBtn = new JButton("START AXIS INDUSTRIAL SHIFT");
        runBtn.setFont(new Font("Arial", Font.BOLD, 30));
        runBtn.setForeground(Color.WHITE);
        runBtn.setBackground(new Color(220, 20, 60));
        
        runBtn.addActionListener(e -> {
            factory.runShift();
            
            for (business.models.Machine m : factory.getMachines()) {
                int partsToSave = m.getTotalPartsProduced(); 
                
                machineDAO.update(m);
            }
            
            refreshMachineList(); 
            updateDashboard();
        });

        panel.add(runBtn, BorderLayout.SOUTH);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setPreferredSize(new Dimension(350, 350));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon originalIcon = new ImageIcon(MainWindow.class.getResource("/images/Axis Industrial logo.png"));
        java.awt.Image scaledImage = originalIcon.getImage().getScaledInstance(350, 350, java.awt.Image.SCALE_SMOOTH);
        lblNewLabel.setIcon(new ImageIcon(scaledImage));
        
        panel.add(lblNewLabel, BorderLayout.NORTH);

        return panel;
    }

 // M 1: For static text
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBorder(new LineBorder(color, 2));
        
        JLabel t = new JLabel(title, JLabel.CENTER);
        JLabel v = new JLabel(value, JLabel.CENTER);
        
        v.setFont(new Font("Arial", Font.BOLD, 20));
        v.setForeground(color);
        
        card.add(t);
        card.add(v);
        return card;
    }

    // M 2: For live updating text
    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBorder(new LineBorder(color, 2));
        
        JLabel t = new JLabel(title, JLabel.CENTER);
        
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(color);
        
        card.add(t);
        card.add(valueLabel); 
        return card;
    }

    private JPanel setupMachinesTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBorder(new EmptyBorder(20, 60, 20, 60));

        JLabel title = new JLabel("Machine Registry & Status");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel header = new JPanel(new GridLayout(1, 5));
        header.setBackground(new Color(200, 200, 200));
        header.setPreferredSize(new Dimension(1100, 50));
        header.setBorder(new EtchedBorder());
        
        header.add(new JLabel("  ID")).setFont(new Font("Arial", Font.BOLD, 18));
        header.add(new JLabel("  Name")).setFont(new Font("Arial", Font.BOLD, 18));
        header.add(new JLabel("  Type")).setFont(new Font("Arial", Font.BOLD, 18));
        header.add(new JLabel("  Status")).setFont(new Font("Arial", Font.BOLD, 18));
        header.add(new JLabel("  Output")).setFont(new Font("Arial", Font.BOLD, 18));
        
        centerPanel.add(header, BorderLayout.NORTH);

        machineRowsPanel = new JPanel(new GridLayout(0, 1, 0, 1));
        centerPanel.add(new JScrollPane(machineRowsPanel), BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);

        panel.add(createMachineInputForm(), BorderLayout.SOUTH);

        return panel;
    }
    
    private void refreshWorkerList() {
        workerRowsPanel.removeAll();

        java.util.List<String[]> allWorkers = workerDAO.findAll();
        if (workerCountLabel != null) {
            workerCountLabel.setText(allWorkers.size() + " members");
        }

        for (String[] data : allWorkers) {
            JPanel row = new JPanel(new GridLayout(1, 4));
            row.setMaximumSize(new Dimension(1100, 40));
            
            row.add(new JLabel("  " + data[0]));
            row.add(new JLabel("  " + data[1]));
            row.add(new JLabel("  " + data[2])); 
            row.add(new JLabel("  " + data[3])); 
            workerRowsPanel.add(row);
        }

        workerRowsPanel.revalidate();
        workerRowsPanel.repaint();
        this.revalidate();
        this.repaint();
    }
    
    private void loadMachinesIntoFactory() {
        java.util.List<String[]> allMachines = machineDAO.findAll();
        
        for (String[] data : allMachines) {
            String id = data[0];
            String name = data[1];
            String type = data[2];
            int dbParts = Integer.parseInt(data[4]);

            business.models.Machine loadedMachine = null;
            if (type.equals("WeldingRobot")) loadedMachine = new business.models.machines.WeldingRobot(id, name);
            else if (type.equals("AssemblyRobot")) loadedMachine = new business.models.machines.AssemblyRobot(id, name);
            else if (type.equals("PaintingRobot")) loadedMachine = new business.models.machines.PaintingRobot(id, name);
            else if (type.equals("CNCMachine")) loadedMachine = new business.models.machines.CNCMachine(id, name);
            else if (type.equals("ConveyorBelt")) loadedMachine = new business.models.machines.ConveyorBelt(id, name);
            
            if (loadedMachine != null) {
                loadedMachine.setParts(dbParts);
                factory.addMachine(loadedMachine); 
            }
        }
    }

    private void updateDashboard() {
        if (workerCountLabel != null) {
            workerCountLabel.setText(factory.getWorkers().size() + " members");
            workerCountLabel.repaint();
        }
        
        if (outputLabel != null) {
            int totalParts = 0;
            for (String[] data : machineDAO.findAll()) {
                totalParts += Integer.parseInt(data[4]);
            }
            outputLabel.setText(totalParts + " units");
            outputLabel.repaint();
        }
    }

    private JPanel createMachineInputForm() {
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        form.setBorder(new TitledBorder("Register New Equipment"));

        JTextField mIdF = new JTextField(5);
        JTextField mNameF = new JTextField(10);
        JComboBox<String> typeC = new JComboBox<>(new String[]{
            "WeldingRobot", "AssemblyRobot", "PaintingRobot", "CNCMachine", "ConveyorBelt"
        });
        
        JButton btnAddMachine = new JButton("Install Machine");
        btnAddMachine.setBackground(new Color(34, 139, 34));
        btnAddMachine.setForeground(Color.WHITE);

        form.add(new JLabel("ID:")); form.add(mIdF);
        form.add(new JLabel("Name:")); form.add(mNameF);
        form.add(new JLabel("Type:")); form.add(typeC);
        form.add(btnAddMachine);
        
        btnAddMachine.addActionListener(e -> {
            String id = mIdF.getText();
            String name = mNameF.getText();
            String type = (String) typeC.getSelectedItem();

            business.models.Machine newMachine = null;
            if (type.equals("WeldingRobot")) newMachine = new WeldingRobot(id, name);
            else if (type.equals("AssemblyRobot")) newMachine = new AssemblyRobot(id, name);
            else if (type.equals("PaintingRobot")) newMachine = new PaintingRobot(id, name);
            else if (type.equals("CNCMachine")) newMachine = new CNCMachine(id, name);
            else if (type.equals("ConveyorBelt")) newMachine = new ConveyorBelt(id, name);
            
            if (newMachine != null) {
                factory.addMachine(newMachine);
                machineDAO.save(newMachine);
                
                refreshMachineList(); 
                updateDashboard();
                mIdF.setText("");
                mNameF.setText("");
            }
        });

        return form;
    }
    
    private void refreshMachineList() {
        if (machineRowsPanel == null) return;
        
        machineRowsPanel.removeAll();

        java.util.List<String[]> allMachines = machineDAO.findAll();
        
        for (String[] data : allMachines) {
            JPanel row = new JPanel(new GridLayout(1, 5)); 
            row.setMaximumSize(new Dimension(1100, 40));
            
            row.add(new JLabel("  " + data[0]));
            row.add(new JLabel("  " + data[1]));
            row.add(new JLabel("  " + data[2])); 
            row.add(new JLabel("  " + data[3])); 
            
            JLabel outputText = new JLabel("  " + data[4]);
            outputText.setFont(new Font("Arial", Font.BOLD, 14));
            row.add(outputText); 
            
            machineRowsPanel.add(row);
        }

        machineRowsPanel.revalidate();
        machineRowsPanel.repaint();
    }
}
