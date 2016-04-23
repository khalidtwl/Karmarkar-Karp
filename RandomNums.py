#
# USAGE: python3 RandomNums.py <# of numbers>
#

import random
import sys

f = open('nums.txt', 'r+')
numLst = []

numsToMake = int(sys.argv[1])
print(numsToMake)

# Generates a list of random numbers
for i in range(numsToMake):
    numLst.append(random.randint(1,10**12))

# Writes the numbers to the file
for x in range(len(numLst)):
    f.write(str(numLst[x]) + '\n')

f.close()
