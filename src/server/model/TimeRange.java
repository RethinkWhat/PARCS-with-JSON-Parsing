package server.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public record TimeRange(String startTime, String endTime) {
    public TimeRange(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String endTime() {
        return endTime;
    }

    @Override
    public String startTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return startTime + "-" + endTime;
    }


    /**
     * Inclusive of start, exclusive of end
     * 7:00-10:00
     * 7:00, 8:00, 9:00*/
    public List<String> getStartToEndTime() {
        List<String> hourIncrements = new ArrayList<>();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date start = sdf.parse(startTime);
            Date end = sdf.parse(endTime);


            long interval = 60 * 60 * 1000; // 1 hour in milliseconds is 60 * 60 * 1000

            for (long time = start.getTime(); time < end.getTime(); time += interval) {
                hourIncrements.add(sdf.format(new Date(time)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception according to your needs
        }

        return hourIncrements;
    }
}
