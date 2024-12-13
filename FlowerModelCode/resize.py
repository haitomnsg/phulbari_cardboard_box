import os
from PIL import Image

# Function to resize images in a directory and rename them sequentially
def resize_and_rename_images(input_dir, output_dir, target_size, max_files=300):
    # Ensure the output directory exists
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    # Iterate over all subdirectories in the input directory
    for subdir in os.listdir(input_dir):
        subdir_path = os.path.join(input_dir, subdir)
        
        # Check if it's a directory
        if os.path.isdir(subdir_path):
            # Create corresponding subdir in output directory
            output_subdir = os.path.join(output_dir, subdir)
            if not os.path.exists(output_subdir):
                os.makedirs(output_subdir)

            # Initialize counter for renaming
            rename_counter = 1

            # Iterate over all files in the current subdirectory
            for filename in os.listdir(subdir_path):
                # Stop processing if the counter exceeds the maximum number of files
                if rename_counter > max_files:
                    break

                file_path = os.path.join(subdir_path, filename)
                
                try:
                    # Open an image file
                    with Image.open(file_path) as img:
                        # Resize the image
                        img_resized = img.resize(target_size, Image.LANCZOS)
                        
                        # Define the output file path with sequential renaming
                        new_filename = f"{subdir}_{rename_counter}.jpeg"
                        output_file_path = os.path.join(output_subdir, new_filename)
                        
                        # Save the image in JPEG format
                        img_resized.save(output_file_path, "JPEG")
                        
                        print(f"Processed {filename} -> {output_file_path}")
                        
                        # Increment counter for next image
                        rename_counter += 1
                except Exception as e:
                    print(f"Failed to process {filename}: {e}")

    print("Processing completed.")

# Example usage
input_directory = 'dataset'
output_directory = 'dtaaset_resized_256'
target_size = (256, 256)

resize_and_rename_images(input_directory, output_directory, target_size)
