string=input("Enter password\n")

length=len(string)

nouns=['book', 'company', 'pen', 'fruit', 'building']
special="@!#$%&*"

len_param=True
count=0
count_check=False
alphanum_param=1
noun=0

if(length>16):
	len_pam=False
for i in nouns:
	if(i==string):
		noun=1

for i in string:
	if (special.find(i)>-1):
		count=count+1
if(count>=2):		
	count_check=True

for i in string:
	asc=ord(i)
	if((asc>=48 and asc<=57)or(asc>=97 and asc<=122)or(asc>=65 and asc<=90)):
		alphanum_param=1*alphanum_param
	else:
		if(special.find(i)<0):
			alphanum_param=0*alphanum_param

if(len_param==True and noun==0 and count_check==True and alphanum_param==1):
	print("Valid password")
else:
	print("Invalid password")
