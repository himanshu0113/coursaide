# coursaide

Vision: To design a TA management and Course-Selection application that helps resolve time-table based conflicts for students of IIIT-Delhi.

Features: The application will provide user profiles for students, and a separate profile for system admins. 
User Profile

# Student Profile

Create Profile: The student will be asked to provide details such as Name, Roll Number, Subjects Completed, Semester Number, and Specialisation. A login password will be generated, and can be changed by the user on first login (or any subsequent login). The username will be the same as the Roll Number, and hence unique. A basic security requirement is that one roll number can only be used to make one profile. 
Course Selection: The system will help the user (student) decide possible combinations of courses that can be chosen in a particular semester to satisfy the personal requirements of the user.

Input
Credit Requirement: 12
Specialisation Credits required (if applicable): 4

Output
Combination 1: Course 1, Course 2, Course 3 – Personal Time Table
Combination 2: Course 1, Course 2, Course 3 – Personal Time Table
.
.
.


Clash Checker: This feature will allow the student to enter the list of courses that she wants to choose. Based on the input, the time table will be checked for clashes and the system will display if there’s a clash or not. 

Input
Course 1
Course 2
Course 3
Course 4
TA subject (if applicable): TA Course

Output
Clashes: Yes
Clashing Subjects 
1. Course 1, Course 2
2. Course 3, Course 4

Or

Output
Clashes: No
See Personal Time Table (button)
TA Course Selection: Will help the student choose a course for her TA responsibility, based on personal requirements.

Input
Tutorial: Yes
Lectures: No
Lab: No
Levels Accepted: 100, 200, 300
Branches Accepted: CSE

Output
Course 1 – Personal Time Table
Course 2 – Personal Time Table
.
.
.

Or 
 
Input
Tutorial: No
Lectures: No
Lab: No
Levels Accepted: 100, 200, 300, 500
Branches Accepted: CSE, PSY

Output
Note: Course Subjects not selected yet. So, personal time table cannot be generated.
Course 1 – See Course Schedule
Course 2 – See Course Schedule
.
.
.


# Admin Profile

Create Profile: To create a user profile to be used by the admin(s) for all administrative features of the application.
Upload Time Table: The Admin can upload the Semester Time Table in a CSV or Spreadsheet format.
 _________________
|Choose File: Browse|
 ----------------------------
See Generated Time Table

Update Time Table: The Admin can upload an updated Time Table if any changes are made to the original. All the changes should reflect in the student profiles after updating. 


 _________________
|Choose File: Browse|
 ----------------------------
See Updated Time Table



