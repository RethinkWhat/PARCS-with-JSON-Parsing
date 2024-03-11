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

        // constants / variables
        // TODO: add method to check if the time today is equal to the time of the booking.
        //  Use the code below to start it:

        //if (model.isStartTimer()) {
        //    view.getPnlTimerGraphics().getSwingTimer().start();
       // }

        view.getPnlTicketInfo().setLblParkingType(model.getParkingType());
        view.getPnlTicketInfo().setLblParkingSpot(model.getParkingSlot());
        view.getPnlTicketInfo().setLblParkingDate(model.getDate());
        view.getPnlTicketInfo().setLblParkingDuration((model.getDuration() + " hour/s"));
        view.getPnlTicketInfo().setLblParkingTime(model.getTimeIn() + " - " + model.getTimeOut());

    }

    public void startTimer() {
        //TODO: RMI Implementation
        /*
        Thread thread = new Thread(() -> {

            if (model.getClient().getDate().equals(model.getDate()) &&
                    Integer.valueOf(model.getTimeIn().split(":")[0]) >=
            Integer.valueOf(model.getClient().getTime().split(":")[0])) {
                if (model.isStartTimer() && !model.isTimeStarted()) {
                    String timeNow[] = model.getClient().getTime().split(":");

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

                    view.getPnlTimerGraphics().setHour(hoursRemaining);
                    view.getPnlTimerGraphics().setMinute(minutesRemaining);
                    view.getPnlTimerGraphics().setSecond(secondsRemaining);

                    view.getPnlTimerGraphics().getSwingTimer().start();
                    model.setTimeStarted(true);
                    view.repaint();
                }
            }
        });
        thread.start();

         */
    }

}
