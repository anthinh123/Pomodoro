# Pomodoro

** Feature
- Can set timer as Pomodoro (25', 5', 15')
- A ringtone when the time comes finish
- Count how many time for working, break
- Integrate with Restful Api to store data to cloud. Then, can look back history of time of working

** Architecture 
- Use Compose + MVI
- One Forground service to can run app in background
- Timer: Handle time running, pause, play
- PomodoroManager: Manage state of pomodoro (work, break, long break)
- PomodoroViewModel: Consume state from PomodoroManager and convert to UI state
- PomodoroScreen: Display UI state
