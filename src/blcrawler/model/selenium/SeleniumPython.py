from stem.control import Controller
from stem import Signal
import random
import os
import subprocess
import time

paths = []
args = []
controllers = []

for k in range(20):
    cmport = 9152+k
    paths.append("C:\Users\Owner\Desktop\cmdtor\\"+str(cmport)+"\Tor\\tor.exe")
    args.append("C:\Users\Owner\Desktop\cmdtor\\"+str(cmport)+"\Data\Tor\\torrc")
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


i=0
while True:
    time.sleep(1)
    i+=1
    cutoff = random.randint(15,45)
    module = random.randint(0,19)
    if i >=cutoff:
        controllers[module].signal(Signal.NEWNYM)
        print("Tor "+str(9152+module)+" reset, rand was "+str(module))
        cutoff = random.randint(15,45)
        module = random.randint(0,19)
        i=0
