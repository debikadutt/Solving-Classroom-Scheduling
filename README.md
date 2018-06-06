# Solving-Classroom-Scheduling
Developed a Hadoop Map-Reduce application which structured datasets of size 4GB collected from the University at Buffalo classrooms information database
CSE487 Data-intensive Computing Spring 2016
Due date: 4/16/2016 by 11.59PM or earlier, online submission on cse machines.
Goal: Learn Parallel Processing of Big Data using Hadoop MapReduce and a Build a Dashboard(s) for Analysis and Visualization of the Results
Context: Class room scheduling for courses is complex problem. It is all the more difficult in a department where the enrollments are increasing and number of courses and class sizes are increasing. Consider the case of this course (CSE4/587): I requested a larger room at the beginning of the semester. We have to send in a formal request through a departmental secretary and the reply comes a week later and it is always negative. For example they could not give a room larger than NSC 215 (150 cap) for this course. I requested a larger room for the midterm exam. They answer was negative. I found out that all the information about courses and classrooms is in a database and it is publicly available through a web site: http://www.buffalo.edu/class-schedule?semester=spring for example gives the Spring semester’s courses. A web crawler can get this information by scraping the web site resulting in very large unstructured text data. Any authorized person can also get this information directly from the database. That’s what we have. I will send you the link in a message to the class. DO NOT SHARE it with anybody beyond this course. Download and save this data in csv file as CourseRoom.csv. This is the main data set you will work with. You can get other data sets based on the needs of your analysis.
Problem Statement: Understand the data by doing an exploratory data analysis using RStudio. The data is for courses and class rooms from 1931 to 2017. Data analysis you perform will be driven by problems you want to solve and questions you want answered. Users for this “data product” are (i) course schedulers (ii) teachers teaching a course (iii) university planners and (iv) university development department for fund raising related activities.
Understand the domain and design a list of relevant questions. You are required to design and implement MR algorithms to extract useful intelligence to answer the questions. This intelligence will be offered as answers to questions through a web interface (dashboard). The answers to these questions could be through visualization, textual output or numerical information. The dashboard will feature ability for the user to interact by choosing from a set of questions and also by configuring the parameters for the questions and the visualizations. Sample questions to get you started are given below one for each type of user: (i) List all the class rooms of capacity greater than 200 for Mondays and Wednesdays between 5 and 6.20PM for Spring 2016 (ii) List a class room with capacity greater than 200 for May 9, 2016, 7-11pm (iii) track the trend in growth of seats per semester per building in the last 10 years in an interactive visualizations and (iv) compare enrollment increase over last 10 years to the building space increase in a visualization.
More specifically we want to analyze the data and provide a presentation of the charts (in the form of the dashboards) explaining the usage of the spaces (classrooms). How it has changed, what is trending, who is using the classrooms. As one of the Assistant Dean stated (I am paraphrasing here), “.. the classrooms ought to be continuously scheduled.”. Are the rooms being efficiently used? Why was
