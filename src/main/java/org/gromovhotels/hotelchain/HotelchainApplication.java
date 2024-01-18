package org.gromovhotels.hotelchain;

import org.gromovhotels.hotelchain.consoleapp.ConsoleApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static asg.cliche.ShellFactory.createConsoleShell;
import static org.gromovhotels.hotelchain.utils.shell.ShellExecutor.executeShellDrivenBy;

@SpringBootApplication
public class HotelchainApplication implements CommandLineRunner {

	@Autowired
	private ConsoleApp consoleApp;

	public static void main(String[] args) {
		SpringApplication.run(HotelchainApplication.class, args);
	}

	@Override
	public void run(String... args) {
		executeShellDrivenBy(consoleApp);
	}
}