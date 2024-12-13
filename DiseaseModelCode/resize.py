import os
from PIL import Image

def resize_and_rename_images(folder_path):
    # Get the folder name
    folder_name = os.path.basename(folder_path)
    
    # List all files in the folder
    files = os.listdir(folder_path)
    
    # Initialize a counter
    counter = 1
    
    for file_name in files:
        file_path = os.path.join(folder_path, file_name)
        
        # Open an image file
        with Image.open(file_path) as img:
            # Resize image
            img = img.resize((224, 224))
            
            # Save it back to the same path with new name
            new_file_name = f"{folder_name}_{counter}.jpg"
            new_file_path = os.path.join(folder_path, new_file_name)
            img.save(new_file_path)
            
            # Remove the old file
            os.remove(file_path)
            
            counter += 1

# Example usage
resize_and_rename_images('Validation/Rust')