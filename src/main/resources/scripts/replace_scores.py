from datetime import datetime
import pandas as pd
import sys 
import csv
import json
import ast
# Path to CSV file is encapsulated in the argument to the script

# print(sys.argv[1])
# Dictionary returned by javascript code on frontend
new = ast.literal_eval(sys.argv[1])
og = pd.read_csv(sys.argv[2]) # Original CSV file

# print("replacing")
# print(og.set_index('Student ID').to_dict()['Scores'])
# print("with")
# print(new)

a = new

final = []

# Open original CSV file and iterate through contents
with open(sys.argv[2], 'r') as csvfile:
    datareader = csv.reader(csvfile)
    for row in datareader: # python list for each line in csv file
        if row[0] != 'Email': # ignore the first row  
            # for each key:value pair in dictionary, check to see if current csv line has that key and value and change accordingly
            for key, value in a.items(): 
                if row[1] == key:
                    row[4] = value;
        final.append(row) # add each array to list of arrays

tme = datetime.now().strftime("%Y_%m_%d_%H_%M_%S")
name = (sys.argv[2]).replace(".csv", "") + tme + ".csv" # generate new csv file name

myfile = csv.writer(open(name, "w", newline='')) 
for row in final:
    myfile.writerow(row) # write all rows in final to new csv

print(name.replace('.\\', "")) # Output csv file name