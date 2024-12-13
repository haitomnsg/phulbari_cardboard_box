import os

# Specify the directory path
directory = 'D:/project_apple/dataset/flowers (Uncleaned)'

# Get all the folder names inside the directory
folder_names = [name for name in os.listdir(directory) if os.path.isdir(os.path.join(directory, name))]

# Export the folder names to a txt file
with open('folder_names.txt', 'w') as file:
    for name in folder_names:
        file.write(name + '\n')