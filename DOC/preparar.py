#!/usr/bin/env python
# -*- coding: utf-8 -*-
#	   preparar.py
#	   
#	   Copyright 2010 Nelson Cruz <neac03@gmail.com>
#	   
#	   This program is free software; you can redistribute it and/or modify
#	   it under the terms of the GNU General Public License as published by
#	   the Free Software Foundation; either version 2 of the License, or
#	   (at your option) any later version.
#	   
#	   This program is distributed in the hope that it will be useful,
#	   but WITHOUT ANY WARRANTY; without even the implied warranty of
#	   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#	   GNU General Public License for more details.
#	   
#	   You should have received a copy of the GNU General Public License
#	   along with this program; if not, write to the Free Software
#	   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
#	   MA 02110-1301, USA.

import random

def load(file_name = '',mod = 'r'):
	try:
		file = open(file_name, mod)
	except IOError, e:
		print('Error no se pudo cargar el fichero %s' %path)
		exit(1)
	data = file.readlines()
	file.close()
	return data
	
def make_streets(file_name=''):
	random.seed()
	data = load(file_name)
	tam = len(data)
	name = file_name.replace(".txt","Fixed.txt")
	out = open(name, "w")
	
	for i in xrange(300):
		empty_line = 1
		while (empty_line == 1):
			street_name = random.choice(data)
			if street_name != "\n":
				empty_line = 0
		line = street_name.strip()
		line = line.replace(".","")
		numeroCalle = random.randint(0,5000)
		line = line + " " + str(numeroCalle)
		out.write(line+"\n")
	out.close

def make_names(file_name='',file_lastname=''):
	
	names = load(file_name)
	lastnames = load(file_lastname)
	namef = "NombreF.txt"
	out = open(namef, "w")
	out2 = open("ApellidoF.txt" , "w")
	
	for i in xrange(300):
		#print(names[i])
		name = random.choice(names)
		lastname = random.choice(lastnames)
		if name != "\n":
			last = lastname.split(" ")
			fixed = ""
			for i in last:
				i = i.strip()
				i = i.capitalize()
				fixed += i +" "
			lastname = fixed
			name =name.strip()
			name = name.capitalize()
			out2.write(lastname + "\n")
			out.write(name.split()[0] + "\n")
	out.close
	out2.close
	print namef + " file generated successfully"
	
def make_mails():
	mails_file = open("mails.txt","w")
	servers = ["gmail.com","yahoo.com.es","hotmail.com","compu.serve.com","fake.net.com"]
	names = load("NombreF.txt")
	for name in names:
		name = name.strip()
		sufix = random.randint(100,999)
		server = random.choice(servers)
		mail = name + str(sufix) + "@" + server
		mails_file.write(mail + "\n")
	mails_file.close
	
def main():
	make_streets("Calles.txt")
	make_names("Nombres.txt","Apellidos.txt")
	make_mails()
	return 0

if __name__ == '__main__': main()
