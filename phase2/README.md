## Phase 2 Extensions
### Mandatory Extensions
- Add an extra template
  - Added Reminders Template
- Add the ability for one other kind of user to log in
  - Added Temporary account that is only kept in the system for 30 days
- Make at least one change to one of the templates from Phase 1 that results in a change to the way the user 
interacts with it
  - Added a prompt called to all templates to ask for the name of the planner when user creates a 
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
  - Upon being added as a friend, there should be immediate access to all of the friends-only content
- GUI using java.swing

### Create your own new features
- Added a "published" property to templates
  - Added the ability to change a template's "published" status 
  - Added the ability to show only the published templates


## Project Specification
### Users
#### Admin User
- Select a template, and change at least one thing about the template
- Able to change any of the users' planners from private to public or public to private
- Able to interact with every user's planners, even if they are private
  - can admin user revert change as well? QUESTION
- Able to delete a planner (e.g., if they decide that the content of the planner is not appropriate for the app)
  - can admin user undo delete as well? QUESTION
- Able to "suspend" a user by x number of days, where x is a number decided by the admin user. This means the user is 
not allowed to log in for x number of days

#### Regular User
- Select a template in order to create a planner that they can then interact with
- See a list of their own, previously created planners
- Interact with their own planners 
- Change their own planners to public, private, friends-only, or deleted
  - Able to un-delete a deleted planner
  - Able to revert their planner to its previous accessibility level (public, private, etc.)
  - Able to select other users for their friends list
  - Whenever they set a creation to "friends-only", any user on that list will have access to it
  - Upon being added as a friend, there should be immediate access to all of the friends-only content
- Interact with other users' planners that have been made "public"

#### Trial User
- Can do the same thing as regular users, but none of their data is stored
- They do not need a password to log in.

#### Temporary User
- Can do the same thing as regular users, but their account will only be kept in the program
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

## How to run the program
TBD
