import os

def count_items_in_subdirectories(directory):
    for subdir in os.listdir(directory):
        subdir_path = os.path.join(directory, subdir)
        if os.path.isdir(subdir_path):
            num_items = len(os.listdir(subdir_path))
            if num_items < 300:
                print(f"Folder '{subdir}' has {num_items} items.")

# Replace 'your_directory_path' with the path to the directory you want to check.
directory_path = 'dataset'
count_items_in_subdirectories(directory_path)