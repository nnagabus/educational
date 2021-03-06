/*-
 * This computer program is the confidential information and proprietary trade
 * secret of Cisco Systems, Inc. Possessions and use of this program must
 * conform strictly to the license agreement between the user and Cisco Systems,
 * Inc., and receipt or possession does not convey any rights to divulge,
 * reproduce, or allow others to use this program without specific written
 * authorization of Cisco Systems, Inc.
 * 
 * Copyright 2011-2013 Cisco Systems, Inc. All rights reserved.
 * 
 * Created on Sep 28, 2013
 */
package threads.semaphore;

public class Semaphore {
    
    public static void main(String[] args){
        Semaphore semaphore = new Semaphore();

        SendingThread sender = new SendingThread(semaphore);

        ReceivingThread receiver = new ReceivingThread(semaphore);

        receiver.start();
        sender.start();
    }
    private boolean signal = false;

    public synchronized void take() {
      this.signal = true;
      this.notify();
    }

    public synchronized void release() throws InterruptedException{
      while(!this.signal) wait();
      this.signal = false;
    }

  }


 class SendingThread  extends Thread{
    Semaphore semaphore = null;

    public SendingThread(Semaphore semaphore){
      this.semaphore = semaphore;
    }

    public void run(){
      while(true){
        //do something, then signal
        this.semaphore.take();

      }
    }
  }

 class ReceivingThread extends Thread{
    Semaphore semaphore = null;

    public ReceivingThread(Semaphore semaphore){
      this.semaphore = semaphore;
    }

    public void run(){
      while(true){
        try {
            this.semaphore.release();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //receive signal, then do something...
      }
    }
  }