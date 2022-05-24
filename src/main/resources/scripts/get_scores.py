import pandas as pd
import sys 

# Path to CSV file is encapsulated in the argument to the script
df = pd.read_csv(sys.argv[1])

print(df.set_index('Student ID').to_dict()['Scores'])
