Authors: Jinyoung Lee, Raazia Hashim, Xiaoxue Yang, Xueqing Zhai, Runlong Ye, Zeyang Ni, Zifan Ye

## How to run the program
To run the program, run the main method in Main.java.\
Make sure to not touch the data folder sitting directly inside the phase2 folder, as it contains external files that the 
program will need to read in. 

## A List of Phase 2 Extensions
### Mandatory Extensions
- Add an extra template
  - Added Reminders Template
- Add the ability for one other kind of user to log in
  - Added Temporary account that is only kept in the system for 30 days
- Make at least one change to one of the templates from Phase 1 that results in a change to the way the user 
interacts with it
  - Added a prompt to all templates to ask for the name of the planner when user creates a 
  planner from a template
- Allow admin users to change any of the users' creations from private to public or public to private
- The admin user should be able to interact with every user's creations, even if they are private. If the admin user 
decides that the content of the creation is not appropriate for the app, they should be able to delete the creation
- The admin user should also be able to "suspend" a user, which means the user is not allowed to log in for x number of 
days, where x is a number decided by the admin user

### Optional Extensions
- Optional Extension #1: Create a more elaborate password system
  - Let a user create a new account by entering their email address as a username and then choose a password
  - Create criteria for a password being "too weak", "weak", or "good"
  - Do not allow the user to choose a password that is less than "weak"
  - Allow users to change their password. A user can request an "email" that creates a text file with a temporary 
  password that the user can use to log back into the system before changing it to a more permanent password
- Optional Extension #3:
  - Users can have the ability to delete their own creations, make the creations public, private, friends-only, or 
  deleted
  - There should be an undo feature that lets users un-delete a deleted creation and revert any creation to its 
  previous accessibility level (public, private, etc.)
  - The friend-only setting allows users to select other users for their friends list. Whenever they set a creation to 
  "friends-only", any user on that list will have access to it
  - Upon being added as a friend, there should be immediate access to all the friends-only content
- GUI using java.swing

### Create your own new features
- Added a "published" property to templates
  - Added the ability to change a template's "published" status (admin users only)
  - Added the ability to show only the published templates
- Admin users can now create templates of an existing template type (daily, project, or reminders). A newly created 
template should still be asking for the same things as an existing template, but they have the freedom of phrasing the
template prompts differently or even in a different language.

## Design Patterns
In this program, we used the following design patterns.
### Dependency Injection
Here we will give one example of using Dependency Injection in the program.
- Which classes were involved?\
  GeneralUI, AccessController, TemplateController, and Planne.\rController.
- How was this design pattern implemented and why did we use it?\
In the GeneralUI class (the parent class for all GUI classes), we call the constructor for all three controllers. \
And in each of the controllers, we have setter methods which take in an instance of another Controller. \
<br/>
This way, we are not calling constructors of the other controllers in every Controller class, and the GeneralUI class is 
the only place where we call those constructors. So if we need to change the constructor of a Controller class, the
GeneralUI class is the only place we need to change. \
<br/>
This eliminates the dependency between Controller classes, and makes the program more Open/Closed.

### Builder
- Which classes were involved?
  - IForm: An interface with a list of required methods for returning components of a Form.
  - Form: A class that implements IForm. A Form is a JPanel with a GridLayout 
  (every component in a Form will be displayed in a vertical grid)
  - FormBuilder: Builder class that builds Form objects.
- How was this design pattern implemented and why did we use it?\
This design pattern was implemented because we have a lot of GUI classes (one GUI class for each type of actions to keep the code
compartmentalized and follow the open/closed and Single Responsibility design principles). Many of the GUI classes have 
a Panel with a grid-style layout (e.g., all the components such as buttons, labels, text fields are displayed one next to each 
other vertically). To construct such a Panel, we need to add all of those components, set their bounds on the screen,
add Action Listener so the components can respond to an event (like clicking a button), etc. \
<br/>
So it is a complicated process to construct a such a Panel in a GUI class, and we need to construct at least one Panel 
in every GUI class. \
<br/>
Therefore, by having a builder class and having different GUI classes call the builder methods that they need in order, \
we encapsulate the process of building a Panel from the rest of the program. It also helps us keep the layout (including 
bounds on the screen, font, etc.) as uniform as possible across all GUI classes. \
<br/>
The type of object returned by getForm() method in the FormBuilder is IForm, this way, GUI classes will depend on \
abstraction instead of a specific class. This makes the program more Open/Closed as we can easily replace a FormBuilder 
with another, if we want to add different kinds of forms (layouts) in the future.

### Factory Method
- Which classes were involved? \
We have two Factory Methods in the program.
  - One Factory Method is the getTemplate() method in TemplateManager, it houses all the constructor calls to create Template entities.
  - The other Factory  Method is the getPlanner() method in PlannerManager, it houses all the constructor calls to create Planner entities. 

- How was this design pattern implemented and why did we use it?\
Here we will explain in the context of Template. Planner follows the same reasoning. \
<br/>
DailyTemplate, ProjectTemplate, and RemindersTemplate all extend Template. They are all Entity classes. 
They all have a common set of methods with the same signature, such as isType(), retrievePrompts(), etc. There is no 
Template subclass that has any public methods that other Template subclasses don't have (i.e., all Template objects 
have the same set of functionalities, and can provide the same set of services - it's only the implementation of those 
services that varies based on the type of the Template). \
<br/>
TemplateManager is a Use Case class, it's responsible for creating and storing Template objects. 
Inside TemplateManager, there is a getTemplate() method which is the Factory Method, it is responsible for housing all 
the constructor calls for DailyTemplate, ProjectTemplate, and RemindersTemplate. 
The getTemplate() method takes in a string that represents which subclass of Template should be created, and it returns 
an object of type Template after calling the constructor of that specific subclass. There is a method called 
createTemplate() inside TemplateManager, which calls getTemplate() to create Template objects and then store them 
inside TemplateManager. \
<br/>
Since admin users have the power to create templates in the program, we don't know exactly which type of template is
going to be created. So when there is a need to create a template, we will get the type of the template that the user
wants to create and all the required inputs, and use the getTemplate() factory method to create the desired template. 
No other method or class needs to depend on the constructors of Template objects. \
<br/>
This works in our design, because all Templates have the same set of functionalities and the same public methods with 
the same signature (but with different implementation) that can be called by the rest of the program. For example, when 
we need to create a Planner based on a Template, we can get the Template's type by calling its isType() method, 
since isType() is implemented by all Template subclasses, when it gets called, the subclass's isType() method 
overrides and returns the correct type of Template, and the corresponding type of Planner can be created accordingly.

## How our code has improved since Phase 1
We improved our code following all the feedback given by our TA for phase 1:
- Reorganized README file, changed its format from .txt to .md for better visuals, included more details but tried to 
keep everything clear and easy to follow in bullet forms
- Changed all package names to lowercase
- Made variables as abstract as possible. Changed ArrayList to List, HashMap to Map, etc.
- Split the big classes into several smaller classes. For example, AccountManager was already big in Phase 1, but it 
became even bigger after we added the Phase 2 functionalities. So we moved the methods related to password into another 
class, methods related to friends to another class, and methods related to planners to another class. This way, each of 
these classes can be reused separately.
- Cleaned up code that's commented out, print statements, dead code, etc.
- Added design patterns, as specified above.

## Project Specification
### Users
#### Admin Users are able to:
- Select a template, and change the template name, template "published" status
- Create a new daily, project, or reminder template (the prompts in a new template should be asking for a set
of things in a set order for that type of template; however, users have the freedom to phrase the prompts the 
way they want, as long as the prompts are asking for the required set of things)
- Change any of the users' planners from private to public or public to private
- Interact with every user's planners, even if they are private
- Delete any planner, including private planners (e.g., if they decide that the content of the planner is not 
appropriate for the app)
- "Suspend" a user by x number of days, where x is a number decided by the admin user. This means the user is 
not allowed to log in for x number of days

#### Regular User are able to:
- Select a template in order to create a planner that they can then interact with
- See a list of their own, previously created planners
- Interact with their own planners 
- Change their own planners to public, private, friends-only, or deleted
  - Able to un-delete a deleted planner
  - Able to revert their planner to its previous accessibility level (public, private, etc.)
  - Able to select other users for their friends list
  - Whenever they set a creation to "friends-only", any user on that list will have access to it
  - Upon being added as a friend, there should be immediate access to all the friends-only content
- Interact with other users' planners that have been made "public"

#### Trial User are able to:
- Do the same thing as regular users, but none of their data is stored
- They do not need a password to log in.

#### Temporary User are able to:
- Do the same thing as regular users, but their account will only be kept in the program
for 30 days, they can no longer access it after 30 days.

### Templates
#### Daily Template
It has the following prompts:
- Name of the planner
- Start time of the planner
- End time of the planner
- Time increment of the planner
#### Project Template
It has the following prompts:
- Name of the planner
- First status column of the planner (e.g., TODO)
- Second status column of the planner (e.g., DOING)
- Third status column of the planner (e.g., DONE)
#### Reminders Template
It has the following prompts:
- Name of the planner
- Task name heading of the planner (e.g., "Tasks", "Tasks left for assignment", etc.)
- Date heading of the planner (e.g., "Deadline")
- Completion status heading of the planner (e.g., "Completion Status")

### Planners
#### Daily Planner made from Daily Template
#### Project Planner made from Project Template
#### Reminders Planner made from Reminders Template

