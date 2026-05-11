package main;

import business.factory.Factory;
import business.models.Worker;
import business.models.machines.*;
import business.models.workers.*;
import database.WorkerDAO;
import database.MachineDAO;
import ui.MainWindow;
import javax.swing.SwingUtilities;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Factory axisFactory = new Factory("Axis Industrial");

        WorkerDAO workerDAO = new WorkerDAO();

        List<String[]> savedWorkers = workerDAO.findAll();
        for (String[] data : savedWorkers) {
            String id = data[0];
            String name = data[1];
            String role = data[2];
            String extra = data[3];

            Worker w;
            if (role.equalsIgnoreCase("SUPERVISOR")) {
                w = new Supervisor(id, name, extra);
            } else if (role.equalsIgnoreCase("TECHNICIAN")) {
                w = new Technician(id, name, extra);
            } else {
                w = new Operator(id, name, extra);
            }
            
            axisFactory.addWorker(w);
        }

        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow(axisFactory);
            window.setVisible(true);
        });
        
        System.out.println("Axis System Initialized with " + savedWorkers.size() + " workers.");
    }
}