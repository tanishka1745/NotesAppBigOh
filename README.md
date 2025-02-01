A simple Notes App that allows users to create, edit, and delete notes with a beautiful, user-friendly interface. Each note consists of a title and content, and users can add colorful backgrounds to each note dynamically.

# Features
Create Notes: Users can add new notes by entering a title and content.
Edit Notes: Users can modify the title or content of an existing note.
Delete Notes: Notes can be deleted through a confirmation dialog.
Colorful Notes: Each note has a random background color generated dynamically.
Custom Dialogs: A confirmation dialog pops up when trying to save or delete a note, ensuring the user’s intention is clear.
Persistent Storage: Notes are stored in a local database using Room Database, ensuring data persistence even after the app is closed.
Rounded Card Views: Notes are displayed in card views with rounded corners for a modern look.
Material Design: The app follows Material Design principles for a clean and attractive interface.


# Technologies Used
Kotlin: The app is developed using Kotlin, leveraging its modern features and conciseness.
Room Database: For storing the notes persistently.
CardView: To display notes in rounded cards with shadow effects.
Custom Dialogs: Dialog boxes with beautiful button colors to confirm actions.
ViewModel: To manage UI-related data in a lifecycle-conscious way.
LiveData: To observe changes to the data and update the UI accordingly.
RecyclerView: For displaying the list of notes dynamically.
Random Color Generator: To assign a random background color to each note for a beautiful appearance.


# ScreenShot

![WhatsApp Image 2025-02-02 at 12 56 17 AM](https://github.com/user-attachments/assets/34570e23-94d2-4bc6-ad3b-fc2d18ae1924)


# Clone the repository:

bash
Copy
Edit
git clone https://github.com/yourusername/notes-app.git
Open the project in Android Studio:

Import the project into Android Studio.
Make sure to sync the project with Gradle.
Run the app:

Connect an Android device or use an emulator.
Build and run the app to see it in action.
Installation
Ensure you have the following software installed:

Android Studio (latest version)
JDK (Java Development Kit) 8 or above
Dependencies:

Room Database
CardView
ViewModel
LiveData
