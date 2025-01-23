# Pomodoro

** Feature
- Can set timer as Pomodoro (25', 5', 15')
- A ringtone when the time comes finish
- Count how many time for working, break
- Setting for timer
- Integrate with Restful Api to store data to cloud. Then, can look back history of time of working

** Architecture 
- Use Compose + MVI
- One Forground service to can run app in background
- Timer: Handle time running, pause, play
- PomodoroManager: Manage state of pomodoro (work, break, long break)
- PomodoroViewModel: Consume state from PomodoroManager and convert to UI state
- PomodoroScreen: Display UI state


** Image
![Screenshot_20250123_134310_Pomodoro](https://github.com/user-attachments/assets/1eab25f0-ee34-475b-9d7d-0718971bbe06)

![Screenshot_20250123_134343_Pomodoro](https://github.com/user-attachments/assets/3dcd2bcd-7963-4171-9618-0c2b7960dfd9)

![Screenshot_20250123_134408_Pomodoro](https://github.com/user-attachments/assets/7b99b8e2-e904-47cd-898b-b2f33de3ec91)

![Screenshot_20250123_134321_Pomodoro](https://github.com/user-attachments/assets/4c61e6e5-38ab-4cff-aad5-ff42c2cf827f)
