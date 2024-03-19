package client.controller.application_pages;

import client.model.application_pages.TimerModel;
import client.view.application_pages.TimerPageView;

/**
 * The TimerController processes the user requests for viewing the current booking.
 * Based on the user request, the TimerController defines methods and invokes methods in the View and Model
 * to accomplish the requested action.
 */
public class TimerController {
    /**
     * The view TimerPageView.
     */
    private TimerPageView view;
    /**
     * The model TimerModel.
     */
    private TimerModel model;

    /**
     * Constructs a TimerController with a specified view and model.
     * @param view The specified view.
     * @param model The specified model.
     */
    public TimerController(TimerPageView view, TimerModel model) {
        this.view = view;
        this.model = model;

        view.getPnlTicketInfo().setLblParkingType(model.getParkingType());
        view.getPnlTicketInfo().setLblParkingSpot(model.getParkingSlot());
        view.getPnlTicketInfo().setLblParkingDate(model.getDate());
        view.getPnlTicketInfo().setLblParkingDuration((model.getDuration() + " hour/s"));
        view.getPnlTicketInfo().setLblParkingTime(model.getTimeIn() + " - " + model.getTimeOut());

    }

    public void startTimer() {
        System.out.println("start");
        //TODO: RMI Implementation

        Thread thread = new Thread(() -> {

            /*
                        if (model.getClient().getDate().equals(model.getDate()) &&
                    Integer.valueOf(model.getTimeIn().split(":")[0]) >=
            Integer.valueOf(model.getClient().getTime().split(":")[0])) {
             */


            // Compare the dates and check whether the booking date and the date today are the same
            if (model.getClient().getDate().equals(model.getDate())){

                String timeNow[] = model.getClient().getTime().split(":");
                String timeIn[] = model.getTimeIn().split(":");

                if (Integer.valueOf(timeIn[0]) >= Integer.valueOf(timeNow[0])) {
                //if (model.getClient().getTime().compareToIgnoreCase(model.getTimeIn())>=0) {
                    System.out.println("MODEL TIME: " + model.getClient().getTime());
                    System.out.println("MODEL TIME IN: " + model.getTimeIn());
                    System.out.println("unreached");

                    int secondsNow = Integer.parseInt(timeNow[2]);
                    int minutesNow = Integer.parseInt(timeNow[1]);

                    int secondsRemaining = 0;
                    int minutesRemaining = 0;
                    int hoursRemaining = model.getDuration();


                    if (secondsNow != 0) {
                        secondsRemaining = 60 - secondsNow;
                        minutesRemaining -= 1;
                    }
                    if (minutesNow != 0) {
                        minutesRemaining += 60 - minutesNow;
                        hoursRemaining -= 1;
                    }

                    System.out.println("reached graphic before");
                    System.out.println("reached graphic after");

                    view.getPnlTimerGraphics().setHour(hoursRemaining);
                    view.getPnlTimerGraphics().setMinute(minutesRemaining);
                    view.getPnlTimerGraphics().setSecond(secondsRemaining);

                    int duration = model.getDuration();
                    view.setCurrent(3600);


                    view.paint(view.getGraphics());
                    //view.setPnlTimerGraphics(hoursRemaining, minutesRemaining, secondsRemaining);
                    view.getPnlTimerGraphics().getSwingTimer().start();
                    model.setTimeStarted(true);
                    view.repaint();
                }
                } else {
                view.getPnlTimerGraphics().setHour(0);
                view.getPnlTimerGraphics().setMinute(0);
                view.getPnlTimerGraphics().setSecond(0);
                }
        });
        thread.start();
    }

}
