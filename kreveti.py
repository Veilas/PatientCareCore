#!/usr/bin/env python
#import RPi.GPIO as GPIO

import time
import sqlite3
from threading import Thread
from pad4pi import rpi_gpio

#Mode mora da bude BCM
INPUT_PINS_BCM = [9, 12, 16, 20, 21]
OUTPUT_PINS_BCM = [5, 6, 13, 19, 26]

LAYOUT = [[1,2,3,4,5],
	  [6,7,8,9,10],
	  [11,12,13,14,15],
	  [16,17,18,19,20],
	  [21,22,23,24,25]]

factory = rpi_gpio.KeypadFactory()
keypad = factory.create_keypad(keypad=LAYOUT, row_pins=OUTPUT_PINS_BCM, col_pins=INPUT_PINS_BCM)
ACTIVE_PINS = []
connection_locked = False

def UpdateDatabase(lista):
        db_conn = sqlite3.connect('/home/pi/PatientDB.db')
        cur = db_conn.cursor();
        #print "Thread inserting..."
        #print lista
        cur.executemany("INSERT INTO patient_log(serviced, request_time, bed_id) VALUES('NE',datetime(CURRENT_TIMESTAMP,'localtime'),?)",(lista,))
        db_conn.commit()
        db_conn.close()

def keypresshandler(key):
	ACTIVE_PINS = []
	#print key
	if (key not in ACTIVE_PINS):
		ACTIVE_PINS.append(key)
	        #print key, " bed pressed"
	if(len(ACTIVE_PINS) > 0):
		thread = Thread(target = UpdateDatabase, args = (list(ACTIVE_PINS),))
		thread.start()
	ACTIVE_PINS = []

keypad.registerKeyPressHandler(keypresshandler)

def Mainloop():
	while(True):
		infi = 1			

Mainloop()
