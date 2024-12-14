import pandas as pd
import sqlite3

# Replace 'flowers.xlsx' with your Excel file name
excel_file = 'flowers.xlsx'

# Read the Excel file into a DataFrame
df = pd.read_excel(excel_file, sheet_name='flowers_data')  # Replace 'Sheet1' with your actual sheet name if different

# Create a SQLite database connection
conn = sqlite3.connect('flowers1.db')  # 'flowers.db' is the output SQLite database file name
cursor = conn.cursor()

# Specify the name of the table you want to create or replace in the SQLite database
table_name = 'flowers_data'

# Write the DataFrame to the SQLite database table
df.to_sql(table_name, conn, if_exists='replace', index=False)

# Commit the transaction
conn.commit()

# Close the connection
conn.close()

print(f"Data from {excel_file} has been successfully converted to {table_name} table in flowers.db database.")
