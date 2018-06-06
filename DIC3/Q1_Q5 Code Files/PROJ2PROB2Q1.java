import java.io.IOException;
import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PROJ2PROB2Q1 {

    public static class Mapper1
    extends Mapper<Object, Text, Text, IntWritable>{

        private Text word = new Text();
        public void map(Object key, Text value, Context context
                        ) throws IOException, InterruptedException {
            String[] fields=value.toString().split(","); //new array of 9 elements
            if(fields[1].equals("Unknown")||fields[7].equals("")||!StringUtils.isNumeric(fields[7]))return;
            int year = Integer.parseInt(fields[1].split("\\s+")[1]);
            if(year == 2017)
            {
              return;
            }
            String newkey = "";
            if(year%2 == 1)
            {
                String s = Integer.toString((year + 1));
                String f = Integer.toString(year);
                newkey = f + "_" + s + "##" + fields[2];
            }
            else
            {
                String s = Integer.toString(year);
                String f = Integer.toString(year - 1);
                newkey = f + "_" + s + "##" + fields[2];
            }
            word.set(newkey);
            context.write(word,new IntWritable(Integer.parseInt(fields[fields.length-2])));
        }
    }

    public static class Reducer1
    extends Reducer<Text,IntWritable,Text,IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
                           ) throws IOException, InterruptedException {
            int sum=0;
            for(IntWritable val:values){
                sum += val.get();
            }
            result.set(sum/2);
            context.write(key,result);
        }
    }
    public static class Mapper2
    extends Mapper<Object, Text, Text, IntWritable>{
        Text word=new Text();

        public void map(Object key, Text value, Context context
                        ) throws IOException, InterruptedException {
            String[] fields=value.toString().split("\\t");
            String[] yearRange = fields[0].split("_");
            String[] year_sub = yearRange[1].split("##");
            if(!StringUtils.isNumeric(yearRange[0])||!StringUtils.isNumeric(year_sub[0]))
            {
              return;
            }
            String key1 = (Integer.parseInt(yearRange[0])) + "_" + (Integer.parseInt(year_sub[0])) + " - " + (Integer.parseInt(yearRange[0]) + 2) + "_" + (Integer.parseInt(year_sub[0]) + 2) + "##" + year_sub[1];
            String key2 = (Integer.parseInt(yearRange[0]) - 2) + "_" + (Integer.parseInt(year_sub[0]) - 2) + " - " + fields[0];
            word.set(key1);
            context.write(word,new IntWritable(Integer.parseInt(fields[fields.length-1])));
            word.set(key2);
            context.write(word,new IntWritable(Integer.parseInt(fields[fields.length-1])));
        }
    }

    public static class Reducer2
    extends Reducer<Text,IntWritable,Text,IntWritable> {

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
                           ) throws IOException, InterruptedException {
            Iterator<IntWritable> iterator=values.iterator();
            int value1=iterator.next().get();
            if(!iterator.hasNext())return;
            int value2=iterator.next().get();
            value2=value1-value2;
            context.write(key,new IntWritable(value2));
        }
    }
    public static void main(String[] args) throws Exception {
        String temp="Temp";
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "get total number of students for every year");
        job.setJarByClass(PROJ2PROB2Q1.class);
        job.setMapperClass(Mapper1.class);
        job.setCombinerClass(Reducer1.class);
        job.setReducerClass(Reducer1.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(temp));
        job.waitForCompletion(true);
        Configuration conf2 = new Configuration();
        Job job2 = Job.getInstance(conf2, "get # of students increasing between years");
        job2.setJarByClass(PROJ2PROB2Q1.class);
        job2.setMapperClass(Mapper2.class);
        //job2.setCombinerClass(FindIncreaseReducer.class);
        job2.setReducerClass(Reducer2.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job2, new Path(temp));
        FileOutputFormat.setOutputPath(job2, new Path(args[1]));
        System.exit(job2.waitForCompletion(true) ? 0 : 1);

    }
}
