# Stage 2.  Q-team033-DianasDog

## **UML Diagram**

![img](https://lh3.googleusercontent.com/h1ND27Wzm-U38DOwZnpbuKgBhgArdwBA9qWQgjnXLrcTnabG5sKW-zwuuR_G0rF8wzL7AIpQLV5FBZrCLQm5qqUlCQzZoZ3-tK9Nu86CncddZA0JyuGHszNKsuv8Ktr981clihNd-RccA8OZnhbd5zFvqevMYEDxPUTUiimHantogisP8aX67HDkeQ)

## **UML Description**

### Entities

- The primary key (PK) of the `User` is `user_id`: We will generate a unique string for each user when they register for an account, and it is enough to define a tuple.

- The PK of the `Course` is `course_id`: We assume that each course has a unique course_id, so its alone is enough to define a tuple.

- The PK of the `Task` is `(task_id, course_id)`: A course may release several tasks. We assume that a task cannot exist on its own; it is a weak entity, and need to be defined with the attribute course_id from Course. Then course_id is a FK that references to `Course.course_id`.

- The PK of the `Team` is `(team_id, task_id, course_id)`: A task may be completed by several teams. We assume that a team cannot exist on its own; it is a weak entity and needs to be defined with the help of a Task. Since the key of Task is (task_id, course_id), we use (team_id, task_id, course_id) to define one tuple in the Team, where task_id, course_id are FKs that reference to `Task.task_id, Task.course_id` 

- The PK of the `Post` is `(post_id, task_id, course_id, owner_id)`: We assume that a post cannot exist on its own; it is a weak entity and needs to be defined with the help of a Task and a User. Since the key of Task is (task_id, course_id) and the key of User is user_id, we use (post_id, task_id, course_id, owner_id) to define one tuple in the Team, where task_id, course_id are FKs that reference to `Task.task_id, Task.course_id`, and owner_id is a FK that reference to `User.user_id`

### Relationships and cardinalities

- A User (identity: Student) can enroll in 0 to many Course
- A User (identity: Instructor) can instruct 0 to many Course
- A User can participate in 0 to many Team
- A User can own 0 to many Post

&nbsp;

- A Course can have (be assigned to by) 0 to many Task
- A Course can have (be enrolled in by) 0 to many User (identity: Student)
- A Course can have (be instructed by) at least one User (identity: Instructor)


&nbsp;

- A Task can have (be belonged to by) 0 to many Post
- A Task is assigned to exactly one Course
- A Task can have (be registered in by) 0 to many Team	

&nbsp;

- A Team has (be participated in by) at least one member
- A Team should register in exactly one Task


&nbsp;

- A Post belongs to exactly one Task
- A Post is owned by exactly one user


## **Relational Schemas**

- Entities:
  - User (<ins> user_id </ins>, identity, name, password, email_address)
  - Course (<ins> course_id </ins>, semester, course_name, register_code)
  - Task (<ins> task_id, course_id</ins>, type)
  - Team (<ins> team_id, task_id, course_id </ins>, max_size)
  - Post (<ins> post_id, task_id, course_id, owner_id</ins>, time_slot, weekend_availability, previous_experience, team_work_style)
 
- Relationships:
  - Enrollment (<ins> user_id, course_id </ins>)
  - Instructor (<ins> user_id, course_id </ins>)
  - Participation (<ins> user_id, team_id </ins>)


- Relational schemas:
  - User (user_id: varchar[255] [PK], identity: int, name: varchar[255], gender: int, password: varchar[255], email_address: varchar[255])
  - Course (course_id: varchar[255] [PK], semester: varchar[255], course_name: varchar[255], register_code: varchar[20])
  - Task (task_id: int [PK], course_id: varchar[255] [PK] [FK to Course.course_id], type: int)
  - Team (team_id: int [PK], task_id: int [PK] [FK to Task.task_id], course_id: varchar[255] [PK] [FK to Task.course_id], max_size: int)
  - Post (post_id: varchar[255] [PK], task_id: int [PK] [FK to Task.task_id], course_id: varchar[255] [PK] [FK to Task.course_id], owner_id: varchar[255] [PK] [FK to User.user_id], time_slot: varchar[255], weekend_availability: int, previous_experience: int, team_work_style: int)

  - Enrollment (user_id: varchar[255] [PK] [FK to User.user_id], course_id: varchar[255] [PK] [FK to Course.course_id])
  - Instructor (user_id: varchar[255] [PK] [FK to User.user_id], course_id: varchar[255] [PK] [FK to Course.course_id])
  - Participation (user_id: varchar[255] [PK] [FK to User.user_id], team_id: int [PK] [FK to Team.team_id])

## **Appendix**

### Questionnaire questions
  - Available time slots for weekly meeting(CT)
    - Monday - Sunday ( Store in JSON, VARCHAR(255)) 
      - Available Morning, Afternoon, Night (0, 0, 0)
  - Able to work on weekends
    - 0 - NO
    - 1 - YES
  - Previous project experience(0, 1, 2, 3)
    - 0 - None but willing to learn
    - 1 - Partly experienced
    - 2 - Fully experienced
    - 3 - Expert
  - Team’s work style
    - 0 - DDL pushes us
    - 1 - Open to any
    - 2 - As early as possible
