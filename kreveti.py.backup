#!/usr/bin/env python

import RPi.GPIO as GPIO
import time
import sqlite3
from threading import Thread
GPIO.setwarnings(False)

INPUT_PINS = [21,32,36,38,40]
OUTPUT_PINS = [29,31,33,35,37]

GPIO.setmode(GPIO.BOARD)

NumberOfButtons = len(OUTPUT_PINS) * len(INPUT_PINS)
ACTIVE_PINS = []

connection_locked = False

def UpdateDatabase(lista):
        db_conn = sqlite3.connect('/home/pi/PatientDB.db')
        cur = db_conn.cursor();
        print "Thread inserting..."
        print lista
        cur.executemany("INSERT INTO patient_log(serviced, request_time, bed_id) VALUES('NE',datetime(CURRENT_TIMESTAMP,'localtime'),?)",lista)
        db_conn.commit()
        db_conn.close()

for i in OUTPUT_PINS:
	GPIO.setup(i,GPIO.OUT)
	GPIO.output(i,GPIO.HIGH)
for i in INPUT_PINS:
	GPIO.setup(i,GPIO.IN,pull_up_down=GPIO.PUD_UP)

def calcBed(i,j):
        return i*len(INPUT_PINS) + j + 1
counter = 0
while(True):

	for iid,i in enumerate(OUTPUT_PINS):
		GPIO.output(i,GPIO.LOW)
		for jid,j in enumerate(INPUT_PINS):
			if(GPIO.input(j) == 0):
				if (calcBed(iid,jid),) not in ACTIVE_PINS:
                                        ACTIVE_PINS.append((calcBed(iid,jid),))
                                        print calcBed(iid,jid), " bed pressed"
		GPIO.output(i,GPIO.HIGH)
	counter = counter + 1
        if counter > 3000:
                print "Thread starting..."
                if(len(ACTIVE_PINS) > 0):
                        thread = Thread(target = UpdateDatabase, args = (list(ACTIVE_PINS),))
                        thread.start()
                else:
                        print "Nothing to add"
                print "Thread ended..."
                ACTIVE_PINS = []
                counter = 0
