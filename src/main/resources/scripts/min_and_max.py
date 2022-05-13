import pandas as pd
import sys 

# Find minumum and maximum of all test scores from "Scores" column
df = pd.read_csv('example.csv') 

print("The max of all scores are: " + str(df["Scores"].max()))
print("The min of all scores are: " + str(df["Scores"].min()))

