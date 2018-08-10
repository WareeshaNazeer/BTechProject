import calendar

year=input("Enter year\n")
year=int(year)

list_day=['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']

leap=True
if(year%4==0):
	if(year%100==0):
		if(year%400==0):
			leap=True
		else:
			leap=False
else:
	leap=False

if(leap==False):
	result=year%4
	if(result>2):
		print(year+1)
	else:
		print(year-result)
else:
	day=calendar.weekday(year, 2, 29)
	print(list_day[int(day)])
