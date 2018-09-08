package edu.mit.hadoop.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WordCountMain {

	public static void main(String[] args) throws Exception {

		String inputPath = args[0];
		String outputPath = args[1];

		System.out.println("inputPath= [" + inputPath + "]");
		System.out.println("outputPath= [" + outputPath + "]");

		String jobName = "MyWordCount";
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, jobName);
		job.setJarByClass(WordCountMain.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setNumReduceTasks(1);

		FileInputFormat.setInputPaths(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));

		boolean success = job.waitForCompletion(true);
		System.out.println(success);
	}
}
