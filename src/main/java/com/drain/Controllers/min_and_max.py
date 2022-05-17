import pandas as pd
import sys 

# Find minumum and maximum of all test scores from "Scores" column
df = pd.read_csv("C:\\Users\\akshay\\Desktop\\CSA\\CSA_Projects\\Drain-Gang\\src\\main\\java\\com\\drain\\Controllers\\example.csv") 

print("The max of all scores are: " + str(df["Scores"].max()))
print("The min of all scores are: " + str(df["Scores"].min()))

