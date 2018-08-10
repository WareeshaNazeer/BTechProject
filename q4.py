
listt=[]

number=input()
l=len(number)

if(int(number)<0):
	l=l-1

dup=abs(int(number))

for i in range(l):
	d=int(dup%10)
	listt.append(d)
	dup=int(dup/10)

if(int(number)<0):
	listt[l-1]=-1*listt[l-1]

print(listt)


