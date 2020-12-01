package com.example.server.request.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.example.server.utils.HttpResponseUtils;
import com.example.server.utils.HttpUtils;
import com.example.server.utils.ResourceLoader;

public class RequestHandler {
	
	ResourceLoader resourceLoader = new ResourceLoader();
	
	public void handleRequest(Socket sock) {
		OutputStream out = null;

		try {
			out = sock.getOutputStream();
			String request = HttpUtils.getRequest(sock);

			String uri = HttpUtils.getRequestUri(request);
			System.out.println("Received request for - " + uri);

			InputStream in = resourceLoader.getResource(uri);

			if (in == null) {
				System.out.println("Sending resource not found ");
				HttpResponseUtils.sendResourceNotFound(out);
				return;
			}

			System.out.println("Sending response ");
			HttpResponseUtils.sendSuccessResponse(in, out);

		} catch (Exception e) {
			e.printStackTrace();
			if (out != null) {
				try {
					System.out.println("Sending internal error ");
					HttpResponseUtils.sendInternalError(out);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				sock.close();
			} catch (Exception e) {
			}
		}
	}
}
