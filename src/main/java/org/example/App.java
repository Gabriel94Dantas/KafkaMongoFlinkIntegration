package org.example;

import org.example.jobs.EventJob;

/**
 * Main method
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception {
        EventJob eventJob = new EventJob();
        eventJob.run();

    }
}
