package edu.mit.hadoop.hdfs.api;

import java.io.File;
import java.util.Arrays;

public class HDFSMain {

	public static void main(String[] args) throws Exception {

		// default values
		String action = "FileReader";
		// if (args != null && args.length > 0) {
		// action = args[0];
		// } else {
		// usage();
		// }

		File winutilsDir = new File("..\\winutils-master", "hadoop-2.6.0");
		if (!winutilsDir.exists()) {
			System.err.println("hadoop-winutils directory not found.");
			System.exit(-1);
		}
		System.setProperty("hadoop.home.dir", winutilsDir.getAbsolutePath());
		System.out.println("hadoop.home.dir: " + System.getProperty("hadoop.home.dir"));

		switch (action) {
		case "FileReader":
			if (args != null && args.length > 0) {
				FileReader.main(Arrays.copyOfRange(args, 1, args.length));
			} else {
				FileReader.main(null);
			}
			break;

		default:
			break;
		}
	}

	private static void usage() {
		System.out.println("Usage: HDFSMain <action> <arguments>");
		System.out.println("For example, HDFSMain FileReader hdfs://192.168.28.128/user/cloudera/data/sample.txt");
		System.exit(-1);
	}
}
