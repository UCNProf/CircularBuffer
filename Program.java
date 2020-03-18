
public class Program {

	public static void main(String[] args) throws InterruptedException {

		IBuffer buffer = new CircularBuffer();
		
		Producer producer = new Producer(buffer);
		Consumer consumer = new Consumer(buffer);
		
		producer.start();
		
		//Thread.sleep(12000);
		
		consumer.start();
	}
}
