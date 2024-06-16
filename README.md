# Pomodoro

** Architecture 
- Timer: Handle time running, pause, play
- PomodoroManager: Manage state of pomodoro (work, break, long break)
- PomodoroViewModel: Consume state from PomodoroManager and convert to UI state
- PomodoroScreen: Display UI state