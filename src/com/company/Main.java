package com.company;




import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;


public class Main {

    public static void main(String[] args) throws Exception {
        int numberOfLTrains = 0;
	    URL url = new URL("http://datamine.mta.info/mta_esi.php?key=1c90c74dbc055d891ad78889c285cec6&feed_id=2");

        FeedMessage feed = FeedMessage.parseFrom(url.openStream());
        for (FeedEntity entity : feed.getEntityList()) {
            if(entity.hasTripUpdate()) {
                numberOfLTrains++;

                //check to see which is more accurate for arrival times.
                //the north or south data


                //System.out.println("Trip update!");
                //System.out.println(entity.getTripUpdate());
                List list = entity.getTripUpdate().getStopTimeUpdateList();
                int counter = 0;
                for(int i = 0; i < list.size(); i++) {
                    String stopID = entity.getTripUpdate().getStopTimeUpdate(i).getStopId();
                    if(stopID.contains("L11")) {

                        counter++;

                        if(stopID.contains("N")){
                           System.out.println("North");
                        }
                        if(stopID.contains("S")) {
                          System.out.println("South");
                        }
                        long timeStamp = entity.getTripUpdate().getStopTimeUpdate(i).getDeparture().getTime();
                        Date arrivalTime = new Date(timeStamp*1000);
                        System.out.println("Estimated Arrival Time at Graham Ave:");
                        System.out.println(arrivalTime);

                        Date now = new Date();
                        now.setTime(System.currentTimeMillis());
                        long duration = arrivalTime.getTime() - now.getTime();
                        //long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
                        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);



                        System.out.println("This train is expected to arrive in: " + diffInMinutes + " minutes.");
                        System.out.println();
                    }
                }


                }

            }
        System.out.println("Number of active L trains: " + numberOfLTrains);
        }


    }
