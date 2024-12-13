import pandas as pd
import sqlite3

# Function to convert Excel file to SQLite database
def excel_to_sqlite(excel_file, sheet_name, db_name, table_name):
    # Read the Excel file
    df = pd.read_excel(excel_file, sheet_name=sheet_name)
    
    # Connect to SQLite database (or create it if it doesn't exist)
    conn = sqlite3.connect(db_name)
    cursor = conn.cursor()
    
    # Write the DataFrame to the SQLite table
    df.to_sql(table_name, conn, if_exists='replace', index=False)
    
    # Commit and close the connection
    conn.commit()
    conn.close()

# Example usage
excel_file = 'data.xlsx'  # Replace with your Excel file name
sheet_name = 'Sheet1'             # Replace with your sheet name if it's not 'Sheet1'
db_name = 'flowers.db'            # Desired SQLite database file name
table_name = 'flowers_data'       # Desired table name in the SQLite database

excel_to_sqlite(excel_file, sheet_name, db_name, table_name)
