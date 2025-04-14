package com.example;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

// import java.io.IOException;
// import java.io.ObjectInputStream;
// import java.net.DatagramPacket;
// import java.net.DatagramSocket;
// import java.net.SocketException;
// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.List;

public class RequestServerTask implements Runnable {

	private Interval partsToSend;

	public RequestServerTask(Interval partsToSend) {
		this.partsToSend = partsToSend;
	}

	@Override
	public void run() {


		// TODO dokonc doma!
		// primanie ziadosti o chunky a ich pridavanie do partsToSend
		
		try (DatagramSocket requestSocket = new DatagramSocket(Consts.REQUEST_SERVER_PORT)) {
			byte[] receivedData = new byte[requestSocket.getReceiveBufferSize()];

			while (true) { 	
				DatagramPacket requestPacket = new DatagramPacket(receivedData, receivedData.length);
				requestSocket.receive(requestPacket);
				
				ByteArrayInputStream bais = new ByteArrayInputStream(requestPacket.getData());
				ObjectInputStream ois = new ObjectInputStream(bais);
				
				int size = ois.readInt();// emptySubitervals size
				System.out.println("Empty Subintervals Size: " + size);
				
				for (int i = 0; i < size; i++) {
					long min = ois.readLong();
					long max = ois.readLong();
					partsToSend.addFullSubinterval(min, max);
					System.out.println("Min: " + min + " |  Max: " + max);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
