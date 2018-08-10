n=input("Enter number\n")
n=int(n)
summation=0.0
numerator=1
denominator=2
for i in range(n):
	summation = summation + (numerator/denominator)
	numerator=numerator+1
	denominator=denominator+1

print("%.2f"%summation)
