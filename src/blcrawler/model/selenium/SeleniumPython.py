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

while True:
    time.sleep(1)
    timesince+=1
    cutoff = random.randint(5,25)
    module = random.randint(0,19)
    for idx in range(20):
        if timesince[idx]>cutoffs[idx]:
            timesince[idx]=0
            cutoffs[idx] = random.randint(15,60)
            controllers[idx].signal(Signal.NEWNYM)
            print("Tor "+str(9152+idx)+" reset, rand was "+str(idx))
