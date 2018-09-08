package edu.mit.hadoop.hdfs.api;

import java.lang.reflect.Method;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class FileReader {

    // static {
    // URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    // }

    public static void printFileInfo(FileSystem fs, Path path) throws Exception {

        System.out.println("\n----------------------- File Status Info -------------------------------");
        System.out.println(path.getName() + " exists: " + fs.exists(path));

        FileStatus stat = fs.getFileStatus(path);
        Class clazz = FileStatus.class;
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            String name = method.getName();
            if ((name.startsWith("get") || name.startsWith("is")) && !name.equals("getSymlink")) {
                int argCnt = method.getParameterCount();
                if (argCnt == 0) {
                    Object result = method.invoke(stat, null);
                    System.out.println(name.replaceAll("get", "") + ": " + result.toString());
                }
            }
        }

        System.out.println("--------------------------------------------------------------------------\n");
    }

    public static void main(String[] args) throws Exception {

        String hdfsFileURI = "hdfs://192.168.48.128/user/cloudera/data/sample.txt";

        if (args != null && args.length > 0) {
            hdfsFileURI = args[0];
        }

        System.out.println("hdfsFileURI=" + hdfsFileURI);
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.48.128");
        conf.set("fs.default.name", "hdfs://192.168.48.128"); // 50010
        // conf.set("mapred.job.tracker", "hdfs://192.168.48.128:54311");

        URI fileURI = URI.create(hdfsFileURI);
        FileSystem fs = FileSystem.get(fileURI, conf, "cloudera");
        FSDataInputStream in = null;
        // InputStream in = null;
        try {
            Path path = new Path(fileURI);
            printFileInfo(fs, path);

            in = fs.open(path);
            in.seek(0); // go to the start of the file
            IOUtils.copyBytes(in, System.out, 4096, false);
            in.seek(0); // go back to the start of the file
            IOUtils.copyBytes(in, System.out, 4096, false);
        } finally {
            IOUtils.closeStream(in);
        }
    }

}