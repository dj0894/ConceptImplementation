package com.example.server;

import java.net.Socket;

import com.example.server.request.handler.RequestHandler;

public class ServiceRequestTask implements Runnable {
	
	Socket sock;
	
	RequestHandler requestHandler = new RequestHandler();
	
	public ServiceRequestTask(Socket sock) {
		this.sock = sock;
	}

	@Override
	public void run() {
		requestHandler.handleRequest(sock);
	}

}
