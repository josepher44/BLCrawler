from stem.control import Controller
from stem import Signal
import random
import os
import subprocess
import time
import numpy as np

paths = []
args = []
controllers = []
cutoffs = []

for k in range(20):
    cmport = 9152+k
    paths.append("C:\Users\Owner\Desktop\cmdtor\\"+str(cmport)+"\Tor\\tor.exe")
    args.append("C:\Users\Owner\Desktop\cmdtor\\"+str(cmport)+"\Data\Tor\\torrc")
    cutoffs.append(random.randint(15,60))
    subprocess.Popen([paths[k], '-f', args[k]], shell=True)
    try:
        controllers.append(Controller.from_port(port = 9052+k))
        controllers[k].authenticate('forthetor')
    except:
        print("Issue happens at module "+str(9052+k))
        print("Issue happens at module "+str(9052+k))
        print("Issue happens at module "+str(9052+k))
        print("Issue happens at module "+str(9052+k))
        print("Issue happens at module "+str(9052+k))
        print("Issue happens at module "+str(9052+k))
        print("Issue happens at module "+str(9052+k))
        print("Issue happens at module "+str(9052+k))
        print("Issue happens at module "+str(9052+k))
        print("Issue happens at module "+str(9052+k))
        print("Issue happens at module "+str(9052+k))

print("TestPrint")



i=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
timesince = np.array(i)
print(cutoffs)

blacklistpath = "C:\Users\Owner\Documents\BLCrawler\Catalog\log\ipblacklist.txt"
with open(blacklistpath, 'r') as file2 :
    blacklistlast = file2.read()
blacklistappend = ""



while True:
    time.sleep(1)
    blacklistpath = "C:\Users\Owner\Documents\BLCrawler\Catalog\log\ipblacklist.txt"
    with open(blacklistpath, 'r') as file :
        blacklistlines = file.readlines()
    with open(blacklistpath, 'r') as file :
        blacklistcontents = file.read()
    #print(blacklistlines)




    if blacklistcontents.count('\n') > blacklistlast.count('\n'):
        blacklistappend = ""
        print(blacklistlines)
        for i in range(blacklistlast.count('\n')-2, blacklistcontents.count('\n')):
            blacklistappend = blacklistappend+" "+blacklistlines[i]
        blacklistappend = blacklistappend.replace('\n', '')
        blacklistappend = blacklistappend.replace('  ', '')
        blacklistappend = blacklistappend.replace(' ', ',')
        blacklistappend = blacklistappend[1:]
        print("New IPs Appended")
        blacklistlast = blacklistcontents
        print (blacklistappend)
        #Append new blacklist addresses to all torrc files
        for k in range(20):
            port = 9152+k
            path = "C:\Users\Owner\Desktop\cmdtor\\"+str(port)+"\Data\Tor\\torrc"
            # Read in the file
            with open(path, 'r') as filetor :
                filedata = filetor.read()

            # Replace the target string
            filedata = filedata.replace('ExcludeExitNodes', 'ExcludeExitNodes '+blacklistappend+",")

            # Write the file out again
            with open(path, 'w') as filetor:
                filetor.write(filedata)
            controllers[k].signal(Signal.HUP)

        print("Tor modules have been hupped")

    timesince+=1
    cutoff = random.randint(5,25)
    module = random.randint(0,19)
    for idx in range(20):
        if timesince[idx]>cutoffs[idx]:
            timesince[idx]=0
            cutoffs[idx] = random.randint(15,60)
            controllers[idx].signal(Signal.NEWNYM)
            print("Tor "+str(9152+idx)+" reset, rand was "+str(idx))
