import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CircularBuffer implements IBuffer{
	
	private final int BUFFER_SIZE = 4;
 
	private int[] buffer = new int[BUFFER_SIZE];
	private int writePosition = 0;
	private int readPosition = 0;
	private volatile int occupiedSlots = 0;
	
	private Lock lock = new ReentrantLock();
	private Condition hasData = lock.newCondition(); // condition variable
	private Condition hasSpace = lock.newCondition(); // condition variable
	
	public void set(int inData) throws InterruptedException {
		lock.lock();
		
		while(occupiedSlots == BUFFER_SIZE) { hasSpace.wait(); } // wait until there is available space in the buffer						
			
		buffer[writePosition] = inData; // write data to buffer
		
		++occupiedSlots;
		writePosition = (writePosition + 1) % BUFFER_SIZE;
		
		hasData.signal();		
		
		lock.unlock();		
	}
	
	public int get() throws InterruptedException {
		lock.lock();
		
		while(occupiedSlots == 0) { hasData.wait();	}
		int retVal = buffer[readPosition];
		--occupiedSlots;
		readPosition = (readPosition + 1) % BUFFER_SIZE;
					
		hasSpace.signal(); // signal that space is available
		lock.unlock();
			
		return retVal;		
	}	
}
