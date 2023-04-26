# Stage 6.  Q-team033-DianasDog

## Project Report

### 1.Changes in directions

> Please list out changes in the directions of your project if the final project is different from your original proposal (based on your stage 1 proposal submission).

Our project direction remains the same as our stage 1 proposal. Our team project is a web application of a teammate matching platform.

### 2.Achieved or failed to achieve usefulness

> Discuss what you think your application achieved or failed to achieve regarding its usefulness.

We successfully designed and implemented all of our main functionalities planned in our proposal, which includes the following modules:

- User: register, login, email verification, user profile
- Course: create/delete/join/quit a course, create/view tasks for courses
- Team: join/quit a team, invite to a team, view my teams
- Post: add/delete/view a post

### 3.Changes in schema or data source

> Discuss if you changed the schema or source of the data for your application

We changed the schema during the development process. In stage 2, the schema of the “User” table was User (user_id, identity, name, password, email_address). However, we later added two more attributes: the *verification_code* and the *is_enabled* to help implement the email verification feature of the user module. The email verification can block users who do not have an Illinois email account and prevent them from accessing the web application. With the *is_enabled* attribute, the application can check whether a user is permitted to access other web pages during each login. Thus, we also add data for these two new attributes. We updated all existing tuples by setting *verification_code* to *null* and *is_enabled* to *true*. Nonetheless, we use the actual emulation values when adding new tuples, like registering from the front end.

We also modified the Task table. It was designed initially as (task_id, course_id, type, max_size). However, during the actual implementation, we notice that the situation, in reality, is more complex. An instructor may wish to add more attributes to tasks. For instance, a real-life job or assignment usually has a deadline. Besides, instructors may hope to limit the number of students in one team. We also provide a text space to instructors in case they want to give some guidelines for a task. Therefore we added three additional attributes: *min_size*, *description*, and *ddl*. To change the data in the database in a convenient approach, we set *min_size* to 1, *description* to “This is a description,” and *ddl* to “2022-12-31” for all existing tuples. Nevertheless, we also use the actual emulation values when adding new tuples, like submitting an add-new-task request from the front end.

The Post table was modified too. We think that *created_time* and *visited_count* are not useful in this module. For *created_time*, we planned to do an auto-increment on *post_id*. So, when a new post is added, it will automatically be assigned to the current maximum *post_id* plus 1. It will generate a new maximum *post_id* and we know that it was created last. For *visited_count*, we also think that it is useless because we are not going to order posts by popularity. We do not want students to just group with the most popular one, and we want diversified teams to be created. In the end, other than *team_work_style*, *previous_experience*, and *weekend_availability*, we decided to add another attribute to the Post table. The attribute is *additional_comments*. We want to give students more flexibility to post their opinions and find ideal teammates to work with. After reading specific requirements, they are likely to match a team better and work smoothly.

### 4.Changes in ER diagram and/or table implementations 

> Discuss what you change to your ER diagram and/or your table implementations. What are some differences between the original design and the final design? Why? What do you think is a more suitable design? 

We changed the schema during the development process. In stage 2, the schema of the “User” table was User (user_id, identity, name, password, email_address). However, we later added two more attributes: the *verification_code* and the *is_enabled* to help implement the email verification feature of the user module. The email verification can block users who do not have an Illinois email account and prevent them from accessing the web application. With the *is_enabled* attribute, the application can check whether a user is permitted to access other web pages during each login. 

We also modified the Task table. It was designed initially as (task_id, course_id, type, max_size). However, during the actual implementation, we notice that the real-world situation is more complex. An instructor may wish to add more attributes to tasks. For instance, an assignment usually has a deadline. Besides, instructors may hope to limit the number of students in one team. We also provide a text space to instructors in case they want to give some guidelines for a task. Therefore we added three additional attributes: *min_size*, *description*, and *ddl*.

Other than the above changes, we also added another attribute in the Post table named “additional_comments.” Since post owners might have their own preferences that are not provided by the default choices, we decided to give them more freedom to enter any text they want in a post. Doing this will maximize the flexibility and realness of the post module because we let users post what kind of teammates they are looking for.

We think our final design is more suitable. At stage 2, we did not begin the implementation of our application, so we made the schemas as simple as possible. We planned to modify our schemas later if needed. Additionally, we discussed and agreed that adding attributes was much more convenient than deleting and adding features. It reduces our workload because we do not need to add extra methods to check the conditions in the back end, and it also causes fewer code conflicts.

### 5.Changes in functionalities

> Discuss what functionalities you added or removed. Why?

We implemented most of the functions designed but changed a few of them. First, the function to export a list of teams and students is removed due to time constraints. We altered this function by implementing an additional webpage with Echarts and a table to exhibit the registered teams. Echarts represent the overall distribution and progress of current teams of a particular task. The table lists all groups, including the team id, team members’ names, and student ids. In this way, the essential feature of looking up a list of current teams and their details is still implemented. In the following work after this class, we will continue to polish the application and add more features, such as the function to export a list of teams and students.

### 6.Advanced database programs

> Explain how you think your advanced database programs complement your application.

The trigger helps us add more constraints on the user's action of joining a team. Using triggers eliminates several undesirable corner cases in this workflow. For the stored procedures, we use them in a very complex database modification case: generating teams under a certain task and randomly adding students to those teams. Writing methods in the back end to handle it will need the back end to communicate with the database multiple times. However, the stored procedures can avoid this overhead.

### 7.Technical challenges of team members

> Each team member should describe one technical challenge that the team encountered. This should be sufficiently detailed such that another future team could use this as helpful advice if they were to start a similar project or where to maintain your project. 

- Bingchang Xu:

  The technical challenge is writing the stored procedures. In the initial design, there is only one stored procedure to handle the generate random team function. There are two loops in that procedure. One inserts team tuples in the Team table and the other randomly assigns students to a team in the first loop and inserts the team into the Participation table. It works well when we run it in the terminal, however, it fails when we run it in the back end. The problem is caused by some unrevealed reasons. The essence is that the newly inserted tuples in the first loop are not available in the second loop, although they are in the same procedure. Thus, our workaround solution is to split these two functions into two procedures to avoid this problem. But there will be concurrency issues, which should be considered as future works.

- Wenxin Dang:

  One of the challenges is debugging. Since we are building both the front end and the back end, the project is complicated. The project comprises several frameworks, and several steps may go wrong. Sometimes, we struggled with finding where the bug was. For instance, when the front end does not return the result we want, it may be because 1) the database query is wrong, 2) the service in the back end does not handle the result from the database well, 3) the controller failed to send it to the front end; 4) the front end does not handle it properly. Therefore, we divided the project into several layers and checked the result returned at each layer. To attain this goal, we learned and used several auxiliary applications. For example, we used Postman to check whether the back end returns the correct response by emulating requests sent from the front. Through this method, we improved our development efficiency.

- Yanlin Liu:

  The biggest challenge for me is to write the front-end part of my module. I am familiar with Java and know some basic ideas about Spring Boot, our back-end framework. But I have little to no knowledge of the front end. I struggled quite a bit to learn and develop the front end. For example, one of the major challenges I met was writing the layout of the web page. I had to do a lot of searches for VueJS tutorials and Element-UI examples to build my web pages. I learned a lot during this process and gained valuable hands-on experience in coding the front end. In addition, my advice for the future team doing this project is to take CS 409 - The Art of Web Programming prior to this course or with this course to have a smooth development experience. 

- Zheng Zou:

  The difficult technical challenge that I encountered was how to display information naturally and intuitively in the front end. I am responsible for the post module, and I need to design an interface to show all the posts that match the users’ preferences. The first problem was showing all the posts. To isolate individual posts, after many trials and errors, I chose to place them in separate cards. The title of the card was the information of the post’s owner. The main body of the card was the post owner’s preference. In this way, other users will immediately know that this post owner wants a teammate that he/she needs. The second problem was filtering the posts according to the user's preferences. I chose to give users the freedom to apply any combination of any choices in work style, experience, and weekend times. They can simply check and uncheck boxes to apply filters, and they will see filtered results instantly. The whole processing logic was written in the front end rather than in the back end. So, the users do not need to wait for back-end results to return and will have a better experience. Also, the logic is flexible as we do not need to modify anything in the back end.

### 8.Other changes

> Are there other things that changed comparing the final application with the original proposal?

All the changes are shown in the sections above, there are no other major changes.

### 9.Future work

> Describe future work that you think, other than the interface, that the application can improve on

There are multiple concurrency issues in this application, especially in the join team and the generate random team functionality. Future works may use some concurrency control features to tackle it.There is either no interception or user-login-status check during the back-end and front-end communication, that is needed to be addressed to eliminate the potential security problems. A login status interceptor that checks Cookie or JWT can be added to the back end to handle this.We did very few security checks for the user input, such as user registration, login, post comments, and so on. A malicious user can attack our application easily using the SQL injection attack. One possible solution is adding an extra layer to filter user input, such as a blacklist of certain characters. 

### 10.Labor division and teamwork

> Describe the final division of labor and how well you managed teamwork. 

Labor division is approximately equal to about 25% per team member. Bingchang managed the team very well. He organized the overall structure of this project and divided the work wisely. As a result, everyone actively participated in the teamwork and worked efficiently. There was little duplicated work, and we all contributed equally to the project. 

Teamwork split is vertically in the functionalities-wise (each member is responsible for both the front end and the back end of his/her module): 

- Bingchang Xu: Team Module (25%)
- Wenxin Dang: Course Module (25%)
- Yanlin Liu: User Module (25%)
- Zheng Zou: Post Module (25%)



 ## Demo Video Link

https://drive.google.com/file/d/12hR1sLsn6hPQGXjtP5xu8J929CTDm4zF/view?usp=sharing 
