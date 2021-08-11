package macc.paxsz.com.myapplication.Javatool;

public class threadtest extends Thread{
	
	public threadtest(Object lock) {
		super();
		this.lock = lock;
	}



	public threadtest() {
		// TODO Auto-generated constructor stub
	}



//	private Object lock=new Object();
	private Object lock;
	
	
	
	
	 boolean isrun=true;
	 int i=0;
	 
	 

	@Override
	public void run() {
//		synchronized (lock) {
			// TODO Auto-generated method stub
			System.out.println("Daemon....");
			//		
			while (i < 1000) {
				if (Thread.currentThread().interrupted()) {
					 try {
						 System.out.println("Daemon...." + i);
						throw new InterruptedException();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println("���߳�");
						
					}
					
				}
			  try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				i++;
			}
//		}
		
	}

}
