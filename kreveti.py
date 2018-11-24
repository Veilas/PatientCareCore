#!/usr/bin/env python
from pad4pi import rpi_gpio
import socket


HOST = ''
PORT = 5000
CLIENT_ADRESSES = []

#Mode mora da bude BCM
INPUT_PINS_BCM  = [9, 12, 16, 20, 21]
OUTPUT_PINS_BCM = [5, 6, 13, 19, 26]

LAYOUT = [[ 1, 2, 3, 4, 5],
	      [ 6, 7, 8, 9,10],
	      [11,12,13,14,15],
	      [16,17,18,19,20],
	      [21,22,23,24,25]]


def sendNumber(number):
    #No connected device
    if len(CLIENT_ADRESSES) == 0:
        return
    i = 0
    while i < len(CLIENT_ADRESSES):
        client = socket.socket()
        client.settimeout(3)
        try:
            client.connect((CLIENT_ADRESSES[i],8000))
        except socket.error as timeoutmsg:
            CLIENT_ADRESSES.remove(CLIENT_ADRESSES[i])
            continue
        ba = bytearray()
        ba.append(number)
        client.send(ba)
        client.close()
        i += 1

factory = rpi_gpio.KeypadFactory()
keypad = factory.create_keypad(keypad=LAYOUT, row_pins=OUTPUT_PINS_BCM, col_pins=INPUT_PINS_BCM)
keypad.registerKeyPressHandler(sendNumber)

def Mainloop():
	while(True):
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		try:
		    s.bind((HOST,PORT))
		except socket.error as msg:
		    print "Error: ", msg

		s.listen(10)
		while True:
		    conn, addr = s.accept()
		    if addr[0] not in CLIENT_ADRESSES:
		        CLIENT_ADRESSES.append(addr[0])
		s.close()


try:
    Mainloop()
except KeyboardInterrupt:
    print "Exiting..."
except:
    print "An error occured..."
finally:
    keypad.cleanup()
