# Solving-Classroom-Scheduling
Developed a Hadoop Map-Reduce application which structured datasets of size 4GB collected from the University at Buffalo classrooms information database

# Implementation details:
The general application architecture for this project is as follows. The data given and any other data you collect will be stored in single input directory. Understand the data by doing an EDA on RStudio. Suggested application architecture is shown below. While MR is a requirement for data analysis you are free to use any other visualization for the charts. Tableau is recommended.

# Figure 1 Application Architecture
Application architecture above shows the overall approach to be taken. Understand the problem domain and develop a set of relevant questions you want answered. Develop general MR solutions to solve the problems. The data analysis and visualization should present a coherent interactive user interface for users to interactively specify/select the questions and parameters and get a visual response. For example, (i) we want to know how efficiently is the classroom space in Knox Hall used in the Spring semester of 2016? (ii) Create a dashboard of charts to convince the administrators that certain classrooms cannot be used for single day/one time reservations. (Knox 109 and Knox 110 are reserved for one time use reservation for Spring 2016.)

# Problem 1: Parallelizing data processing using MR (30%): 
Study the details of basic MapReduce provided in the Apache Hadoop documentation [4]. You can run the word count tutorial directly on your unix environment (machine/partition on your machine) or a VM that your TA has created. Then you will use the class room data provided and use MR to determine the rooms used in each hall for each semester. Example: outputs: <key, value> pair: (Key=<”Knox_Spring 2016”>, Value=6000). Output for this problem is all the rooms with years and capacity served. This could be done by reading a line, selecting the three needed tokens (room, year and capacity), setting the key with (room concatenated with year) and setting the value (capacity) and output the <key,value> pair. Firm deadline for this problem 1 is 4/2. Please do not submit data: Just the MR source code and sample input and real output.

# Problem 2: Design of questions (Experiments) (40%): 
Design a set of 20 questions that will provide insights into the situation of classroom scheduling at UB North Campus. This could be trends over the years, how the space needs have grown and how efficiently are the classrooms used, what is most popular time and day etc. Design MR code to solve these problems/questions and extract the answers. Submit the questions, output/results and also the MR source code. Firm date of completion for this part: 4/9.

# Problem 3: Building an analysis and visualization user interface (30%): 
Ingest the results of the MR analysis in the above parts to create visual insights into the data. Any user should be able to browse through a dashboard of charts that explain the overall status of classroom usage in UB North Campus. I am looking for Tableau like story or dashboards using Tableau, RShiny or other visual software you are familiar with. Firm deadline for this part is 4/16.
