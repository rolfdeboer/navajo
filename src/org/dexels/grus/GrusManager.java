package org.dexels.grus;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;

public class GrusManager implements Runnable {

	private final static GrusManager instance; 
	private Thread myThread;
	private final static HashSet<DbConnectionBroker> registeredBrokers = new HashSet<DbConnectionBroker>();
	
	static {
		GrusManager myGrus = new GrusManager();
		myGrus.myThread = new Thread(myGrus, "Grus DbConnection Manager");
		myGrus.myThread.setDaemon(true);
		myGrus.myThread.start();
		instance = myGrus;
	}

	public static GrusManager getInstance() {
		return instance;
	}

	public void addBroker(DbConnectionBroker broker) {
		registeredBrokers.add(broker);
	}

	public void removeBroker(DbConnectionBroker broker) {
		registeredBrokers.remove(broker);
	}

	public void run() {
		long maxAge;
		
		while (true) {	

			try {

				Thread.sleep(10000);
				// Make copy to avoid concurrent modification exception.
				Iterator<DbConnectionBroker> allBrokers = new HashSet<DbConnectionBroker>(registeredBrokers).iterator();

				int index = 0;
				while ( allBrokers.hasNext() ) {

					DbConnectionBroker inspectedBroker = allBrokers.next();

					maxAge = (long) (System.currentTimeMillis() - inspectedBroker.timeoutDays * 86400000L);
					int idle = 0;
					int currentCount = inspectedBroker.current;
					//System.err.println(Thread.currentThread().getName() + ": MAXAGE IS: " + maxAge);
					// Check IDLE time, created[i] contains timestamp of last use.
					
					if ( inspectedBroker.conns != null ) {
						for (int i = 0; i < inspectedBroker.conns.length; i++) {
							if (inspectedBroker.conns[i] != null && !inspectedBroker.usedmap[i] && inspectedBroker.created[i] < maxAge) {
								try {
									inspectedBroker.log("Closing idle connection: " + inspectedBroker.conns[i].hashCode());
									inspectedBroker.conns[i].close();
									idle++;
								} catch (SQLException e) {
									//e.printStackTrace(System.err);
								}
								inspectedBroker.conns[i] = null;
								inspectedBroker.usedmap[i] = false;
								--inspectedBroker.available;
								--inspectedBroker.current;
								// System.err.println("Checking timeout for
								// connections, current count is " + current);
							}
						}
					}
					
					System.err.println( (++index) + ": " + inspectedBroker.location + "/" + inspectedBroker.username + ": IDLE COUNT: " + idle + ", currentCount = " + currentCount);
					if (idle == currentCount) {
						inspectedBroker.log("Nobody interested anymore, about to kill thread ( idle = " + idle + ", currentCount = " + currentCount + ")");
						// Nobody interested anymore.
						inspectedBroker.closed = true;
						inspectedBroker.dead = true;
						removeBroker(inspectedBroker);
					}
				}
			} catch (Throwable t) {
				t.printStackTrace(System.err);
			} finally { 

			}
		} // while true.
	}
	
	public int getInstances() {
		return registeredBrokers.size();
	}
}
