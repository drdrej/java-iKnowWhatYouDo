package com.significantfiles.debug.test;

public class SimpleApp {

	public static void main(String[] args) {
		final long now = System.currentTimeMillis();

		for (int i = 1000000; i > 0; i--) {
			final String logMsg = "Hello World! Current time is: " + now;
			System.out.println(logMsg);
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("<<< end of app");
	}
}
