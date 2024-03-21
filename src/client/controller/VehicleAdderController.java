package client.controller;

import client.model.ApplicationModel;
import client.model.VehicleAdderModel;
import client.view.ApplicationView;
import client.view.VehicleAdderView;
import client.view.VehicleErrorDialog;
import utilities.Resources;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Template for VehicleAdderController.
 * The VehicleAdderController processes the user requests for adding a vehicle.
 * Based on the user request, the VehicleAdderController defines methods and invokes methods in the View
 * and Model to accomplish the requested action.
 */
public class VehicleAdderController {
    /**
     * The view VehicleAdderView object.
     */
    private VehicleAdderView view;
    /**
     * The model VehicleAdderModel object.
     */
    private VehicleAdderModel model;

    /**
     * Constructs a VehicleAdderController with a specified view and model.
     * @param view The specified VehicleAdderView.
     * @param model The specified VehicleAdderModel.
     */
    public VehicleAdderController(VehicleAdderView view, VehicleAdderModel model) {
        this.view = view;
        this.model = model;
        //view.setDefaultCloseOperation(model.getClient().logoutAndExit());

        // model map
        view.getCmbType().setModel(new DefaultComboBoxModel(model.getVehicleTypes()));

        // action listeners
        view.setAddVehicleListener(new AddVehicleListener());
        view.setCancelListener(new CancelVehicleAdder());

        // mouse listeners
        view.getBtnAddVehicle().addMouseListener(new Resources.CursorChanger(view.getBtnAddVehicle()));
        view.getBtnCancel().addMouseListener(new Resources.CursorChanger(view.getBtnCancel()));

        // focus listeners
        view.getTxtModel().addFocusListener(new Resources.TextFieldFocus(view.getTxtModel(),
                "Vehicle Model (e.g., Honda Civic)"));
        view.getTxtPlateNumber().addFocusListener(new Resources.TextFieldFocus(view.getTxtPlateNumber(),
                "License Plate Number (e.g., NWA 991)"));
    }

    /**
     * Adds the vehicle of the user to their respective account.
     */
    class AddVehicleListener implements ActionListener {
        /**
         * Adding the vehicle.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            boolean validate = model.writeVehicle(view.getVehicleType(),view.getModel(),view.getPlateNumber());
            if (validate) {
                new ApplicationController(new ApplicationView(), new ApplicationModel(model.getClient()));
                view.dispose();
            } else {
                new VehicleErrorDialog();
            }
        }
    }

    /**
     * Cancels the process of adding the vehicle by closing the frame.
     */
    class CancelVehicleAdder implements ActionListener {
        /**
         * Closes the frame.
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            new ApplicationController(new ApplicationView(), new ApplicationModel(model.getClient()));
            view.dispose();
        }
    }
}
