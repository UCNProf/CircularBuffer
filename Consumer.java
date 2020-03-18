
public class Consumer extends Thread {
	
	IBuffer buffer;
	
	public Consumer(IBuffer buffer) {
		super("Consumer");

		this.buffer = buffer;
	}

	@Override
	public void run() {

		int sum = 0;
		
		try {
			for(int v = 1; v <= 8; v++) {
			
				Thread.sleep((int) (Math.random() * 3001));
				
				int r = buffer.get();			
				sum += r;
				System.out.println(getName() + " read value: "+ r);
			}
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		System.out.println(getName() + " read values totaling: "+ sum);
	}	
}
