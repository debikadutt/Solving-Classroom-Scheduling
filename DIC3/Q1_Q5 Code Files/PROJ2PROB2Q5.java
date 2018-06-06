/* Ques 5 Most free time category in the day per location */
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
import java.util.*;

public class PROJ2PROB2Q5 {

    public static class Mapper1
    extends Mapper<Object, Text, Text, IntWritable>{

        private String TimeOfDay(String T)
        {
            int hour = 0;
            for(int i = 0;i<T.length();i++)
            {
                if (T.charAt(i) == ':') {
                   break;
                }
                hour = hour*10 + T.charAt(i) - '0';
            }
            if(hour >= 8 && hour < 12 && T.charAt(T.length()-2) == 'A')
            {
              String cat = "0";
              return cat;
            }
            else if(hour >= 12 || hour < 4)
            {
              String cat = "1";
              return cat;
            }
            else if(hour >= 4)
            {
              String cat = "2";
              return cat;
            }
            return "";
        }
        private Text word = new Text();

        public void map(Object key, Text value, Context context
                        ) throws IOException, InterruptedException {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("M","Mon");
            hm.put("T","Tues");
            hm.put("W","Wed");
            hm.put("R","Thur");
            hm.put("F","Fri");
            hm.put("S","Weekend");
            String[] fields=value.toString().split(","); //new array of 9 elements
            String[] time = fields[4].split(" ");
            if(time[0].equals("Unknown")) return;

            if(fields[1].equals("Unknown") || fields[1].equals("Arr") || fields[3].equals("Unknown")) return;
            for( int i = 0; i < fields[3].length(); i++)
            {
                String day = ""+fields[3].charAt(i);
                if(!hm.containsKey(day)) continue;
                String sem = fields[1].split("\\s+")[1] + "_" + fields[2].split("\\s+")[0] + "_" + hm.get(day) + "**" + fields[fields.length-3] + "##" + TimeOfDay(time[time.length-1]);

                word.set(sem);
                /***** enrolled , reducer will find total of enrolled at the time category *****/
                context.write(word,new IntWritable(Integer.parseInt(fields[fields.length-2])));
            }

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
            result.set(sum);
            context.write(key,result);
        }
    }
    public static class Mapper2
    extends Mapper<Object, Text, Text, IntWritable>{
        Text word=new Text();

        public void map(Object key, Text value, Context context
                        ) throws IOException, InterruptedException {
            String[] fields=value.toString().split("\\t");
            String[] sem = fields[0].split("##");
            word.set(sem[0]);
            if(sem.length < 2) return;
            context.write(word,new IntWritable(Integer.parseInt(fields[1]+sem[1])));
        }
    }

    public static class Reducer2
    extends Reducer<Text,IntWritable,Text,IntWritable> {

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
                           ) throws IOException, InterruptedException {
            int maxCat = 0;
            double maxVal = 0;
            double total = 0;
            
            for(IntWritable val:values){
//                context.write(key,new IntWritable(val.get()));
                int timeCat = (val.get())%10;
                double valu = (val.get())/10;
                total += valu;
                if(valu > maxVal)
                {
                  maxVal = valu;
                  maxCat = timeCat;
                }
            }
            if( total == 0)
            {
                total = 1;
            }
            key.set(key.toString() + appendTime(maxCat));
            double multi = 100;
            context.write(key,new IntWritable((int)((maxVal*multi)/total)));
            
        }
        public String appendTime(int timeCat)
        {
            String append = "";
            if(timeCat == 0)
            {
                append = "_Morning";
            }
            else if(timeCat == 1)
            {
                append = "_Noon";
            }
            else
            {
                append = "_Evening";
            }
            return append;
            
        }
    }
    
    
    
    public static void main(String[] args) throws Exception {
        String temp="Temp";
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "get total number of students for every year");
        job.setJarByClass(PROJ2PROB2Q5.class);
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
        job2.setJarByClass(PROJ2PROB2Q5.class);
        job2.setMapperClass(Mapper2.class);
        //job2.setCombinerClass(FindIncreaseReducer.class);
        job2.setReducerClass(Reducer2.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job2, new Path("Temp"));
        FileOutputFormat.setOutputPath(job2, new Path(args[1]));
        System.exit(job2.waitForCompletion(true) ? 0 : 1);

    }
}
