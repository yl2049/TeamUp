# Q-team033-DianasDog

## Title: Teamup! A Teammate Matching Platform

## Project Summary

&emsp; Our team proposes a teammate matching platform. On this platform, instructors can assign students to groups randomly or allow students to form their own groups. On the student's side, they can view their teams and their group members’ contact information. When self-assigned groups are allowed, students can submit their team information through this website. They can also post teammates finding announcements or look up messages to join a team. After teams are formed, instructors can view and download all teams’ information.

## Description

&emsp;  Many courses at UIUC involve group assignments. Students are expected to work together and learn collaboration skills. However, team formation is a prime issue. Sometimes instructors prefer to assign students to random groups so that students can meet and collaborate with more co-workers. Sometimes, instructors allow students to find team members on their own. Nonetheless, some students might be new to the school. They know very few people and cannot find classmates to pair with. Aditionally, courses at UIUC usually have a large class size, which increases the difficulty to manage or find teams. Thus, our team attempts to ease the process. We design a website that enables instructors to manage groups more efficiently and enables students to find more favorable teammates.

### Data Stored

For the starting and testing phase of this project, we plan to use randomly generated data for all our entities. After our team has done thorough testing, making sure our system work as expected, we will gather real-world data by inviting students to try out our system. For the user entity, attributes are user_ID as primary key, type, name, password, and email address. For the Course entity, attributes are course_ID as the primary key, semester, course_code, and course_name. Other entities and attributes can be found in this [UML](./UML_Diagram_1.pdf).  

### Basic Functions of Web Application

The platform has three identities : `Student`, `Instructor`, and `Administrator`. Each identity will have its own functions.

#### Basic functions of `Student`:

- View team & teammates info for all tasks in all courses registered.
- Post *Looking for teammates* messages
- Sort by condition & View all posts for a specific task in a course.
- Join & submit a team

#### Basic functions of `Instructor`:

- Create courses
- Manage tasks in a course
- Randomly assign students to groups for a specific task
- View & download all teams formed

#### Basic functions of `Administrator`:

- Authorize a user's identity to be "Student" or "Instructor"

### Creative Components

The main problems we imagined were how to make sure the realness of registered users and how to simplify the login process. We proposed that the university email will be the key to solving them. Upon registering, potential users must enter their netId@illinois.edu email on the webpage so our system can send a confirmation code to that address. After the user entered the correct confirm code, a user entity entrance will then be created. In addition to strictly filtering our user to ensure data realness, one important improvement to user experience can be one-time login links. Users might forget their passwords or be annoyed by retyping passwords many times. So they can simply click a button, and our system will send a login link to that email address(if exists in the databse). Then the user will click on the link and complete the login process. Other convenient tools like cookies can be used if our skill permits. For emails, our system will use SMTP(Simple Mail Transfer Protocol) to send out all the emails.

## Usefulness

At UIUC, students are currently using `campuswire` and `slack` to find teammates; using `google sheets` or `PrairieLearn` to sign up for teams.

Nonetheless, first of all, there are many other unrelated messages on `campuswire` and `slack`. Though we can create a task-specific room or channel, these web apps are not explicitly designed for teammate matching. In comparison, our team's design concentrates more on this purpose.

Second, students may post messages in various forms, which is not intuitive to browse around. Alternatively, we use a standard template to generate announcements. Students looking for teammates will fill out a survey, and the platform will create a post in uniformed format.

Third, students need an additional step to sign up for teams in `google sheets`, another application. However, we put two functions, finding groups and registering, in one.

Forth, our app support students in sorting and filtering posts based on their needs so that they can find favorable teammates more efficiently.

Fifth, we allow instructors to assign students into groups randomly.

With these features different from `campuswire`, `slack`, `google sheets`, `PrairieLearn`, we attempts to create a platform that matches students more intuitively and efficiently.

## Realness

We choose our entity and attributes based on real-world scenario. For example, each user has his or her name and email address; each course has its semester, code, and name; each post will store user-entered survey questions. In addition, our registered user are real University of Illinois personnels, which eliminates the chances of fake data. So, our data can represent reality and thus have decent realness.

In the initial development and testing phases, we plan to auto-generate  data for users, teams, posts, and use some data from [Kaggle](https://www.kaggle.com/datasets/siddharthm1698/coursera-course-dataset) to generate courses; in the later testing phases we will spread out invitation links and collect real data from actual users.

## Functionality

The Teamup! The system currently contains 4 parts: `User Module`, `Course Module`, `Team Module`, and `Post Module`. And there are three identity authorizations: `Student`, `Instructor`, and `Administrator` as actors interacting with the System. There will be helper modules and inherent actors in the incoming implementation stage.

`User Module` provides Registration and Login services for students, instructors, and administrators. The registration module will request users to register by using an illinois.edu email. The administrator can modify user identities. Also, there will be a simple profile for each user.

`Course Module` provides course-related functionalities. Instructors can create courses(with a random course code) and create tasks in different courses. Each task represents a round of team-up, and there are two types of tasks. One is for the random team-up (within all students registered for this course), and the other is for self-assigned groups. Students can use the course code to register for courses.

`Team Module` is for students to create teams for different tasks, review team members, leave teams, and invite students who registered for the course as new team members(through email links). When students accept to join another team, they will automatically leave the original team. Once a team contains no student, it will be removed by a trigger in the database. Instructors can view and download the teams' information for a specific task.

`Post Module` can help students network with others. For each self-assigned group task, there will be a bulletin board for students to post their team information or view other teams’ posts. If they want to join a team, they can reach out to students in the team by contact info in their profiles. Students can create their posts by finishing a series of simple selection questions or delete them by button-clicking.

## Low Fidelity UI Mockup

[Visit PDF](./s1_low_fidelity_UI_mockup.pdf)

## Project Work Distribution

 Backend systems will be distributed based on Modules

### Bingchang Xu

- Responsible for `User Module`

### Wenxin Dang

- Responsible for `Course Module`

### Yanlin Liu

- Responsible for `Team Module`

### Zheng Zou

- Responsible for `Post Module`
