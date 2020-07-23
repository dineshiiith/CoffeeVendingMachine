package Scratch;

public class Dummy implements Runnable{
    Integer counter;

    public Dummy() {
        counter = 0;
    }


    private void process() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " inside Sync with counter value: " + counter);
            while (counter == 3) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                }

            }
            counter++;
            System.out.println(Thread.currentThread().getName() + " ending sync with counter " + counter);
        }
            System.out.println(Thread.currentThread().getName() + " came out with counter " + counter);
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException ex) {}
            synchronized (this) {
                counter--;
                this.notifyAll();
            }
            System.out.println(Thread.currentThread().getName() + " going out with counter " + counter);

    }

    @Override
    public void run() {
        process();
    }
}
