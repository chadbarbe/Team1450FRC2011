/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Utils;

/**
 * A utility class to run period loops.
 */
public class Threading {

    /**
     * Run the runnable in a loop with a specified period in ms.
     *
     * @param period period in ms
     * @param runnable loop content
     * @param name name of loop thread
     */
    public static void runInLoop(final long period, final Runnable runnable, final String name) {
        Thread thread = new Thread (name) {
            public void run() {
                while (true) {
                    runnable.run();
                    try {
                        Thread.sleep(period);
                    } catch (InterruptedException ex) {
                        System.err.println("ERROR: Thread (" + name + ") got unexpected InterruptedException");
                        ex.printStackTrace();
                        break;
                    }
                }
            }
        };
        thread.start();
    }

    public static void sleep(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
