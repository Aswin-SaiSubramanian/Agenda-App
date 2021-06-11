# My Personal Project : Student Agenda

An app simulating an annual agenda where each day of the year gets an agenda page, and each
agenda page can hold a collection of tasks and their details, including task names, start times, and end times. Scheduling 
conflicts are avoided by simply not allowing new tasks that overlap in time with already scheduled tasks to be 
entered.

## User Stories
- As a user, I want to be able to add a task onto a day's schedule.
- As a user, I want to be able to remove a task from a day's schedule.
- As a user, I want to be able to view the task scheduled at a certain time in the day.
- As a user, I want to be able to view a day's schedule.

- As a user, I want to be able to save to file, changes I made to my agenda, when I exit the application.
- As a user, I want to be able to load from file, saved changes of my agenda. 

## Design
The data of the agenda is managed by the classes Task, ScheduleForDay, and ScheduleForYear. ScheduleViewer and Main 
manage user interaction, and the remaining classes represent the GUI. Data persistence (saving and reloading the 
agenda) is implemented to modify one agenda page at a time, and therefore acts on the ScheduleForDay module.
![UML Design Diagram](/docs/UML_Design_Diagram.png)

## Tour of the App
- Add a task onto a day's schedule by following these steps:
 1. Select "no" on the load options window, to load a new agenda.
 ![App tour step 1](/docs/img/demo-step1.PNG)
 2. Select a month to go to, from the pull down menu on the right-hand side of the resulting window.
 ![App tour step 1](/docs/img/demo-step2.PNG)
 3. Select a date to go to, from the resulting calendar window.
 ![App tour step 1](/docs/img/demo-step3.PNG)
 4. Type the name of a new task in the text field named that asks for it, on the resulting agenda page.
    - Then press the "enter" key on the keyboard.
 ![App tour step 1](/docs/img/demo-step4.PNG)
 5. Select the start hour, start minute, end hour, and end minute, on the agenda page
 ![App tour step 1](/docs/img/demo-step5.PNG)
 6. Click "Add task" at the bottom of the agenda page.
 ![App tour step 1](/docs/img/demo-step6.PNG)
 7. To see that the task has been added, click on "View schedule for the day" in the menu at the top of the page.
 You should see the task you created, along with its start hour listed. 
 
 - To remove a task from a day's schedule follow these steps:
 (For convenience, start from the same agenda page where you previously added a task.)
 
 8. If you closed the agenda page, open it again from the calendar window. On the menu at the top of the agenda page,
  select "View schedule for the day". A list of tasks and their start times will appear.
 9. Click on the task you would like to remove. Details of the task, and a red "Remove" button should appear.
 ![App tour step 1](/docs/img/demo-step9.PNG)
 10. Click on the "Remove" button.
 ![App tour step 1](/docs/img/demo-step10.PNG)
 
 - Can save the state of this application by clicking on the green "Save" button, 
 located in the menu at the top of all the agenda pages.
 
 - Can reload the state of this application by selecting the "yes" button when it is opened and asks if you would like 
 to load agenda.
  
## Testing and Designing a Robust App
- Class made more robust: ScheduleForYear
- Method that throws exception: public ScheduleForYear(ArrayList<ScheduleForDay> daySchedules) ... {...}
- Exception thrown: IncorrectNumberOfAgendaPagesException
- Class in which this is tested: ScheduleForYearTest
- Relevant test methods: testOverloadedConstructorExceptionNotExpected(), testOverloadedConstructorExceptionExpected()    

## Decreasing Coupling
- change 1: decreased coupling by removing dependency to ScheduleForDay from TaskPanel. This was done by adding a
a getter function for daySchedule (a field of type ScheduleForDay) to ScheduleViewer, and using that in TaskPanel.
- change 2: decreased coupling by removing dependancy to ScheduleForDay from TaskAddingPanel, by the same means
described in change 1.  

